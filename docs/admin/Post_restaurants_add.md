### Add a Restaurant
Allows admins to add new restaurant with an initial rating of 0.

#### Request
`POST http://localhost:8080/rest/admin/restaurants`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                               |
|:------:|-------------------------------------------|
|  name  | New restaurant's name (mast be not empty) |

#### Errors
The method may return an HTTP 409 error if the new restaurant's name looks like a duplicate of an existing restaurant's name.
The method may return an HTTP 400 error if the new restaurant's name is not valid.