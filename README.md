# Project Discription
This project is a bankaccount magnment system.

it alows employees to login. employees can:
  - create and delete users
    
it alows customers to login. cuesomter can:
  - veiw their details
  - create transactions

# Future Goals
more features are going to be added to the front end.  
the back end has full functonality, but needs more work done to ensure invalid input is properly delt with.

# Implementation Details
the front end is done in react using typescript and run using vite.  
the back end is done in java using spring boot and jpa.

# run the program
there must be a database setup as decribed in \backend\springBootAPI\src\test\resources\schema.sql

run the backend:
  - go to the springBootAPI folder (in the backend folder)
  - run mvn clean install -DskipTests (note the mocking setup is currently not working, it is using the actual database)
  - run cd target
  - run java -jar springBootAPI-0.0.1-SNAPSHOT.jar
    
run front end:
  - go to the front end folder
  - npm run dev -- --port=5173
go to http://localhost:5173/ to access the front end or use postman to access the backend directly

