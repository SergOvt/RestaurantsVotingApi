### Vote for a Restaurant
Allows authorized users vote for restaurant. If user votes again the same day:
- If it is before 11:00 we assume that he changed his mind.
- If it is after 11:00 then it is too late, vote can't be changed.

#### Request
`PUT http://localhost:8080/rest/user/restaurants/RESTAURANT_ID/vote`

#### Authentication
Mast user authentication.

#### Parameters
Not supported

#### Response Fields
Not supported
