DELETE
FROM members
WHERE id = 10000001;

DELETE
FROM payment_events
WHERE id = 'PAYMENT-TEST-ID';

DELETE
FROM payment_orders
WHERE payment_event_id = 'PAYMENT-TEST-ID';

DELETE
FROM payment_cancel_events
WHERE id = 'PAYMENT-CANCEL-ID';

DELETE
FROM payback_events
WHERE id = 'PAYBACK-EVENT-ID';

DELETE
FROM payback_orders
WHERE payment_event_id = 'PAYMENT-TEST-ID';

DELETE
FROM payback_cancel_events
WHERE id = 'PAYBACK-CANCEL-ID';
