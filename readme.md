[![Codacy Badge](https://api.codacy.com/project/badge/Grade/8b3f4e6b450241489f9e51704e132154)](https://www.codacy.com/app/synya/where-to-go?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=synya/where-to-go&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/synya/where-to-go.svg?branch=master)](https://travis-ci.org/synya/where-to-go)

## Where To Go

### A voting system for deciding where to have lunch.

#### curl commands to test rest API

------------------------------------------------------

#### Restaurants and voting

------------------------------------------------------
##### Get all restaurants for voting:

The request returns restaurant id, restaurant name, menu of the day which consists from list of dish names and their prices. Pay attention, that all prices are presented in cents of USD.  
 
 * `curl -X GET http://localhost:8080/where-to-go/rest/api-v1/restaurants`