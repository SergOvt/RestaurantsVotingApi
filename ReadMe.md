[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7b44e357f94c4eb39b5ad58fd90d38bd)](https://www.codacy.com/app/SergOvt/restaurants?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SergOvt/restaurants&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/SergOvt/restaurants.svg?branch=master)](https://travis-ci.org/SergOvt/restaurants)

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

#### For Admin:
| Method |    Group    |   Endpoint    |                                                   Usage                                                              |
|:------:|:-----------:|:-------------:|:---------------------------------------------------------------------------------------------------------------------|
| GET    | restaurants |    `all`      | [Get all Restaurants](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Get_restaurants_all.md)          |
| GET    | restaurants |`RESTAURANT_ID`| [Get details of a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Get_restaurants_id.md)   |
| GET    | restaurants |    `menu`     | [Get a Restaurant's day menu](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Get_restaurants_menu.md) |
| POST   | restaurants |    `add`      | [Add a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Post_restaurants_add.md)            |
| PUT    | restaurants |`RESTAURANT_ID`| [Update a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Put_restaurants_id.md)           |
| DELETE | restaurants |`RESTAURANT_ID`| [Delete a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Delete_restaurants_id.md)        |
| PUT    | restaurants |    `menu`     | [Put a Restaurant's day menu](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Put_restaurants_menu.md) |
| GET    |    users    |    `all`      | [Get all Users](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Get_users_all.md)                      |
| GET    |    users    |   `USER_ID`   | [Get details of a User](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Get_users_id.md)               |
| POST   |    users    |    `add`      | [Add a User](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Post_users_add.md)                        |
| PUT    |    users    |   `USER_ID`   | [Update a User](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Put_users_id.md)                       |
| DELETE |    users    |   `USER_ID`   | [Delete a User](https://github.com/SergOvt/restaurants/blob/master/docs/admin/Delete_users_id.md)                    |

#### For User:
| Method |    Group    |    Endpoint   |                                                  Usage                                                               |
|:------:|:-----------:|:-------------:|:---------------------------------------------------------------------------------------------------------------------|
| GET    | restaurants |     `all`     | [Get all Restaurants](https://github.com/SergOvt/restaurants/blob/master/docs/user/Get_restaurants_all.md)           |
| GET    | restaurants |`RESTAURANT_ID`| [Get details of a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/user/Get_restaurants_id.md)    |
| GET    | restaurants |    `menu`     | [Get a Restaurant's day menu](https://github.com/SergOvt/restaurants/blob/master/docs/user/Get_restaurants_menu.md)  |
| PUT    | restaurants |    `vote`     | [Vote for a Restaurant](https://github.com/SergOvt/restaurants/blob/master/docs/user/Put_restaurants_vote.md)        |
| GET    |  profile    |      `/`      | [Get User's details](https://github.com/SergOvt/restaurants/blob/master/docs/user/Get_user.md)                       |
| PUT    |  profile    |      `/`      | [Update User's details](https://github.com/SergOvt/restaurants/blob/master/docs/user/Put_user.md)                    |
| DELETE |  profile    |      `/`      | [User deletes himself](https://github.com/SergOvt/restaurants/blob/master/docs/user/Delete_user.md)                  |

-----------------------------
## Example curl commands

#### get All Users
`curl -s http://localhost:8080/rest/admin/users/all --user admin1@mail.ru:qwerty`

#### create a User
`curl -s -X POST -d '{"name":"new_user","email":"new_user@mail.ru","password":"qwerty","roles":["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/users/add --user admin1@mail.ru:qwerty`

#### delete a Restaurant
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/1 --user admin1@mail.ru:qwerty`