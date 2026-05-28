alter table products
    add column created_at timestamp,
    add column last_updated timestamp;

alter table product_categories
    add column created_at timestamp,
    add column last_updated timestamp;