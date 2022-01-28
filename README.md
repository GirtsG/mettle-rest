# Mettle REST API

This REST API microservice is to for creating feature objects. The end-points are:
```
POST /features - Creates new feature
PUT /features/{id}/user/{username} - Enable feature for user with username
DELETE /features/{id}/user/{username} - Disable feature for user with username
GET /features/enabled - Get all enabled features
```

Start application using command:
```
./gradlew bootRun
```

It should be available on http://localhost:8080

DB console is available at http://localhost:8080/db-console
```
username: sa
password:
Driver class: org.h2.Driver
JDBC URL: jdbc:h2:mem:db
```

Invoke REST API requests, for example, in POSTMAN to get results, in POST /features should be JSON body provided like:

```
{
    "name": "Some feature"
}
```

There are 2 users automatically created on each startup - one with username "user", other with username "admin", so for example, you can enable user with username "user" for feature with ID=1 like following:
```
PUT /features/1/user/user
```

The same URL to disable feature with ID=1 for "user" (delete association):
```
DELETE /features/1/user/user
```