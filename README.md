# Billing System
A RESTful web service developed with Spring Boot that mimics a rudimentary billing system. Developed for the final project of Patika Evam Java Bootcamp.

### Dependencies
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok

### Database Diagram
<img src="https://github.com/egeoz/bootcamp-final/blob/main/images/DatabaseDiagram.png?raw=true">

### API Examples
##### Adding a New Customer

##### Removing a Customer

##### Query a Specific Customer

##### Listing All Customers

##### Adding a New Invoice

##### Paying an Invoice

### Sending Requests via cURL
##### Creating a new customer
```bash
$ curl http://localhost:8080/customers -X POST -d '{"customerName": "Ege", "customerSurname": "Öz"}' -H 'Content-Type: application/json'
```
