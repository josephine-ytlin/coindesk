# coindesk

##### coindesk CRUD RESTful API using Spring-boot and Maven

#### Endpoints:
##### &emsp; GET [^/myapp/originalCoindesk]: **return** coindesk API data.
##### &emsp; GET /myapp/newCoindesk : **return** transformed coindesk API data contained: id/name/rate/type/updateDate.
##### &emsp; GET /myapp/newCoindesk/{type} : get one coin information by Type, **return** id/name/rate/type/updateDate.
##### &emsp; POST /myapp/newCoindesk : create a new coin data, **required** id/name/rate/type/updateDate.
##### &emsp; PUT /myapp/newCoindesk/{type} : update an existed coin data, **required** id/name/rate/type/updateDate.
##### &emsp; DELETE /myapp/newCoindesk/{type} : delete one coin information by Type. 
