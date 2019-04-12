[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8b3f4e6b450241489f9e51704e132154)](https://www.codacy.com/app/synya/where-to-go?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=synya/where-to-go&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/synya/where-to-go.svg?branch=master)](https://travis-ci.org/synya/where-to-go)

## Where To Go

### A voting system for deciding where to have lunch

Each restaurant has id, name and set of dishes that cooked at the restaurant. Administrator makes the update of daily menu. Menus of the day consist of items each one with the dish, date and price. 

Use following curl commands to test rest API

------------------------------------------------------

### User's API

------------------------------------------------------

#### Restaurants

##### Get all restaurants for voting

The following `GET` request returns list of restaurants with their id, name and menu of the day which consists from list of dish names and their prices. Pay attention, that all prices are presented in cents of USD.  
 
*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants"  --user user@gmail.com:userPassword`

------------------------------------------------------

#### Votes

Each valid vote contains id, restaurant name and date/time when vote was made.

##### Get all user's votes

The following `GET` request returns all user's votes were made.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes" --user user@gmail.com:userPassword`

##### Get all user's votes between certain dates 

The following `GET` request returns all user's votes were made between two dates.

The request accepts two following parameters: `startDate` and `endDate` both in `ISO_DATE` format  

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes?startDate=2019-03-20&endDate=2019-03-21" --user user@gmail.com:userPassword`

If parameters are empty the request will return all user's votes between 01.01.2000 and 01.01.3000

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes?startDate=&endDate=" --user user@gmail.com:userPassword`

##### Get today user's vote

The following `GET` request returns either user's today vote information, or empty object if no vote has been made today yet.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes/today" --user user@gmail.com:userPassword`

##### Make vote for a restaurant

The following `POST` request stores user's vote for a restaurant.

The request accepts one parameter: `restaurantId` - an id of the restaurant the user votes for.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes/today?restaurantId=100003" --user user@gmail.com:userPassword`

##### Update vote

The following `PUT` request makes update of previously made vote.

The request accepts one parameter: `restaurantId` - an id of the restaurant the user votes for. 
Pay attention that it's allowed to change the opinion only before 11:00

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes/today?restaurantId=100002" --user user@gmail.com:userPassword`

------------------------------------------------------

#### User's profile

##### Get user profile

The following `GET` request returns full user's information.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/profile" --user user@gmail.com:userPassword`

##### Update user profile

The following `PUT` request updates user's information.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/profile" -d '{"name":"Updated Name","email":"user@gmail.com","password":"userPassword"}' -H "Content-Type: application/json" --user user@gmail.com:userPassword`

##### Delete user profile

The following `DELETE` request deletes user from service.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/profile" --user user@gmail.com:userPassword`

------------------------------------------------------

### Administrators's API

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

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants" -d '{"name":"The New Place To Launch At"}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

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

The following `GET` request returns all stored menus of the day.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/between/?startDate=2019-03-20&endDate=2019-03-21" --user admin@gmail.com:adminPassword`

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

The following  `PUT` request adds new today menu item.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items/100030" -d '{"id":100030,"dish":{"id":100010,"name":"Meet The Meat","restaurant":{"id":100003,"name":"The Restaurant at the End of the Universe"}},"date":"2019-04-07","price":2560}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete today menu item

The following  `DELETE` request deletes existed today menu item.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/restaurants/menus/daily/today/items/100030" --user admin@gmail.com:adminPassword`

------------------------------------------------------

#### Users management

##### Get all users

The following `GET` request returns all stored menus of the day.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/users" --user admin@gmail.com:adminPassword`

##### Get user

The following `GET` request returns user with `id`.

*   `curl -X GET "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" --user admin@gmail.com:adminPassword`

##### Get user by Email

The following `GET` request returns user with particular email.

*   `curl -X GET "http://localhost:8080/where-to-go//rest/api-v1/management/users/by?email=user@gmail.com" --user admin@gmail.com:adminPassword`

##### Add new user

The following  `POST` request adds new user.

*   `curl -X POST "http://localhost:8080/where-to-go/rest/api-v1/management/users" -d '{"name":"New User","email":"newuser@gmail.com","password":"password","enabled":true,"registered":"2019-04-08T11:46:48.632158","roles":["ROLE_USER"]}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Update user information

The following  `PUT` request updates existing user.

*   `curl -X PUT "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" -d '{"id":100000,"name":"Updated Name","email":"user@gmail.com","password":"userPassword","enabled":true,"registered":"2019-04-08T11:46:48.841556","roles":["ROLE_ADMIN"]}' -H "Content-Type: application/json" --user admin@gmail.com:adminPassword`

##### Delete user

The following  `DELETE` request deletes existing user.

*   `curl -X DELETE "http://localhost:8080/where-to-go/rest/api-v1/management/users/100000" --user admin@gmail.com:adminPassword`