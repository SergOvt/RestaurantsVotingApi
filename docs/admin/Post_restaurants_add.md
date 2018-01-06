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
