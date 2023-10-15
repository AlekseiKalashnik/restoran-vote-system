# Restaurant Voting System App

---

### Task:
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.


Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users.
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price).
Menu changes each day (admins do the updates).
Users can vote on which restaurant they want to have lunch at.
Only one vote counted per user.
If user votes again the same day:
if it is before 11:00 we assume that he changed his mind,
if it is after 11:00 then it is too late, vote can't be changed.
Each restaurant provides a new menu each day.

### Stack: 
[JDK 17](http://jdk.java.net/17/), Spring Boot 2.5, Lombok, JUnit 5, Mockito, DATA Jpa, H2, Swagger/OpenAPI 3.0

---

### Run: 
`mvn spring-boot:run` in root directory.

---

### [REST API documentation](http://localhost:8080/swagger-ui.html)  
* Сredentials:
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```
