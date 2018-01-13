### Update a User
Allows admins to update user's details (name, email, password, roles).

#### Request
`PUT http://localhost:8080/rest/admin/users/USER_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                                                                           |
|:--------:|-------------------------------------------------------------------------------------------------------|
|   name   | New or current user's name (mast be not empty)                                                        |
|   email  | New or current user's unique email (mast be not empty, have correct email format, max 100 characters) |
| password | New or current user's password (min 5 characters, max 64 characters)                                  |
|   roles  | New or current roles for updatable user (mast be ROLE_USER or ROLE_ADMIN or [ROLE_USER, ROLE_ADMIN])  |
| enabled  | New or current status for updatable user (true or false)                                              |
