[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8b3f4e6b450241489f9e51704e132154)](https://www.codacy.com/app/synya/where-to-go?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=synya/where-to-go&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/synya/where-to-go.svg?branch=master)](https://travis-ci.org/synya/where-to-go)

## Where To Go

### A voting system for deciding where to have lunch

#### curl commands to test rest API

------------------------------------------------------

#### Restaurants

------------------------------------------------------
##### Get all restaurants for voting

The request returns restaurant id, restaurant name, menu of the day which consists from list of dish names and their prices. Pay attention, that all prices are presented in cents of USD.  
 
*   `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants`

------------------------------------------------------

#### Votes

Each valid vote contains vote id, restaurant name and date/time when vote was made.

------------------------------------------------------

##### Get all user's votes

The request returns all user's votes were made.

*   `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes`

##### Get all user's votes between certain dates 

The request returns all user's votes were made between two dates.

The request accepts two following parameters: startDate and endDate both in ISO_DATE format  

*   `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes?startDate=2019-03-20&endDate=2019-03-21`

If parameters are empty the request will return all user's votes between 01.01.2000 and 01.01.3000

*   `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes?startDate=&endDate=`

##### Get today user's vote

The request returns either user's today vote information, or result with one boolean `false` in `voteDone` field if no vote has been made today yet.

*   `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants/votes/today`
