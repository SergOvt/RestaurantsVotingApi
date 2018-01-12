### Add a User
Allows admins to add new user.

#### Request
`POST http://localhost:8080/rest/admin/users`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                                                                |
|:--------:|--------------------------------------------------------------------------------------------|
|   name   | New user's name (mast be not empty)                                                        |
|   email  | New user's unique email (mast be not empty, have correct email format, max 100 characters) |
| password | New user's password (min 5 characters, max 64 characters)                                  |
|   roles  | Roles for new user (mast be ROLE_USER or ROLE_ADMIN or [ROLE_USER, ROLE_ADMIN])            |
