### Get details of a User
Gives full details about a user including id, name, email, password and roles.

#### Request
`GET http://localhost:8080/rest/admin/users/USER_ID`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
|  Field   | Description                                      |
|:--------:|--------------------------------------------------|
|    id    | A unique identifier for user (USER_ID)           |
|   name   | This user's name                                 |
|   email  | This user's unique email                         |
| password | This user's password                             |
|   roles  | Roles for this user (admin or/and user)          |
| enabled  | A status for this user (enabled or disabled)     |

#### Response
```{"id":1,"name":"user1","email":"user1@mail.ru","password":"qwerty","roles":["ROLE_USER"],"enabled":true}```
