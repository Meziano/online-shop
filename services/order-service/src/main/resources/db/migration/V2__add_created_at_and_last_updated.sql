alter table orders
    add column created_at timestamp,
    add column last_updated timestamp;

alter table order_items
    add column created_at timestamp,
    add column last_updated timestamp;