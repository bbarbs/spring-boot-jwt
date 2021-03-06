[![Build Status](https://travis-ci.org/bbarbs/spring-boot-jwt.svg?branch=master)](https://travis-ci.org/bbarbs/spring-boot-jwt)

> Spring security using jwt https://jwt.io/

## Getting Started
This is a sample token authentication project using JWT. This project show how to manage both the roles and privileges/permissions of the user.

### Feature
* Swagger2
* [jjwt](https://github.com/jwtk/jjwt)
* [redis](https://redis.io/)
* Spring AOP(for logging) and also using ApplicationListener for event logging.

### Prerequisites
* redis

### Installing
Since we are using redis to store token make sure to run the redis server.
For windows you can download it [here](https://github.com/dmajkic/redis/downloads)

### Configuration
You can configure the jpa hibernate dll, by default it is set to "create-drop"
```
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
```

## Testing 
* You can use the swagger-ui to test the api or postman. Much better to use postman so you can login using the api "/login" which is not displayed in the swagger-ui.
* You can add first a user using this endpoint:
```
http://localhost:8080/users
```
* Then you can add the user role through this endpoint:
```
http://localhost:8080/users/{userId}/roles
```
* Then you can check the h2 db
```
http://localhost:8080/h2/
```
* To test like adding rules through swagger api
```
http://localhost:8080/swagger-ui.html
http://localhost:8080/v2/api-docs
```
* After that you can proceed to authentication and authorization process.

### How it Works
* For Authentication it use the spring UsernamePasswordAuthenticationFilter class which will intercept the request for login POST ("/login). See: [JwtAuthenticationFilter.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/security/JwtAuthenticationFilter.java)
* In every login it will generate a SecretKey using KeyGenerator Advance Standard Encryption(AES) then use it to sign the token.
```
public SecretKey generateKey() throws NoSuchAlgorithmException {
     return KeyGenerator.getInstance("AES").generateKey();
}
```
* Token, SecretKey and Claims are stored in redis. See: [JwtModel.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/jwt/JwtModel.java)
* For Authorization it use also the spring BasicAuthenticationFilter class to filter request. See: [JwtAuthorizationFilter.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/security/JwtAuthorizationFilter.java). This checks the token if it exists in the redis, then validates it using the SecretKey which is save also in the redis.

### Built With
* Spring Boot
* Gradle

### Jwt Library
For more information regarding with the jwt library being used you can check it [here](https://github.com/jwtk/jjwt).

