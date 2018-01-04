### Update a Restaurant
Allows admins to update restaurant's name.

#### Request
`PUT http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                                   |
|:------:|---------------------------------------------------------------|
|  id    | A unique identifier for updatable restaurant (not changeable) |
|  name  | New restaurant's name                                         |
