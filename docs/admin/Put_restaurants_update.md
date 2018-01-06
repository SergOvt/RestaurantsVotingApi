### Update a Restaurant
Allows admins to update restaurant's name.

#### Request
`PUT http://localhost:8080/rest/admin/restaurants`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                                   |
|:------:|---------------------------------------------------------------|
|  id    | A unique identifier for updatable restaurant                  |
|  name  | New restaurant's name (mast be not empty)                     |
