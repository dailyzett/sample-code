CREATE TABLE cart
(
    cart_id varchar(8),
    PRIMARY KEY (cart_id)
);


CREATE TABLE cart_event
(
    event_id varchar(36),
    cart_id  varchar(8),
    payload  json,
    time     long,
    PRIMARY KEY (event_id)
);