[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8b3f4e6b450241489f9e51704e132154)](https://www.codacy.com/app/synya/where-to-go?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=synya/where-to-go&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/synya/where-to-go.svg?branch=master)](https://travis-ci.org/synya/where-to-go)

## Where To Go

### A voting system for deciding where to have lunch

The task is:

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

*   2 types of users: admin and regular users
*   Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
*   Menu changes each day (admins do the updates)
*   Users can vote on which restaurant they want to have lunch at
*   Only one vote counted per user
*   If user votes again the same day: If it is before 11:00 we asume that he changed his mind. If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

------------------------------------------------------

### Public area API

------------------------------------------------------

#### Restaurants

##### Get all restaurants for today voting. No authentification for this request is needed

The following `GET` request returns list of restaurants with their id, name and menu of the day which consists from list of dish names and their prices. Pay attention, that all prices are presented in cents of USD.  
 
*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants"`

------------------------------------------------------

### User's area API

------------------------------------------------------

Each valid user vote contains id, restaurant name and date/time when the vote was made.

##### Get all restaurants for today voting with user's vote

The following `GET` request returns similar to previous list of restaurants with only one distinction: each reastaurant has `elected` boolean flag to show if the user made vote for the restaurant today.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/today" --user user@gmail.com:userPassword`

##### Vote for a restaurant

The following `POST` request stores user's vote for a restaurant.

The request accepts one parameter: `id` - an id of the restaurant the user votes for.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/today?id=100003" --user user@gmail.com:userPassword`

##### Update vote for another restaurant

The following `PUT` request makes an update of previously made vote.

The request accepts one parameter: `id` - an id of the restaurant the user votes for. The request will return error message if user change his opinion after 11:00.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/today?id=100002" --user user@gmail.com:userPassword`

##### Get all user's votes

The following `GET` request returns all user's votes were made.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/" --user user@gmail.com:userPassword`

##### Get all user's votes between certain dates 

The following `GET` request returns all user's votes were made between two dates.

The request accepts two following parameters: `startDate` and `endDate` both in `ISO_DATE` format  

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/between?startDate=2019-03-20&endDate=2019-03-21" --user user@gmail.com:userPassword`

If parameters are empty the request will return all user's votes between 01.01.2000 and 01.01.3000

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/votes/restaurants/between?startDate=&endDate=" --user user@gmail.com:userPassword`

------------------------------------------------------

#### User's profile

##### Get user profile

The following `GET` request returns user's information: id, name and email.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/profile" --user user@gmail.com:userPassword`

##### Update user profile

The following `PUT` request updates user's information.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/profile" -d '{"name":"Updated Name","email":"user@gmail.com","password":"userPassword"}' -H "Content-Type: application/json" --user user@gmail.com:userPassword`

##### Delete user profile

The following `DELETE` request deletes user from service.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/profile" --user user@gmail.com:userPassword`

------------------------------------------------------

### Administrators's area API

------------------------------------------------------

#### Restaurant management

##### Get all restaurants

The following `GET` request returns all stored restaurants.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants" --user admin@gmail.com:adminPassword`

##### Get restaurant

The following `GET` request returns restaurant with `id`.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003" --user admin@gmail.com:adminPassword`

##### Add new restaurant

The following  `POST` request adds new restaurant.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants" -d '{"name":"The New Place To Have Launch At"}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Update restaurant

The following  `PUT` request updates the restaurant information.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100002" -d '{"name":"Burger King Rebranded"}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete restaurant

The following  `DELETE` request deletes existed restaurant.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100002" --user admin@gmail.com:adminPassword`

------------------------------------------------------

#### Restaurant's dishes management

##### Get all dishes of restaurant with `id`

The following `GET` request returns all stored restaurants.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003/dishes" --user admin@gmail.com:adminPassword`

##### Get restaurant's dish with `id`

The following `GET` request returns dish with `id` of the restauranat with.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003/dishes/100013" --user admin@gmail.com:adminPassword`

##### Add new restaurant's dish

The following  `POST` request adds new dish to the restaurant with `id`.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003/dishes" -d '{"name":"The New And Delicious Dish"}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Update restaurant's dish

The following  `PUT` request updates dish of the restaurant with `id`.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003/dishes/100011" -d '{"name":"The Pan Galactic Gargle Blaster Updated"}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete restaurant's dish

The following  `DELETE` request deletes existed dish of the restaurant with `id`.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/100003/dishes/100011" --user admin@gmail.com:adminPassword`

------------------------------------------------------

#### Restaurant's menu of the day history view

##### Get all menus of the day of the restaurant with `id`

The following `GET` request returns all stored menus of the day.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily" --user admin@gmail.com:adminPassword`

##### Get all menus of the day of the restaurant with `id` between dates

The following `GET` request returns all stored menus of the day between dates.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/between?startDate=2019-03-20&endDate=2019-03-21" --user admin@gmail.com:adminPassword`

------------------------------------------------------

#### Restaurant's today menu items management

##### Get all today menu items

The following `GET` request returns all stored menus of the day.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items" --user admin@gmail.com:adminPassword`

##### Get today menu item with `id`

The following `GET` request returns all stored menus of the day.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items/100029" --user admin@gmail.com:adminPassword`

##### Add new today menu item

The following  `POST` request adds new today menu item.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items?dishId=100012&price=10020" --user admin@gmail.com:adminPassword`

##### Update today menu item

The following  `PUT` request updates today menu item with `id`.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items/100030" -d '{"id":100030,"dish":{"id":100010,"name":"Meet The Meat","restaurant":{"id":100003,"name":"The Restaurant at the End of the Universe"}},"date":"2019-04-07","price":2560}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete today menu item

The following  `DELETE` request deletes existed today menu item.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items/100030" --user admin@gmail.com:adminPassword`

------------------------------------------------------

#### Users management

##### Get all users

The following `GET` request returns list of all users with their full information: id, name, email, date of registration, enabled status and set of roles.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/users" --user admin@gmail.com:adminPassword`

##### Get user

The following `GET` request returns user with `id` with his full information: id, name, email, date of registration, enabled status and set of roles.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" --user admin@gmail.com:adminPassword`

##### Get user by Email

The following `GET` request returns user with particular email.

*   `curl -X GET "http://localhost:8080/where-to-go//rest/api-v1/management/users/by?email=user@gmail.com" --user admin@gmail.com:adminPassword`

##### Add new user

The following  `POST` request adds new user.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/users" -d '{"name":"New User","email":"newuser@gmail.com","password":"password","enabled":true,"roles":["ROLE_USER"]}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Update user information

The following  `PUT` request updates existing user.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" -d '{"id":100000,"name":"Updated Name","email":"user@gmail.com","password":"userPassword","enabled":true,"roles":["ROLE_ADMIN"]}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete user

The following  `DELETE` request deletes existing user.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" --user admin@gmail.com:adminPassword`