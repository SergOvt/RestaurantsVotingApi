### Get restaurant's day Menu
Gives restaurant's day menu.

#### Request
`GET http://localhost:8080/rest/restaurants/RESTAURANT_ID/menu`

#### Authentication
User or userless authentication.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                        |
|:------:|----------------------------------------------------|
| title  | A name for each dish                               |
| price  | Numerical price for each dish. Price keep in cents |  

#### Response
```
[
{"title":"fish","price":15000},
{"title":"chicken","price":5000}
]
```
#### Errors
The method may return an HTTP 422 error if the menu for restaurant with id = RESTAURANT_ID not exist.