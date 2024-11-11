# Project Discription
This project is a bankaccount magnment system.

it alows employees to login. employees can:
  - create and delete customers and create, delete and update accounts
    
it alows customers to login. cuesomter can:
  - veiw their details
  - veiw their accounts and account transactions
  - create transactions

# future goals
implement JWT for security
add password encryption

# Implementation Details
the front end is done in react using typescript and run using vite.  
the back end is done in java using spring boot and jpa.

# run the program
there must be a mySQL database setup as decribed in \backend\springBootAPI\src\test\resources\schema.sql

run the backend:
  - go to the springBootAPI folder (in the backend folder)
  - run mvn clean install -DskipTests (note the mocking setup is currently not working, it is using the actual database)
  - run cd target
  - run java -jar springBootAPI-0.0.1-SNAPSHOT.jar
    
run front end:
  - go to the front end folder
  - npm run dev -- --port=5173
go to http://localhost:5173/ to access the front end or use postman to access the backend directly

