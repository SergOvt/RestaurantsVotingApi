#### Get all Restaurants
`curl -i http://localhost:8080/rest/admin/restaurants/all`

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 05 Jan 2018 16:37:46 GMT
 
[{"id":2,"name":"Restaurant2","rating":2},{"id":1,"name":"Restaurant1","rating":1},{"id":3,"name":"Restaurant3","rating":0}]
```

#### Get details of a Restaurant
`curl -i http://localhost:8080/rest/admin/restaurants/2`

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 05 Jan 2018 16:36:22 GMT
 
{"id":2,"name":"Restaurant2","rating":2}
```

#### Get a Restaurant's day menu
`curl -i http://localhost:8080/rest/admin/restaurants/1/menu`

```
HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 05 Jan 2018 16:41:39 GMT
 
[{"id":2,"title":"fish","price":15000},{"id":3,"title":"chicken","price":5000}]
```

#### Add a Restaurant
`curl -i -X POST -d '{"name":"New restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants`

```
HTTP/1.1 201 
Location: http://localhost:8080/rest/admin/restaurants/4
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 05 Jan 2018 19:24:11 GMT
 
{"id":4,"name":"New restaurant","rating":0}
```

