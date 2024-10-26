SELECT dept_no, COUNT(*)
FROM dept_emp
GROUP BY dept_no
WITH ROLLUP;

SELECT first_name, last_name, COUNT(*)
FROM employees
GROUP BY first_name, last_name
WITH ROLLUP;

SELECT dept_no, COUNT(*)
FROM dept_emp
GROUP BY dept_no

/**
  레코드를 칼럼으로 변환해서 조회
 */

SELECT dept_no, COUNT(*) AS emp_count
FROM dept_emp
GROUP BY dept_no;

SELECT *
FROM dept_emp;

SELECT SUM(CASE WHEN dept_no = 'd001' THEN emp_count ELSE 0 END) AS count_d001,
       SUM(CASE WHEN dept_no = 'd002' THEN emp_count ELSE 0 END) AS count_d002,
       SUM(CASE WHEN dept_no = 'd003' THEN emp_count ELSE 0 END) AS count_d003,
       SUM(CASE WHEN dept_no = 'd004' THEN emp_count ELSE 0 END) AS count_d004,
       SUM(CASE WHEN dept_no = 'd005' THEN emp_count ELSE 0 END) AS count_d005,
       SUM(CASE WHEN dept_no = 'd006' THEN emp_count ELSE 0 END) AS count_d006,
       SUM(CASE WHEN dept_no = 'd007' THEN emp_count ELSE 0 END) AS count_d007,
       SUM(CASE WHEN dept_no = 'd008' THEN emp_count ELSE 0 END) AS count_d008,
       SUM(CASE WHEN dept_no = 'd009' THEN emp_count ELSE 0 END) AS count_d009,
       SUM(emp_count)                                            AS count_total
FROM (SELECT dept_no, COUNT(*) AS emp_count
      FROM dept_emp
      GROUP BY dept_no) tb_derived;

/**
  하나의 칼럼을 여러 칼럼으로 분리
 */

SELECT dept_no, COUNT(*) AS emp_count
FROM dept_emp
GROUP BY dept_no;

SELECT de.dept_no,
       SUM(CASE WHEN e.hire_date BETWEEN '1980-01-01' AND '1989-12-31' THEN 1 ELSE 0 END) AS count_1980,
       SUM(CASE WHEN e.hire_date BETWEEN '1990-01-01' AND '1999-12-31' THEN 1 ELSE 0 END) AS count_1990,
       SUM(CASE WHEN e.hire_date BETWEEN '2000-01-01' AND '2009-12-31' THEN 1 ELSE 0 END) AS count_2000,
       COUNT(*)                                                                           AS cnt_total
FROM dept_emp de,
     employees e
WHERE de.emp_no = e.emp_no
GROUP BY de.dept_no