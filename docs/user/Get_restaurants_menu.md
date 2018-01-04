### Get a Restaurant's day menu 
Gives restaurant's day menu.

#### Request
`GET http://localhost:8080/rest/user/restaurants/RESTAURANT_ID/menu`

#### Authentication
User or userless authentication.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                        |
|:------:|----------------------------------------------------|
|  id    | A unique identifier for each dish                  |
| title  | A name for each dish                               |
| price  | Numerical price for each dish. Price keep in cents |  

#### Response
```
[
{"id":2,"title":"fish","price":15000},
{"id":3,"title":"chicken","price":5000}
]
```
