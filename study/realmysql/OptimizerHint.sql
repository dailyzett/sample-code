SELECT /*+ JOIN_ORDER(e, s@subq1) */
    COUNT(*)
FROM employees e
WHERE e.first_name = 'Matt'
  AND e.emp_no IN (SELECT /*+ QB_NAME(subq1) */ s.emp_no
                   FROM salaries s
                   WHERE s.salary BETWEEN 50000 AND 50500)


/**
  SET VAR

  조인 버퍼나 정렬용 버퍼의 크기를 일시적으로 증가시켜
  대용량 처리 쿼리 성능을 향상시키는 용도
 */
SELECT /*+ SET_VAR(optimizer_switch='index_merge_intersection=off') */ *
FROM employees
WHERE first_name = 'Georgi'
  AND emp_no BETWEEN 10000 AND 20000;

/**
  SEMIJOIN & NO_SEMIJOIN

  세미조인 최적화와 관련된 힌트
  Table Pull-out 은 선택할 수 없다.

  기존 FirstMatch 세미 조인을 Materialization(구체화) 형식으로 바꿔본다.
 */
SELECT *
FROM departments d
WHERE d.dept_no IN (SELECT de.dept_no FROM dept_emp de);

SELECT *
FROM departments d
WHERE d.dept_no IN
      (SELECT /*+ SEMIJOIN(MATERIALIZATION) */ de.dept_no FROM dept_emp de)

/**
  BNL & NO_BNL & HASH JOIN

  8.0.20 버전 이상부터 BNL 힌트 사용 시 해시 조인을 유도한다.
  HASHJOIN, NO_HASHJOIN 은 8.0.18 버전에서만 유효하다.

  8.0.20 과 그 이후 버전에서는 해시 조인을 유도하거나 해시 조인을 사용하지 않게 한다면,
  BNL과 NO_BNL 힌트를 사용해야한다.
 */

SELECT /*+ BNL(e, de) */ *
FROM employees e
         INNER JOIN dept_emp de ON de.dept_no = e.emp_no

/**
  JOIN_FIXED_ORDER
  JOIN_ORDER: 힌트에 명시된 테이블의 순서대로 조인을 실행하는 힌트
  JOIN_PREFIX: 조인에서 드라이빙 테이블만 강제하는 힌트
  JOIN_SUFFIX: 조인에서 드리븐 테이블만 강제하는 힌트
 */

SELECT /*+ JOIN_FIXED_ORDER() */ *
FROM employees e
         JOIN dept_emp de ON e.emp_no = de.emp_no
         JOIN departments d ON de.dept_no = d.dept_no;

SELECT /* +JOIN_ORDER(d, de) */ *
FROM employees e
         JOIN dept_emp de ON e.emp_no = de.emp_no
         JOIN departments d ON de.dept_no = d.dept_no;

SELECT /* +JOIN_PREFIX(e, de) */ *
FROM employees e
         JOIN dept_emp de ON e.emp_no = de.emp_no
         JOIN departments d ON de.dept_no = d.dept_no;

/**
  MERGE & NO_MERGE

  FROM 절의 서브쿼리를 외부 쿼리로 최대한 병합한다.
  때로는 옵티마이저가 병합하지 못할 때 이 힌트를 사용한다.

  아래 예제(MERGE)는 힌트를 적용 하나 안하나 결과는 똑같다.
 */

SELECT /*+ MERGE(sub)*/ *
FROM (SELECT * FROM employees WHERE first_name = 'Matt') sub
LIMIT 10;

select /*+ NO_MERGE(sub)*/ *
FROM (SELECT * FROM employees WHERE first_name = 'Matt') sub
LIMIT 10;

/**
  INDEX_MERGE & NO_INDEX_MERGE
 */

SELECT /*+ NO_INDEX_MERGE(employees PRIMARY) */ *
FROM employees
WHERE first_name = 'Georgi' AND emp_no BETWEEN 10000 AND 20000;

/**
  인덱스 컨디션 푸시다운은 가능하면 사용하는 것이 큰 성능의 이점을 가져다 준다.
  하지만 ICP 로 인해, 잘못된 실행 계획이 수립될 수 있다.

  - A 인덱스와 B 인덱스가 있다.
  - A는 ICP가 가능해서 A 인덱스를 사용하는 것이 비용이 낮게 예측됐다고 가정한다.
  - 하지만 실제 서비스는 B 인덱스를 사용하는 것이 효율적일 수 있다.

  - A 인덱스를 완전히 사용하지 못하게하거나 B 인덱스를 선호하는 것은 좋지 않은 방법이다.
  - 데이터 분포는 항상 균등하지 않으므로 쿼리 검색 범위에 따라 효율성이 달라지기 때문이다.
  - 이럴 때만 ICP 를 끄는 방식으로 힌트를 작성할 수 있다.
 */


SELECT /*+ NO_ICP(employees ix_lastname_firstname) */*
from employees WHERE last_name = 'Ac트tion' AND first_name LIKE '%sal';