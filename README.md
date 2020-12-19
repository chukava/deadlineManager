## Requirements:
1. jdk 11
2. gradle
3. MySql
4. Docker

## Project run:

    1. clone this repository 
    2. setup the database(instrucrions below)
    3. make sure your standart port(8080) is free
    4. run apllication (make jar using gradle and then run jar file)
    5. run insert_skript.sql 
    6. you can use files from package http_requests for testing web services
       
                
## Database setup:
   
First of all, you would need to install the database system MySQL 5.0. http://dev.mysql.com/downloads/.

#### To start using MySql
    
    • sudo mysql -p
#### For having access to database from project you need to create a database named “deadlineManager” using MySQL. 
    
    • create database deadlineManager;
#### Create user 
    
    • create user ‘mysql’@‘%’ identified by ‘mysql‘;
    • grant all on deadlineManager.* to ‘mysql’@‘%’;