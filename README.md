
## Coindesk CRUD RESTful API & Unit tests using Spring-boot, JUnit and Maven with H2 database ORM.
- Endpoints:
  - GET `/myapp/originalCoindesk` : **return** coindesk API data.
  - GET `/myapp/newCoindesk` : **return** transformed coindesk API data contained: 
    - id, name, rate, type, updateDate.
  - GET `/myapp/newCoindesk/{type}` : get one coin information by Type, **return**:
    - id, name, rate, type, updateDate.
  - POST `/myapp/newCoindesk` : create a new coin data, **required**:
    - id, name, rate, type, updateDate.
  - PUT `/myapp/newCoindesk/{type}` : update an existed coin data, **required**:
    - id, name, rate, type, updateDate.
  - DELETE `/myapp/newCoindesk/{type}` : delete one coin information by Type. 
