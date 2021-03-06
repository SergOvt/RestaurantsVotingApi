### Get all Users
Gives the list of all users.

#### Request
`GET http://localhost:8080/rest/admin/users`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                      |
|:--------:|--------------------------------------------------|
|    id    | A unique identifier for each user                |
|   name   | Each user's name                                 |
|   email  | Each user's unique email                         |
| password | Each user's password                             |
|   roles  | A set of roles for each user (admin or/and user) |
| enabled  | A status for each user (enabled or disabled)     |

#### Response
```
[
{"id":1,"name":"user1","email":"user1@mail.ru","password":"{noop}qwerty","roles":["ROLE_USER"],"enabled":true},
{"id":2,"name":"user2","email":"user2@mail.ru","password":"{noop}qwerty","roles":["ROLE_USER"],"enabled":true},
{"id":3,"name":"admin","email":"admin@mail.ru","password":"{noop}qwerty","roles":["ROLE_ADMIN"],"enabled":true}
]
```
