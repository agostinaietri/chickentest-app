Chickentest
---------------------------------------

_Description_

- This project serves the purpose of 
managing a farm in a day-to-day basis,
with two kinds of cattle, 
chickens and eggs. It fulfills the following
goals:
  - Me, as a farmer, can own a farm, eggs and
  chickens.
  -  I can also buy and sell them,
  - and finally, I need to be able to see a 
  status report of the farm, including capacity, 
  money and cattle.
---------------------------------------
_Prerequisites_

- To be able to run this program, you should:
  - Have Windows 10 or above installed
  - Have an API platform installed like Postman
  - Have Java 17 installed
  - Have a Java IDE installed 
  (be it Intellij, Eclipse...)
  - Set up a connection with a database in MySQL Workbench
  - Framework: Springboot 3.3.4
---------------------------------------
_Features - Endpoints_

- Chicken controller:
  - (post) localhost:8080/api/v1/chicken/add. Creates a 
  Chicken instance. Example input: 
  {
    "price":5,
    "daysLived":14
    }
  - (get) localhost:8080/api/v1/chicken. Returns a
  list of all Chickens. 
  - (get) localhost:8080/api/v1/chicken/get?id=X.
  Returns a chicken with the specified id.
  - (put) localhost:8080/api/v1/chicken/update/X. 
  Updates Chicken instance. Example input:
    {
    "price":5,
    "daysLived":5
    }
  - (delete) localhost:8080/api/v1/chicken/delete/X.
  Deletes Chicken instance. 
- Egg controller:
  - (post) localhost:8080/api/v1/egg/add. Creates 
  an Egg instance. Example input: 
  {
    "price":5.05,
    "daysLived":5
    }
  - (get) localhost:8080/api/v1/egg. Returns
  a list of all Eggs. 
  - (get) localhost:8080/api/v1/egg/get?id=X
  Returns an Egg with the specified id.
  - (put) localhost:8080/api/v1/egg/update/X.
  Updates Egg instance. Example input:
    {
    "price":6.6,
    "daysLived":6
    }
  - (delete) localhost:8080/api/v1/egg/delete/X.
  Deletes Egg instance.
- Farmer controller:
  - (post) localhost:8080/api/v1/farmer/add.
  Creates a Farmer instance. Example input: 
  {
    "name":"Jack Marston",
    "balance":100,
    "farmLimit":4
    }
  - (get) localhost:8080/api/v1/farmer
  Returns a list of all Farmers. 
  - (get) localhost:8080/api/v1/farmer/get?id=X
  Returns Farmer with specified id. 
  - (put) localhost:8080/api/v1/farmer/update/X.
  Updates Farmer instance. Example input:
    {
    "name":"Arthur Morgan",
    "balance":66.0,
    "farmLimit":25
    }
  - (delete) localhost:8080/api/v1/farmer/delete/X.
  Deletes Farmer instance.
  - (post) localhost:8080/api/v1/farmer/buy/chicken/X.
  Buys Chickens for the specified Farmer id.
  Example input:
    [
    {
    "price":5,
    "daysLived":1
    },
    {
    "price":5,
    "daysLived":1
    }
    ]
  - (delete) localhost:8080/api/v1/farmer/sell/chicken/X.
  Sells Chickens specified in the body of the
  request for the specified Farmer id in the URL.
  Example input: [X, Y] where X, Y stand for Chicken id.
  - (post) localhost:8080/api/v1/farmer/buy/egg/X.
    Buys Eggs for the specified Farmer id.
    Example input:
    [
    {
    "price":5,
    "daysLived":5
    },
    {
    "price":5,
    "daysLived":5
    }
    ]
  - (delete) localhost:8080/api/v1/farmer/sell/egg/X.
    Sells Eggs specified in the body of the
    request for the specified Farmer id in the URL.
    Example input: [X, Y] where X and Y stand for Egg id.
  - (get) localhost:8080/api/v1/farmer/report/X/Y.
  Gets a report of the status of the farm for the Farmer
    (X in the URL, stands for Farmer id). For days to pass,
  we have to specify how many days should pass in the URL (Y,
  stands for days).