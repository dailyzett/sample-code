UPDATE performance_schema.setup_instruments
SET ENABLED = 'YES',
    TIMED   = 'YES'
WHERE NAME = 'stage/innodb/alter%';

UPDATE performance_schema.setup_instruments
SET ENABLED = 'YES'
WHERE NAME LIKE '%stages%';

ALTER TABLE salaries
    DROP PRIMARY KEY,
    ALGORITHM = COPY,
    LOCK = SHARED;

ALTER TABLE salaries
    ADD INDEX ix_todate (to_date),
    ALGORITHM = INPLACE,
    LOCK = NONE;

SELECT EVENT_NAME, WORK_COMPLETED, WORK_ESTIMATED
FROM performance_schema.events_stages_current;