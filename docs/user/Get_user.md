### Get User's details
Gives full details about authorized user including id, name, email, password and set of roles.

#### Request
`GET http://localhost:8080/rest/user/profile`

#### Authentication
Mast user authentication.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                            |
|:--------:|--------------------------------------------------------|
|    id    | A unique identifier for authorized user                |
|   name   | Authorized user's name                                 |
|   email  | Authorized user's unique email                         |
| password | Authorized user's password                             |
|   roles  | A set of roles for authorized user (user or/and admin) |
| enabled  | A status for authorized user (may be just enabled)     |

#### Response
```{"id":1,"name":"user1","email":"user1@mail.ru","password":"qwerty","roles":["USER"],"enabled":true}```
