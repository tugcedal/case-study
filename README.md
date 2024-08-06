# Stock Exchange Application

## Description
This is a Spring Boot application that manages stock exchanges and stocks. It uses an H2 in-memory database, Swagger UI for API documentation, and JWT authentication for securing the endpoints.

## Features
- Each Stock exchange can have many stocks 
- Stock exchange having less than 5 stocks can not be live in the market. 
- The particular stock can be listed in many stock exchanges and all the properties of a stock will remain same in all the exchanges
- H2 in-memory database for data storage.
- Swagger UI for interactive API documentation.
- JWT authentication for securing REST endpoints.

## Requirements
- Docker

## Setup

### Clone the repository
bash
git clone https://github.com/tugcedal/homework.git
cd your-repository

## How to run and stop the application

### Build and Start the Docker Container
- Following commands needs to be executed inside of the project folder

- Use the provided docker_build_and_start.sh script to build and start the Docker container.
sh ./docker_build_and_start.sh

### Stop the Docker Container
- Use the provided docker_stop_and_optional_remove.sh script to stop the Docker container.
sh ./docker_stop_and_optional_remove.sh

- To stop the Docker container and remove the image, use the --rmi parameter.
sh ./docker_stop_and_optional_remove.sh --rmi

## How to use the application 
- Once you run the application you can follow the below steps

### Swagger UI
- You can access the Swagger UI without any authantication.
- From the UI you can easly access the example of request bodies.
- Swagger UI is available at http://localhost:8080/swagger-ui/index.html#/

### Postman
- You can import stock exchange.postman_collection.json to your postman
- You can use folders: Stock, Stock Exchange and Authorization to use Rest API Requests

### H2 Database
The H2 database console is available at http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

### JWT Authentication
To access secured endpoints, you need to authenticate and obtain a JWT token.

### Obtain JWT Token
- Make a POST request to /authentication/register with the following JSON body:

{
  "data": {
    "username": "user",
    "password": "pass"
  }
}

- Make a POST request to /authentication/login with the following JSON with the same body, this response will include JWT token
- Add the JWT token to the Authorization header for secured endpoints:
Authorization: Bearer your-jwt-token
