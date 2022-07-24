# Billing System
A RESTful web service developed with Spring Boot that mimics a rudimentary billing system. Developed for the final project of Patika Evam Java Bootcamp.

## Dependencies
- Spring Web
- Spring Data JPA
- PostgreSQL Driver
- Lombok

## Database Diagram
<img src="https://github.com/egeoz/bootcamp-final/blob/main/images/DatabaseDiagram.png?raw=true">

## API Documentation
### Customer Operations
##### Add a new customer
```
METHOD: POST 
localhost:8080/v1/customers
```

```json
{
    "customerName": "Ege",
    "customerSurname": "Öz"
}
```

**Ouput:**
```
Customer 1 has been created successfully.
```
<br></br>

##### Get the list of all customers
```
METHOD: GET 
localhost:8080/v1/customers
```

**Output:**
```json
[
    {
        "customerID": 1,
        "customerName": "Ege",
        "customerSurname": "Öz"
    },
    {
        "customerID": 2,
        "customerName": "Satoshi",
        "customerSurname": "Nakomoto"
    },
    {
        "customerID": 3,
        "customerName": "Marco",
        "customerSurname": "Inaros"
    },
    {
        "customerID": 4,
        "customerName": "Hari",
        "customerSurname": "Seldon"
    }
]
```
<br></br>

##### Get customer by ID
```
METHOD: GET 
localhost:8080/v1/customers/{customerid}
```

**Output:**
```json
{
    "customerID": 1,
    "customerName": "Ege",
    "customerSurname": "Öz"
}
```
<br></br>

##### Get customer by details by ID
```
METHOD: GET 
localhost:8080/v1/customers/{customerid}/details
```

**Output:**
```
Customer ID: 2
Customer Name: Saul Tigh

Payment ID: 1
Payment Amount: 3500.00
Payment Date: 2022-07-24


[
Invoice ID: 1
Invoice Amount: 2500.00
Invoice Payment Status: false
InvoiceDate: 2022-07-24

Invoice ID: 2
Invoice Amount: 3500.00
Invoice Payment Status: true
InvoiceDate: 2022-07-24
]

Customer has unpaid invoices.
```
<br></br>

##### Update a customer by ID
```
METHOD: PUT 
localhost:8080/v1/customers/{customerid}
```

```json
{
  "customerName": "Saul",
  "customerSurname": "Tigh"
}
```

**Output:**
```
Customer 2 has been updated successfully:
Customer ID: 2
Customer Name: Saul Tigh
```
<br></br>

##### Delete customer by ID
```
METHOD: DELETE 
localhost:8080/v1/customers/{customerid}
```

**Output:**
```
Customer 1 has been deleted successfully.
```

<br></br>

### Invoice Operations
##### Get the list of all invoices
```
METHOD: GET 
localhost:8080/v1/invoice
```

**Output:**
```json
[
  {
    "invoiceID": 1,
    "customerID": {
      "customerID": 2,
      "customerName": "Saul",
      "customerSurname": "Tigh"
    },
    "invoiceAmount": 2500.0,
    "invoiceDate": "2022-07-24",
    "isPaid": false
  },
  {
    "invoiceID": 3,
    "customerID": {
      "customerID": 3,
      "customerName": "Marco",
      "customerSurname": "Inaros"
    },
    "invoiceAmount": 100.0,
    "invoiceDate": "2022-07-24",
    "isPaid": false
  },
  {
    "invoiceID": 4,
    "customerID": {
      "customerID": 3,
      "customerName": "Marco",
      "customerSurname": "Inaros"
    },
    "invoiceAmount": 980.0,
    "invoiceDate": "2022-07-24",
    "isPaid": false
  },
  {
    "invoiceID": 5,
    "customerID": {
      "customerID": 4,
      "customerName": "Hari",
      "customerSurname": "Seldon"
    },
    "invoiceAmount": 9999.0,
    "invoiceDate": "2022-07-24",
    "isPaid": false
  },
  {
    "invoiceID": 2,
    "customerID": {
      "customerID": 2,
      "customerName": "Saul",
      "customerSurname": "Tigh"
    },
    "invoiceAmount": 3500.0,
    "invoiceDate": "2022-07-24",
    "isPaid": true
  }
]
```

<br></br>

##### Get invoice by ID
```
METHOD: GET 
localhost:8080/v1/invoice/{invoiceid}
```

**Output:**
```json
{
  "invoiceID": 1,
  "customerID": {
    "customerID": 2,
    "customerName": "Saul",
    "customerSurname": "Tigh"
  },
  "invoiceAmount": 2500.0,
  "invoiceDate": "2022-07-24",
  "isPaid": false
}
```

<br></br>

##### Get the list of invoices by payment status
```
METHOD: GET 
localhost:8080/v1/invoice/status/{ispaid}
```

**Output:**
```json
[
  {
    "invoiceID": 2,
    "customerID": {
      "customerID": 2,
      "customerName": "Saul",
      "customerSurname": "Tigh"
    },
    "invoiceAmount": 3500.0,
    "invoiceDate": "2022-07-24",
    "isPaid": true
  }
]
```

<br></br>

##### Add new invoice
```
METHOD: POST 
localhost:8080/v1/customers/{customerid}/addinvoice
```

```json
{
    "invoiceAmount": "2500"
}
```

**Ouput:**
```
Invoice 1 has been created successfully.
```

<br></br>

##### Delete invoice
```
METHOD: DELETE 
localhost:8080/v1/customers/{customerid}/{invoiceid}/delete
```

**Ouput:**
```
Invoice 1 has been deleted successfully.
```

<br></br>
### Payment Operations
##### Get the list of all payments
```
METHOD: GET 
localhost:8080/v1/payment
```

**Output:**
```json
[
    {
        "paymentID": 1,
        "customerID": {
            "customerID": 2,
            "customerName": "Saul",
            "customerSurname": "Tigh"
        },
        "paymentAmount": 3500.0,
        "paymentDate": "2022-07-24"
    },
    {
        "paymentID": 2,
        "customerID": {
            "customerID": 3,
            "customerName": "Marco",
            "customerSurname": "Inaros"
        },
        "paymentAmount": 100.0,
        "paymentDate": "2022-07-24"
    }
]
```

<br></br>

##### Get payment by ID
```
METHOD: GET 
localhost:8080/v1/payment/{paymentid}
```

**Output:**
```json
{
  "paymentID": 1,
  "customerID": {
    "customerID": 2,
    "customerName": "Saul",
    "customerSurname": "Tigh"
  },
  "paymentAmount": 3500.0,
  "paymentDate": "2022-07-24"
}
```

<br></br>

##### Pay for invoice
```
METHOD: POST 
localhost:8080/v1/customers/{customerid}/{invoiceid}/pay
```

**Output:**
```
Amount 3500.00 is added to payment. Total amount is now: 3500.00
```

<br></br>

### Sending Requests via cURL
```bash
$ curl http://localhost:8080/customers -X POST -d '{"customerName": "Ege", "customerSurname": "Öz"}' -H 'Content-Type: application/json'
```
