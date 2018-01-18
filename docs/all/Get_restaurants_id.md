### Get details of a Restaurant
Gives full details about a restaurant including id, name and rating.

#### Request
`GET http://localhost:8080/rest/restaurants/RESTAURANT_ID`

#### Authentication
User or userless authentication.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                        |
|:------:|----------------------------------------------------|
|  id    | A unique identifier for restaurant (RESTAURANT_ID) |
|  name  | This restaurant's name                             |
| rating | Numerical day rating of the restaurant             |

#### Response
```{"id":1,"name":"Restaurant1","rating":1}```

#### Errors
The method may return an HTTP 422 error if the restaurant with id = RESTAURANT_ID not exist.
