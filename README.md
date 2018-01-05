[![Build Status](https://travis-ci.org/bbarbs/spring-boot-jwt.svg?branch=master)](https://travis-ci.org/bbarbs/spring-boot-jwt)

> Spring security using jwt https://jwt.io/

## Getting Started
This is a sample token authentication project using JWT, it manage both the role and privilege/permission of the user.

### Feature
* Swagger2
* [jjwt](https://github.com/jwtk/jjwt)
* [redis](https://redis.io/)

### Prerequisites
* redis

### Installing
Since we are using redis to store token make sure to run the redis server.
For windows you can download it [here](https://github.com/dmajkic/redis/downloads)

### How it Works
* Every genetared token has SecretKey with is also generated using KeyGenerator.
```
public SecretKey generateKey() throws NoSuchAlgorithmException {
     return KeyGenerator.getInstance("AES").generateKey();
}
```
* Token, SecretKey and Claims are stored in redis.
* Roles and Privileges is prepopulated during bean creation. See: [DataInitializer.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/setup/DataInitializer.java)
* For Authentication it use the spring UsernamePasswordAuthenticationFilter class which will intercept the request for login POST ("/login). See: [JwtAuthenticationFilter.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/security/JwtAuthenticationFilter.java)
* For Authorization it use also the spring BasicAuthenticationFilter class to authorize request. See: [JwtAuthorizationFilter.java](https://github.com/bbarbs/spring-boot-jwt/blob/master/src/main/java/com/auth/core/security/JwtAuthorizationFilter.java)

### Built With
* Spring Boot
* Gradle

### Jwt Library
For more information regarding with the jwt library being used you can check it [here](https://github.com/jwtk/jjwt).

