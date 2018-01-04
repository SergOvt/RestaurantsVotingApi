### Update a User
Allows admins to update user's details (name, email, password, roles).

#### Request
`PUT http://localhost:8080/rest/admin/users/USER_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                             |
|:--------:|---------------------------------------------------------|
|    id    | A unique identifier for updatable user (not changeable) |
|   name   | New user's name                                         |
|   email  | New user's unique email                                 |
| password | New user's password                                     |
|   roles  | New set of roles for updatable user (admin or/and user) |
