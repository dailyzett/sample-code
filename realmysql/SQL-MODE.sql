USE employees;

SELECT *
FROM departments
WHERE dept_no = 'd001'

/**
  GROUP_CONCAT
 */
SELECT GROUP_CONCAT(dept_no SEPARATOR '|')
FROM departments;

-- emp_no 컬럼을 역순으로 정렬, dept_no 컬럼의 값을 연결해서 가져오는 쿼리
SELECT GROUP_CONCAT(dept_no ORDER BY emp_no DESC)
FROM dept_emp
WHERE emp_no BETWEEN 100001 AND 100003;

SELECT de.dept_no,
       e.first_name,
       e.gender,
       CASE
           WHEN e.gender = 'F' THEN
               (SELECT s.salary FROM salaries s WHERE s.emp_no = e.emp_no ORDER BY s.from_date DESC LIMIT 1)
           ELSE 0 END AS last_salary
FROM dept_emp de,
     employees e
WHERE e.emp_no = de.emp_no
  AND de.dept_no = 'd001';

/**
  MD5 이용해서 긴 문자열 압축해서 인덱싱하기
 */
CREATE TABLE tb_accesslog
(
    access_id   BIGINT        NOT NULL AUTO_INCREMENT,
    access_url  VARCHAR(1000) NOT NULL,
    access_dttm DATETIME      NOT NULL,
    PRIMARY KEY (access_id),
    INDEX ix_accessurl ((UNHEX(MD5(access_url))))
);


INSERT INTO tb_accesslog VALUE (1, 'http://matt.com', NOW());

SELECT *
FROM tb_accesslog
WHERE UNHEX(MD5(access_url)) = UNHEX(MD5('http://matt.com'));

/**
  JSON_EXTRANCT()
 */

SELECT *
FROM employee_docs;

SELECT emp_no, doc ->> '$.first_name'
FROM employee_docs

SELECT *
FROM employees e,
     dept_emp de
WHERE e.emp_no = de.emp_no;

/**
  지연된 조인 사용하기
  파생 테이블을 사용해서 느릴 것 같지만 사실 느리지 않다.
 */
SELECT e.*
FROM salaries s,
     employees e
WHERE e.emp_no = s.emp_no
  AND s.emp_no BETWEEN 10001 AND 13000
GROUP BY s.emp_no
ORDER BY SUM(s.salary) DESC
LIMIT 10;

SELECT e.*
FROM (SELECT s.emp_no
      FROM salaries s
      WHERE s.emp_no BETWEEN 10001 AND 13000
      GROUP BY s.emp_no
      ORDER BY SUM(s.salary) DESC
      LIMIT 10) x,
     employees e
WHERE e.emp_no = x.emp_no;

/**
  8.0에서 새로 생긴 래터럴 조인
 */

SELECT *
FROM employees e
         LEFT JOIN LATERAL ( SELECT *
                             FROM salaries s
                             WHERE e.emp_no = s.emp_no
                             ORDER BY s.from_date DESC
                             LIMIT 2) s2 ON s2.emp_no = e.emp_no
WHERE e.first_name = 'Matt'

/**
  해시 조인 사용 시 정렬 흐트러짐
 */
SELECT e.emp_no, e.first_name, e.last_name, de.from_date
FROM dept_emp de,
     employees e
WHERE de.from_date > '2001-10-10' AND e.emp_no < 10005;