CREATE TABLE members
(
    id                    bigint      NOT NULL,
    user_name             VARCHAR(10) NULL,
    is_registered         boolean     NOT NULL DEFAULT TRUE COMMENT '가입 여부',
    single_payment_limit  int         NOT NULL COMMENT '1회 결제 한도',
    daily_payment_limit   int         NOT NULL COMMENT '1일 결제 한도',
    monthly_payment_limit int         NOT NULL COMMENT '한달 결제 한도',
    current_balance       int         NOT NULL DEFAULT 0 COMMENT '현재 잔액',
    created_dt            datetime    NOT NULL,
    updated_dt            datetime    NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '유저 테이블';

CREATE TABLE payment_events
(
    id              varchar(20) NOT NULL,
    is_payment_done boolean     NOT NULL,
    member_id       bigint      NOT NULL,
    created_dt      datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '결제 이벤트 테이블';


CREATE TABLE payment_orders
(
    id                   bigint      NOT NULL AUTO_INCREMENT,
    payment_event_id     varchar(20) NOT NULL,
    amount               int         NOT NULL,
    payment_order_status varchar(30) NOT NULL,
    created_dt           datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '결제 주문 테이블';

CREATE INDEX idx_payment_event ON payment_orders (payment_event_id);

CREATE TABLE payment_cancel_events
(
    id               varchar(20) NOT NULL,
    is_cancel_done   boolean     NOT NULL,
    payment_event_id varchar(20) NOT NULL,
    event_type       varchar(20) NOT NULL,
    member_id        bigint      NOT NULL,
    created_dt       datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '취소 이벤트 테이블';

CREATE INDEX idx_payment_cancel_event ON payment_cancel_events (payment_event_id);

CREATE TABLE payback_events
(
    id              varchar(20) NOT NULL,
    is_payback_done boolean     NOT NULL,
    member_id       bigint      NOT NULL,
    created_dt      datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '페이백 이벤트 테이블';

CREATE TABLE payback_cancel_events
(
    id               varchar(20) NOT NULL,
    is_cancel_done   boolean     NOT NULL,
    payback_event_id varchar(20) NOT NULL,
    created_dt       datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '페이백 취소 이벤트 테이블';

CREATE INDEX idx_payback_cancel_event ON payback_cancel_events (payback_event_id);

CREATE TABLE payback_orders
(
    id                   bigint      NOT NULL AUTO_INCREMENT,
    payback_event_id     varchar(20) NULL,
    payment_event_id     varchar(20) NOT NULL,
    payback_order_status varchar(30) NOT NULL,
    payment_amount       int         NOT NULL,
    payback_amount       int         NULL,
    member_id            bigint      NOT NULL,
    created_dt           datetime    NOT NULL,
    PRIMARY KEY (id)
) COLLATE utf8mb4_unicode_ci COMMENT '페이백 대상 테이블';


## 랜덤 사용자 생성
INSERT INTO members (id, user_name, is_registered, single_payment_limit, daily_payment_limit,
                     monthly_payment_limit, current_balance, created_dt, updated_dt)
VALUES (1,
        (CONCAT('user', FLOOR(RAND() * 9999))), -- 랜덤 사용자 이름 생성
        TRUE, -- 랜덤 가입 여부 설정
        ROUND((RAND() * 10000)), -- 1회 결제 한도: 랜덤으로 0~10000 사이 값
        ROUND((RAND() * 50000)), -- 1일 결제 한도: 랜덤으로 0~50000 사이 값
        ROUND((RAND() * 100000)), -- 한달 결제 한도: 랜덤으로 0~100000 사이 값
        ROUND((RAND() * 200000)), -- 잔액: 랜덤으로 0~200000 사이 값
        NOW(), -- 현재 날짜 및 시간 세팅
        NULL);