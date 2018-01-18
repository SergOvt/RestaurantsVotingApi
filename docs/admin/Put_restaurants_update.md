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
|  name  | New restaurant's name (mast be not empty)                     |

#### Errors
The method may return an HTTP 409 error if the new restaurant's name looks like a duplicate of an existing restaurant's name.
The method may return an HTTP 400 error if the new restaurant's name is not valid.