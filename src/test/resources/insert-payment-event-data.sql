INSERT INTO bank.payment_events (id, is_payment_done, member_id, created_dt)
VALUES ('PAYMENT-TEST-ID', 1, 10000001, '2024-08-14 14:20:31');

INSERT INTO bank.payment_orders (payment_event_id, amount, payment_order_status, created_dt)
VALUES ('PAYMENT-TEST-ID', 1000, 'EXECUTING', '2024-08-14 14:20:31');
INSERT INTO bank.payment_orders (payment_event_id, amount, payment_order_status, created_dt)
VALUES ('PAYMENT-TEST-ID', 1000, 'SUCCESS', '2024-08-14 14:20:31');

INSERT INTO bank.payback_orders (payback_event_id, payment_event_id, payback_order_status, payment_amount,
                                 payback_amount, member_id, created_dt)
VALUES (null, 'PAYMENT-TEST-ID', 'NOT_STARTED', 1000, null, 10000001, '2024-08-14 14:52:28');