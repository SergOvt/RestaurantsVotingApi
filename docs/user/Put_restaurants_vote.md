### Vote for a Restaurant
Allows authorized users vote for restaurant (RESTAURANT_ID). If user votes again the same day:
- If it is before 11:00 we assume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed.

#### Request
`PUT http://localhost:8080/rest/user/restaurants/RESTAURANT_ID`

#### Authentication
Mast user authentication.

#### Parameters
Not supported

#### Response Fields
Not supported

#### Errors
The method may return an HTTP 423 error if user votes again and it is after 11:00.
