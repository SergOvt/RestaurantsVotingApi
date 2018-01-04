### Get details of a Restaurant
Gives full details about a restaurant including id, name and rating.

#### Request
`GET http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                             |
|:------:|-----------------------------------------|
|  id    | A unique identifier for this restaurant |
|  name  | This restaurant's name                  |
| rating | Numerical day rating of the restaurant  |

#### Response
```{"id":1,"name":"Restaurant1","rating":1}```
