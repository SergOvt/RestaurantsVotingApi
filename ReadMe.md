Voting system for restaurants REST API (without frontend)
===============================

There is a voting system for deciding where to have lunch:

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides new menu each day.

-----------------------------
## Endpoints
All endpoints require user authentication.

#### For Admin:
| Method |    Group    |   Endpoint    |           Usage                    |
|:------:|:-----------:|:-------------:|:-----------------------------------|
| GET    | restaurants |    `all`      | Get all Restaurants                |
| GET    | restaurants |`RESTAURANT_ID`| Get details of a Restaurant        |
| GET    | restaurants |    `menu`     | Get a Restaurant's day menu        |
| POST   | restaurants |    `add`      | Add a Restaurant                   |
| PUT    | restaurants |`RESTAURANT_ID`| Update a Restaurant                |
| DELETE | restaurants |`RESTAURANT_ID`| Delete a Restaurant                |
| PUT    | restaurants |    `menu`     | Put a Restaurant's day menu        |
| GET    |    users    |    `all`      | Get all Users                      |
| GET    |    users    |   `USER_ID`   | Get details of a User              |
| POST   |    users    |    `add`      | Add a User                         |
| PUT    |    users    |   `USER_ID`   | Update a User                      |
| DELETE |    users    |   `USER_ID`   | Delete a User                      |

#### For User:
| Method |    Group    |    Endpoint   |           Usage                    |
|:------:|:-----------:|:-------------:|:-----------------------------------|
| GET    | restaurants |     `all`     | Get all Restaurants                |
| GET    | restaurants |`RESTAURANT_ID`| Get details of a Restaurant        |
| GET    | restaurants |    `menu`     | Get a Restaurant's day menu        |
| PUT    | restaurants |    `vote`     | Vote for a Restaurant              |
| GET    |  profile    |      `/`      | Get User's details                 |
| PUT    |  profile    |      `/`      | Update User's details              |
| DELETE |  profile    |      `/`      | User deletes himself               |

-----------------------------
## Description Endpoints for Admin

### Get all Restaurants
Gives the list of all sorted by rating restaurants (Max rating first).

##### Request
`GET http://localhost:8080/rest/admin/restaurants/all`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                  |
|:------:|----------------------------------------------|
|  id    | A unique identifier for each restaurant      |
|  name  | Each restaurant's name                       |
| rating | Numerical day rating of each restaurant      |

##### Response
```
[
{"id":2,"name":"Restaurant2","rating":2},
{"id":1,"name":"Restaurant1","rating":1},
{"id":3,"name":"Restaurant3","rating":0}
]
```

### Get details of a Restaurant
Gives full details about a restaurant including id, name and rating.

##### Request
`GET http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                             |
|:------:|-----------------------------------------|
|  id    | A unique identifier for this restaurant |
|  name  | This restaurant's name                  |
| rating | Numerical day rating of the restaurant  |

##### Response
```{"id":1,"name":"Restaurant1","rating":1}```

### Get a Restaurant's day menu 
Gives restaurant's day menu.

##### Request
`GET http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID/menu`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                        |
|:------:|----------------------------------------------------|
|  id    | A unique identifier for each dish                  |
| title  | A name for each dish                               |
| price  | Numerical price for each dish. Price keep in cents |  

##### Response
```
[
{"id":2,"title":"fish","price":15000},
{"id":3,"title":"chicken","price":5000}
]
```

### Add a Restaurant
Allows admins to add new restaurant with an initial rating of 0.

##### Request
`POST http://localhost:8080/rest/admin/restaurants/add`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                   |
|:------:|-------------------------------|
|  name  | New restaurant's name         |

### Update a Restaurant
Allows admins to update restaurant's name.

##### Request
`PUT http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                                   |
|:------:|---------------------------------------------------------------|
|  id    | A unique identifier for updatable restaurant (not changeable) |
|  name  | New restaurant's name                                         |

### Delete a Restaurant
Allows admins to delete a restaurant.

##### Request
`DELETE http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
Not supported

### Put a Restaurant's day menu
Allows admins to add restaurant's day menu or update, if menu for current date is exist.

##### Request
`PUT http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID/menu`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                             |
|:------:|---------------------------------------------------------|
| title  | A name for each new dish                                |
| price  | Numerical price for each new dish. Price keep in cents  |

### Get all Users
Gives the list of all users.

##### Request
`GET http://localhost:8080/rest/admin/users/all`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                      |
|:--------:|--------------------------------------------------|
|    id    | A unique identifier for each user                |
|   name   | Each user's name                                 |
|   email  | Each user's unique email                         |
| password | Each user's password                             |
|   roles  | A set of roles for each user (admin or/and user) |

##### Response
```
[
{"id":1,"name":"user1","email":"user1@mail.ru","password":"qwerty","roles":["USER"]},
{"id":2,"name":"user2","email":"user2@mail.ru","password":"qwerty","roles":["USER"]},
{"id":3,"name":"admin1","email":"admin1@mail.ru","password":"qwerty","roles":["ADMIN"]},
{"id":4,"name":"admin2","email":"admin2@mail.ru","password":"qwerty","roles":["ADMIN"]}
]
```

### Get details of a User
Gives full details about a user including id, name, email, password and set of roles.

##### Request
`GET http://localhost:8080/rest/admin/users/USER_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                      |
|:--------:|--------------------------------------------------|
|    id    | A unique identifier for each user                |
|   name   | This user's name                                 |
|   email  | This user's unique email                         |
| password | This user's password                             |
|   roles  | A set of roles for this user (admin or/and user) |

##### Response
```{"id":1,"name":"user1","email":"user1@mail.ru","password":"qwerty","roles":["USER"]}```

### Add a User
Allows admins to add new user.

##### Request
`POST http://localhost:8080/rest/admin/users/add`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                        |
|:--------:|----------------------------------------------------|
|   name   | New user's name                                    |
|   email  | New user's unique email                            |
| password | New user's password                                |
|   roles  | A set of roles for new user (admin or/and user)    |

### Update a User
Allows admins to update user's details (name, email, password, roles).

##### Request
`PUT http://localhost:8080/rest/admin/users/USER_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                             |
|:--------:|---------------------------------------------------------|
|    id    | A unique identifier for updatable user (not changeable) |
|   name   | New user's name                                         |
|   email  | New user's unique email                                 |
| password | New user's password                                     |
|   roles  | New set of roles for updatable user (admin or/and user) |

### Delete a User
Allows admins to delete a user.

##### Request
`DELETE http://localhost:8080/rest/admin/users/USER_ID`

##### Authentication
User must be admin.

##### Parameters
Not supported

##### Response Fields
Not supported

-----------------------------
## Description Endpoints for User

### Get all Restaurants
Gives the list of all sorted by rating restaurants (Max rating first).

##### Request
`GET http://localhost:8080/rest/user/restaurants/all`

##### Authentication
User or userless authentication.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                  |
|:------:|----------------------------------------------|
|  id    | A unique identifier for each restaurant      |
|  name  | Each restaurant's name                       |
| rating | Numerical day rating of each restaurant      |

##### Response
```
[
{"id":2,"name":"Restaurant2","rating":2},
{"id":1,"name":"Restaurant1","rating":1},
{"id":3,"name":"Restaurant3","rating":0}
]
```

### Get details of a Restaurant
Gives full details about a restaurant including id, name and rating.

##### Request
`GET http://localhost:8080/rest/user/restaurants/RESTAURANT_ID`

##### Authentication
User or userless authentication.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                             |
|:------:|-----------------------------------------|
|  id    | A unique identifier for this restaurant |
|  name  | This restaurant's name                  |
| rating | Numerical day rating of the restaurant  |

##### Response
```{"id":1,"name":"Restaurant1","rating":1}```

### Get a Restaurant's day menu 
Gives restaurant's day menu.

##### Request
`GET http://localhost:8080/rest/user/restaurants/RESTAURANT_ID/menu`

##### Authentication
User or userless authentication.

##### Parameters
Not supported

##### Response Fields
| Field  | Description                                        |
|:------:|----------------------------------------------------|
|  id    | A unique identifier for each dish                  |
| title  | A name for each dish                               |
| price  | Numerical price for each dish. Price keep in cents |  

##### Response
```
[
{"id":2,"title":"fish","price":15000},
{"id":3,"title":"chicken","price":5000}
]
```

### Vote for a Restaurant
Allows authorized users vote for restaurant. If user votes again the same day:
- If it is before 11:00 we assume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed.

##### Request
`PUT http://localhost:8080/rest/user/restaurants/RESTAURANT_ID/vote`

##### Authentication
Mast user authentication.

##### Parameters
Not supported

##### Response Fields
Not supported

### Get User's details
Gives full details about authorized user including id, name, email, password and set of roles.

##### Request
`GET http://localhost:8080/rest/user/profile`

##### Authentication
Mast user authentication.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                            |
|:--------:|--------------------------------------------------------|
|    id    | A unique identifier for authorized user                |
|   name   | Authorized user's name                                 |
|   email  | Authorized user's unique email                         |
| password | Authorized user's password                             |
|   roles  | A set of roles for authorized user (user or/and admin) |

##### Response
```{"id":1,"name":"user1","email":"user1@mail.ru","password":"qwerty","roles":["USER"]}```

### Update User's details
Allows authorized users to update some their details (name and password).

##### Request
`PUT http://localhost:8080/rest/user/profile`

##### Authentication
Mast user authentication.

##### Parameters
Not supported

##### Response Fields
|  Field   | Description                                                 |
|:--------:|-------------------------------------------------------------|
|    id    | A unique identifier for authorized user (not changeable)    |
|   name   | New user's name                                             |
|   email  | Authorized user's unique email (not changeable by user)     |
| password | New user's password                                         |
|   roles  | A set of roles for authorized user (not changeable by user) |

### Delete a User
Allows authorized users to delete himself.

##### Request
`DELETE http://localhost:8080/rest/user/profile`

##### Authentication
Mast user authentication.

##### Parameters
Not supported

##### Response Fields
Not supported

