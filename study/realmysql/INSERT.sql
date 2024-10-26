CREATE TABLE daily_statistic
(
    target_date DATE        NOT NULL,
    stat_name   VARCHAR(10) NOT NULL,
    stat_value  BIGINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (target_date, stat_name)
);

INSERT INTO daily_statistic (target_date, stat_name, stat_value)
VALUES (DATE(NOW()), 'VISIT', 1)
ON DUPLICATE KEY UPDATE stat_value = stat_value + 1;

SELECT *
FROM daily_statistic;

/**
  JOIN UPDATE
 */

CREATE TABLE tb_test1
(
    emp_no     INT,
    first_name VARCHAR(14),
    PRIMARY KEY (emp_no)
);

INSERT INTO tb_test1
VALUES (10001, NULL),
       (10002, NULL),
       (10003, NULL),
       (10004, NULL);

UPDATE tb_test1 t1, employees e
SET t1.first_name = e.first_name
WHERE e.emp_no = t1.emp_no;

SELECT *
FROM tb_test1;

DROP TABLE tb_test1;

/**
  여러 레코드 UPDATE
 */
CREATE TABLE user_level
(
    user_id    BIGINT   NOT NULL,
    user_lv    INT      NOT NULL,
    created_dt DATETIME NOT NULL,
    PRIMARY KEY (user_id)
);

UPDATE user_level ul
    JOIN (VALUES ROW (1, 1), ROW (2, 4)) new_user_level(user_id, user_lv)
    ON new_user_level.user_id = ul.user_id
SET ul.user_lv = ul.user_lv + new_user_level.user_lv;

SELECT *
FROM user_level;