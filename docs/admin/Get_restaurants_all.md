### Get all Restaurants
Gives the list of all sorted by rating restaurants (Max rating first).

#### Request
`GET http://localhost:8080/rest/admin/restaurants/all`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                  |
|:------:|----------------------------------------------|
|  id    | A unique identifier for each restaurant      |
|  name  | Each restaurant's name                       |
| rating | Numerical day rating of each restaurant      |

#### Response
```
[
{"id":2,"name":"Restaurant2","rating":2},
{"id":1,"name":"Restaurant1","rating":1},
{"id":3,"name":"Restaurant3","rating":0}
]
```
