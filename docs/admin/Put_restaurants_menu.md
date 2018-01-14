### Put restaurant's day Menu
Allows admins to add restaurant's day menu or update, if menu for current date is exist.

#### Request
`PUT http://localhost:8080/rest/admin/restaurants/RESTAURANT_ID/menu`

#### Authentication
User must be admin.

#### Parameters
Not supported

#### Response Fields
| Field  | Description                                                                      |
|:------:|----------------------------------------------------------------------------------|
| title  | A name for each new dish (mast be not empty)                                     |
| price  | Numerical price for each new dish. Price keep in cents (min = 0, max = 1 000 000)|
