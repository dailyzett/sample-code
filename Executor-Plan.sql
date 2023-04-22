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
    STATS_PERSISTENT = 1


