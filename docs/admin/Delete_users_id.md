### Delete a User
Allows admins to delete a user with id = USER_ID.

#### Request
`DELETE http://localhost:8080/rest/admin/users/USER_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
Not supported

#### Errors
The method may return an HTTP 422 error if the user with id = USER_ID not exist.