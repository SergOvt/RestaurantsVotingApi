### Delete a Restaurant
Allows admins to delete a restaurant with id = RESTAURANT_ID.

#### Request
`DELETE http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
Not supported

#### Errors
The method may return an HTTP 422 error if the restaurant with id = RESTAURANT_ID not exist.