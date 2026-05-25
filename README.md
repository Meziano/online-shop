# Online Shop

Generated multi-module Spring Boot microservices workspace.

## Modules

- libs/common-model
- libs/common-events
- libs/common-test
- services/customer-service
- services/product-catalog-service
- services/shopping-cart-service
- services/order-service
- services/payment-service
- services/inventory-service
- services/delivery-service

## First steps

1. Start infrastructure with Docker Compose:
   `docker compose up -d`
2. Build all modules:
   `mvn clean install`
3. Run a service, for example:
   `cd services/customer-service && mvn spring-boot:run`
