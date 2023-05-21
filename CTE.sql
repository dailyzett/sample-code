WITH cte1 AS (SELECT emp_no, MIN(from_date) FROM salaries GROUP BY emp_no)
SELECT *
FROM employees e
         JOIN cte1 t1 ON t1.emp_no = e.emp_no
         JOIN cte1 t2 ON t2.emp_no = e.emp_no;

/**
  재귀 쿼리
 */
WITH RECURSIVE cte (no) AS (SELECT 1
                            UNION ALL
                            SELECT no + 1
                            FROM cte
                            WHERE no < 5)
SELECT *
FROM cte;

WITH cte1 AS (SELECT * FROM departments)
SELECT *
FROM cte1;

/**
  재귀 쿼리의 활용
 */
CREATE TABLE test.employees
(
    id         INT PRIMARY KEY,
    name       varchar(100) NOT NULL,
    manager_id INT          NULL,
    INDEX (manager_id),
    FOREIGN KEY (manager_id) REFERENCES employees (id)
);

INSERT INTO test.employees
VALUES (333, 'Yasmina', NULL),
       (198, 'John', 333),
       (692, 'Tarek', 333),
       (29, 'Pedro', 198),
       (4610, 'Sarah', 29),
       (72, 'Pierre', 29),
       (123, 'Adil', 692);

WITH RECURSIVE managers AS (SELECT *, 1 AS lv
                            FROM employees
                            WHERE id = 123
                            UNION ALL
                            SELECT e.*, lv + 1
                            FROM managers m
                                     JOIN employees e ON m.manager_id = e.id AND m.manager_id IS NOT NULL)
SELECT *
FROM managers
ORDER BY lv DESC;

WITH RECURSIVE managers AS (SELECT *,
                                   CAST(id AS CHAR(100)) AS manager_path,
                                   1                     AS lv
                            FROM employees
                            WHERE id = manager_id IS NULL
                            UNION ALL
                            SELECT e.*,
                                   CONCAT(e.id, '->', m.manager_path) AS manager_path,
                                   lv + 1
                            FROM managers m
                                     JOIN employees e ON m.id = e.manager_id)
SELECT *
FROM managers;

/**
  윈도우 함수
 */
SELECT e.*, RANK() OVER (ORDER BY e.hire_date) AS hire_date_rank
FROM employees e

/**
  부서별로 입사 순위 매기기
 */
SELECT e.*, RANK() OVER (PARTITION BY de.dept_no ORDER BY e.hire_date) AS hire_date_rank
FROM employees e
         JOIN dept_emp de ON e.emp_no = de.emp_no
ORDER BY de.dept_no, e.hire_date;

-- emp_no가 10001인 사원인 급여 이력과 평균 급여를 조회하는 쿼리
SELECT emp_no,
       from_date,
       salary,
       AVG(salary) OVER () AS avg_salary
FROM salaries
WHERE emp_no = 10001;

-- 윈도우 함수 프레임
SELECT emp_no,
       from_date,
       salary,
       -- 현재 레코드의 from_date 를 기준으로 1년 전부터 지금까지 급여 중 최소 급여
       MIN(salary) OVER (ORDER BY from_date RANGE INTERVAL 1 YEAR PRECEDING)                               AS min_1,
       -- 현재 레코드의 from_date 를 기준으로 1년 전부터 2년 후까지의 급여 중 최대 급여
       MAX(salary)
           OVER (ORDER BY from_date RANGE BETWEEN INTERVAL 1 YEAR PRECEDING AND INTERVAL 2 YEAR FOLLOWING) AS max_2,
       -- from_date 칼럼으로 정렬 후, 첫 번째 레코드로부터 현재 레코드까지의 평균
       AVG(salary) OVER (ORDER BY from_date ROWS UNBOUNDED PRECEDING)                                      AS avg_1,
       -- from_date 칼럼으로 정렬 후, 현재 레코드를 기준으로 이전 건부터 이후 레코드까지의 급여 평균
       AVG(salary)
           OVER (ORDER BY from_date ROWS BETWEEN 1 PRECEDING AND 1 FOLLOWING)                              AS avg_2
FROM salaries
WHERE emp_no = 10001;

/**
  FOR SHARE, FOR UPDATE
  공유 잠금, 배타 잠금
 */

SELECT *
FROM employees e
         JOIN dept_emp de ON e.emp_no = de.emp_no
         JOIN departments d ON de.dept_no = d.dept_no
    FOR
UPDATE OF e;