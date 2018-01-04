### Update User's details
Allows authorized users to update some their details (name and password).

#### Request
`PUT http://localhost:8080/rest/user/profile`

#### Authentication
Mast user authentication.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                                 |
|:--------:|-------------------------------------------------------------|
|    id    | A unique identifier for authorized user (not changeable)    |
|   name   | New user's name                                             |
|   email  | Authorized user's unique email (not changeable by user)     |
| password | New user's password                                         |
|   roles  | A set of roles for authorized user (not changeable by user) |
