### Update User's details
Allows authorized users to update some their details (name, email and password).

#### Request
`PUT http://localhost:8080/rest/user/profile`

#### Authentication
Mast user authentication.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                                                                           |
|:--------:|-------------------------------------------------------------------------------------------------------|
|   name   | New or current user's name (mast be not empty)                                                        |
|   email  | New or current user's unique email (mast be not empty, have correct email format, max 100 characters) |
| password | New or current user's password (min 5 characters, max 32 characters)                                  |

#### Errors
The method may return an HTTP 409 error if the user's email looks like a duplicate of an existing user's email.
The method may return an HTTP 400 error if the response fields are not valid.