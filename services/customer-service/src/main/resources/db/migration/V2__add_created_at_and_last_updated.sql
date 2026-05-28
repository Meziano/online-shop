alter table customers
    add column created_at timestamp,
    add column last_updated timestamp;

alter table customer_contacts
    add column created_at timestamp,
    add column last_updated timestamp;