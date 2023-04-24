USE employees;
SHOW TABLES LIKE '%_stats';

/**
  통계 테이블 생성

  STATS_PERSISTENT = 1. 기본적으로 ON 으로 설정되어 있다.
 */
CREATE TABLE tab_test
(
    fd1 INT,
    fd2 VARCHAR(20),
    PRIMARY KEY (fd1)
) ENGINE = InnoDB
  STATS_PERSISTENT = 1;

SELECT *
FROM mysql.innodb_table_stats
WHERE table_name IN ('table_persistent', 'tab_transient');

-- 통계 정보를 0으로 변경
ALTER TABLE employees.employees
    STATS_PERSISTENT = 1;

/**
  히스토그램 조회

  수집된 히스토그램 정보는 시스템 딕셔너리에 저장.
  MySQL 서버가 시작될 때 딕셔너리의 히스토그램 정보를 information_schema 데이터베이스의 column_statistics 테이블로 로드한다.

  - Singleton: 컬럼값 개별로 레코드 건수를 관리하는 히스토그램
  - Equi-Height: 컬럼값의 범위를 균등한 개수로 구분해서 관리하는 히스토그램
 */

ANALYZE TABLE employees.employees
    UPDATE HISTOGRAM ON gender, hire_date;


/**
  히스토그램의 용도
 */

SELECT *
FROM employees
WHERE first_name = 'Zita'
  AND birth_date BETWEEN '1950-01-01' AND '1960-01-01';

ANALYZE TABLE employees
    UPDATE HISTOGRAM ON first_name, birth_date;

SELECT SUM(IF(birth_date BETWEEN '1950-01-01' AND '1960-01-01', 1, 0))
           / COUNT(*) AS ratio
FROM employees
WHERE first_name = 'Zita';


/**
  코스트 모델
 */

USE information_schema;


/**
  EXPLAIN FORMAT
 */

USE employees;

SELECT *
FROM employees e
         JOIN salaries s ON s.emp_no = e.emp_no
WHERE first_name = 'ABC';

/**
  EXPLAIN ANALYZE
  읽는 순서
  - 들여쓰기가 같은 레벨에서는 상단에 위치한 라인이 먼저 실행
  - 들여쓰기가 다른 레벨에서는 가장 안쪽에 위치한 라인이 먼저 실행
 */

SELECT e.emp_no, s.salary
FROM employees e
         INNER JOIN salaries s ON e.emp_no = s.emp_no
    AND s.salary > 50000
    AND s.from_date <= '1990-01-01'
    AND s.to_date > '1990-01-01'
WHERE e.first_name = 'Matt'
GROUP BY e.hire_date


/**
  실행 계획 - 컬럼 분석
 */

-- ID
-- 하나의 SELECT 안에 하위 SELECT 가 있는 쿼리
SELECT e.emp_no, e.first_name, s.from_date, s.salary
FROM employees e,
     salaries s
WHERE e.emp_no = s.emp_no LIMIT 10;

-- 3개의 SELECT 쿼리로 구성돼있음
-- ID 각각 분배
SELECT
    ((SELECT COUNT(*) FROM employees) + (SELECT COUNT(*) FROM departments)) AS total_count

-- select_type 칼럼



