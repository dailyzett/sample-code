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
WHERE e.emp_no = s.emp_no
LIMIT 10;

-- 3개의 SELECT 쿼리로 구성돼있음
-- ID 각각 분배
SELECT ((SELECT COUNT(*) FROM employees) + (SELECT COUNT(*) FROM departments)) AS total_count

-- select_type 칼럼.. DRIVED, UNION

SELECT *
FROM ((SELECT emp_no FROM employees e1 LIMIT 10)
      UNION ALL
      (SELECT emp_no FROM employees e2 LIMIT 10)
      UNION ALL
      (SELECT emp_no FROM employees e3 LIMIT 10)) tb;

-- UNION RESULT

SELECT emp_no
FROM salaries
WHERE salary > 100000
UNION
DISTINCT
SELECT emp_no
FROM dept_emp
WHERE from_date > '2001-01-01'

-- SUBQUERY (FROM 절이 아닌 서브쿼리만을 말한다)

SELECT e.first_name,
       (SELECT COUNT(*)
        FROM dept_emp de,
             dept_manager dm
        WHERE dm.dept_no = de.dept_no) AS cnt
FROM employees e
WHERE e.emp_no = 10001;

-- DEPENDENT SUBQUERY

SELECT e.first_name,
       (SELECT COUNT(*)
        FROM dept_emp de,
             dept_manager dm
        WHERE de.dept_no = dm.dept_no
          AND de.emp_no = dm.emp_no) AS cnt
FROM employees e
WHERE e.first_name = 'Matt';

-- DERIVED

SELECT COUNT(*)
FROM (SELECT de.emp_no FROM dept_emp de GROUP BY de.emp_no) tb,
     employees e
WHERE e.emp_no = tb.emp_no;

-- (2) DERIVED 를 JOIN 으로 최적화해보기

SELECT COUNT(DISTINCT de.emp_no)
FROM dept_emp de
         JOIN employees e ON e.emp_no = de.emp_no

-- DEPENDENT DERIVED
-- 레터럴 조인
-- Extra Rematerialize (<derived2>)
SELECT *
FROM employees e
         LEFT JOIN LATERAL (
    SELECT *
    FROM salaries s
    WHERE s.emp_no = e.emp_no
    ORDER BY s.from_date DESC
    LIMIT 2
    ) AS s2 ON s2.emp_no = e.emp_no


-- MATERIALIZED
-- 주로 FROM절이나 IN 서브쿼리 최적화를 위해 사용
SELECT *
FROM employees e
WHERE e.emp_no IN (SELECT emp_no FROM salaries WHERE salary BETWEEN 100 AND 1000);


/**
  table 컬럼
 */
SELECT *
FROM (SELECT de.emp_no FROM dept_emp de GROUP BY de.emp_no) tb,
     employees e
WHERE e.emp_no = tb.emp_no


/**
  type 칼럼
 */

SELECT *
FROM employees e,
     dept_emp de
WHERE e.emp_no = de.emp_no
  AND de.dept_no = 'd005';

/**
  UNIQUE_SUBQUERY
 */

SET OPTIMIZER_SWITCH = 'semijoin=on';

-- ix_empno_fromdate dept_emp (emp_no, from_date);
SELECT *
FROM departments
WHERE dept_no IN (SELECT dept_no FROM dept_emp WHERE emp_no = 10001);

/**
  index_merge:

  first_name = ix_first_name,
  emp_no = PRIMARY
 */

SELECT *
FROM employees
WHERE emp_no BETWEEN 10001 AND 11000
   OR first_name = 'Smith';

/**
  key_len 칼럼

  dept_no char(4)
  utf8mb4 집합에서는 문자 하나 당 4 바이트로 계산,
  4바이트 X 4바이트 = 16바이트
 */
SELECT *
FROM dept_emp
WHERE dept_no = 'd005';

/**
  ref 칼럼

  평소엔 크게 신경쓰지 않아도 되지만 func 일때는 유심히 볼 필요가 있다.
 */

SELECT *
FROM employees e,
     dept_emp de
WHERE e.emp_no = (de.emp_no - 1)

/**
  filtered 컬럼
 */

SELECT *
FROM employees e,
     salaries s
WHERE e.first_name = 'Matt'
  AND e.hire_date BETWEEN '1990-01-01' AND '1991-01-01'
  AND s.emp_no = e.emp_no
  AND s.from_date BETWEEN '1990-01-01' AND '1991-01-01'
  AND s.salary BETWEEN 50000 AND 60000;

SELECT *
FROM dept_emp de
WHERE de.emp_no = (SELECT e.emp_no FROM employees e WHERE e.first_name = 'Georgi' AND e.last_name = 'Facello' LIMIT 1);

SELECT *
FROM departments d
WHERE dept_no IN (SELECT de.dept_no FROM dept_emp de)

/**
  Extra - Not exists
 */

SELECT *
FROM dept_emp de
         LEFT JOIN departments d ON de.dept_no = d.dept_no
WHERE d.dept_no IS NULL;

SELECT *
FROM departments;

SELECT *
FROM dept_emp de
         LEFT JOIN departments d ON de.dept_no = d.dept_no;

/**
  Rematerialize
 */

SELECT *
FROM employees e
         LEFT JOIN LATERAL (SELECT * FROM salaries s WHERE s.emp_no = e.emp_no ORDER BY s.from_date DESC LIMIT 2) s2
                   ON s2.emp_no = e.emp_no
WHERE e.first_name = 'Matt';

/**
  루스 인덱스 스캔을 위한 GROUP BY 처리
 */

SELECT emp_no, MIN(from_date)
FROM salaries
GROUP BY emp_no;


