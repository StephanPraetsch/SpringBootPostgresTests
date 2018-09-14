# About this application

  * SpringBoot 2
  * REST
  * HATEOAS
  * integration tests with embedded PostgresQL
  * integration tests with REST
  * LiquiBase
  * Java8 OffsetDateTime in database
  * unit tests
  * AssertJ

# hateoas
see [HATEOAS.md](HATEOAS.md)

# create a user
POST http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629
```json
{"id":"ba2fb9f3-6bab-4202-bb7b-f73872ae3629","name":"John Doe"}
```

# read a user
GET http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629
```json
{
    "id": "ba2fb9f3-6bab-4202-bb7b-f73872ae3629",
    "name": "John Doe",
    "_schema": {
        "links": [
            {
                "href": "http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629",
                "targetSchema": { ... },
                "rel": "self",
                "mediaType": "application/json",
                "method": "GET"
            },
            {
                "href": "http://localhost:8080/users/ba2fb9f3-6bab-4202-bb7b-f73872ae3629",
                "schema": { ... },
                "rel": "update",
                "mediaType": "application/json",
                "method": "PUT"
            }
        ]
    }
}
```
