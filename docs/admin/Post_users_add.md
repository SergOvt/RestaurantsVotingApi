### Add a User
Allows admins to add new user.

#### Request
`POST http://localhost:8080/rest/admin/users/add`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                        |
|:--------:|----------------------------------------------------|
|   name   | New user's name                                    |
|   email  | New user's unique email                            |
| password | New user's password                                |
|   roles  | A set of roles for new user (admin or/and user)    |
