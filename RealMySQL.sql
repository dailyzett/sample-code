CREATE DATABASE employees
    DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

USE employees;


EXPLAIN
SELECT gender, birth_date
FROM employees
WHERE birth_date >= '1965-02-01';

EXPLAIN
SELECT *
FROM employees
WHERE first_name LIKE '%mer';

EXPLAIN
SELECT *
FROM dept_emp
WHERE dept_no = 10017
  AND emp_no >= 10017;


SELECT *
FROM dept_emp;

SELECT *
FROM information_schema.INNODB_FT_DEFAULT_STOPWORD;

EXPLAIN
SELECT *
FROM test1_user
WHERE 360 MEMBER OF (credit_info -> '$.credit_scores');

-- 옵티마이저 트레이스 활성화
SET OPTIMIZER_TRACE = "enabled=on", END_MARKERS_IN_JSON = on;
SET OPTIMIZER_TRACE_MAX_MEM_SIZE = 1000000;

SELECT emp_no, first_name, last_name
FROM employees
ORDER BY first_name;

-- 인덱스를 이용한 정렬 방식
EXPLAIN
SELECT *
FROM employees e,
     salaries s
WHERE s.emp_no = e.emp_no
  AND e.emp_no BETWEEN 100002 AND 100020
ORDER BY e.emp_no;

-- STRAIGHT_JOIN
EXPLAIN
SELECT STRAIGHT_JOIN e.first_name,
                     e.last_name,
                     d.dept_name
FROM employees e,
     dept_emp de,
     departments d
WHERE e.emp_no = de.emp_no
  AND d.dept_no = de.dept_no;

-- 인덱스를 이용한 정렬 방식
EXPLAIN
SELECT *
FROM employees e,
     salaries s
WHERE s.emp_no = e.emp_no
  AND e.emp_no BETWEEN 100002 AND 100020
ORDER BY e.emp_no;

-- 조인의 드라이빙 테이블만 정렬
EXPLAIN
SELECT *
FROM employees e,
     salaries s
WHERE s.emp_no = e.emp_no
  AND e.emp_no BETWEEN 100002 AND 100020
ORDER BY e.last_name;

-- 임시 테이블을 이용한 정렬
EXPLAIN
SELECT *
FROM employees e,
     salaries s
WHERE s.emp_no = e.emp_no
  AND e.emp_no BETWEEN 100002 AND 100020
ORDER BY s.salary;

-- 정렬 관련 상태 변수
FLUSH STATUS;
SHOW STATUS LIKE 'Sort%';

-- 임시 테이블을 사용하는 GROUP BY
EXPLAIN
SELECT e.last_name, AVG(s.salary)
FROM employees e,
     salaries s
WHERE s.emp_no = e.emp_no
GROUP BY e.last_name;

-- 집함 함수와 함께 사용된 DISTINCT
EXPLAIN
SELECT COUNT(DISTINCT s.salary)
FROM employees e,
     salaries s
WHERE e.emp_no = s.emp_no
  AND e.emp_no BETWEEN 100001 AND 100100;

EXPLAIN
SELECT *
FROM dept_emp d
WHERE d.from_date = '1987-07-25'
ORDER BY dept_no;


EXPLAIN
SELECT *
FROM employees
WHERE first_name = 'Georgi' AND emp_no BETWEEN 10000 AND 20000;

EXPLAIN
SELECT *
FROM employees
WHERE first_name = 'Matt' OR hire_date = '1987-03-31';

EXPLAIN
SELECT *
FROM employees
WHERE first_name = 'Matt';
EXPLAIN
SELECT *
FROM employees
WHERE hire_date BETWEEN '1987-03-01' AND '1987-03-31';
EXPLAIN
SELECT *
FROM employees
WHERE first_name = 'Matt' OR hire_date BETWEEN '1987-03-01' AND '1987-03-31';

-- 세미조인
SET SESSION OPTIMIZER_SWITCH = 'semijoin=on';
EXPLAIN
SELECT *
FROM employees e
WHERE e.emp_no IN
      (SELECT de.emp_no FROM dept_emp de WHERE de.from_date = '1995-01-01');


EXPLAIN
SELECT *
FROM employees e
WHERE e.first_name = 'Matt'
  AND e.emp_no IN (SELECT t.emp_no FROM titles t WHERE t.from_date BETWEEN '1995-01-01' AND '1995-01-30');

-- dept_emp 테이블에 존재하는 모든 부서 번호에 대해 부서 정보를 읽어오기 위한 쿼리
EXPLAIN
SELECT *
FROM departments d
WHERE d.dept_no IN (SELECT de.dept_no FROM dept_emp de);

-- 구체화
EXPLAIN
SELECT *
FROM employees e
WHERE e.emp_no IN (SELECT de.emp_no FROM dept_emp de WHERE de.from_date = '1995-01-01');

-- Duplicated Weed-Out
EXPLAIN
SELECT *
FROM employees e
WHERE e.emp_no IN (SELECT s.emp_no FROM salaries s WHERE s.salary > 150000);


-- Condition_fanout_filter
SET SESSION OPTIMIZER_SWITCH = 'condition_fanout_filter=on';
EXPLAIN
SELECT *
FROM employees e
         INNER JOIN salaries s ON s.emp_no = e.emp_no
WHERE e.first_name = 'Matt'
  AND e.hire_date BETWEEN '1985-11-21' AND '1986-11-21';

-- 파생 테이블 머지
EXPLAIN
SELECT *
FROM (SELECT * FROM employees WHERE first_name = 'Matt') derived_table
WHERE derived_table.hire_date = '1986-04-03';

-- DISTINCT 구분
EXPLAIN
SELECT DISTINCT first_name, last_name
FROM employees
WHERE emp_no BETWEEN 10001 AND 10020;

EXPLAIN
SELECT COUNT(DISTINCT first_name), COUNT(last_name)
FROM employees
WHERE emp_no BETWEEN 10001 AND 10020;

EXPLAIN
SELECT COUNT(DISTINCT first_name, last_name)
FROM employees
WHERE emp_no BETWEEN 10001 AND 10020;

SHOW VARIABLES LIKE 'optimizer_search_depth';

SELECT *
FROM employees
WHERE emp_no = 10001;

SELECT *
FROM employees FORCE INDEX (`PRIMARY`)
WHERE emp_no = 10001;
