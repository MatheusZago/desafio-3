# User api with notify microsrevice.

## Challenge 03 - Compass UOL - SPRINGBOOT_AWS_AGO24

Repository created for Challenge 03 - Week 13, part of the **SPRING BOOT AWS** scholarship program by Compass UOL.

## Dependencies

Dependency management is done using **Maven**, so to run the application locally, the `pom.xml` file must include the following dependencies:

1. **spring-boot-starter-data-jpa**  

2. **spring-boot-starter-web**  

3. **spring-boot-devtools**  

4. **mysql-connector-j**  

5. **apache-http-components**

6. **google-code-gson**  

7. **springframework-kafka**  

8. **io-jsonwebtoken**

9. **com-auth0**


To check the versions of each dependency used in the project, see the `pom.xml` file.

---

### docker-compose.yml

This file is a Docker Compose file that defines the configuration for a MySQL database service. Below is how to use it:

### Prerequisites
- **Docker**: Ensure that Docker is installed on your machine. You can download and install Docker [here](https://www.docker.com/get-started).
- **Docker Compose**: Docker Compose is usually included with the Docker installation. You can check if it is installed by running `docker-compose --version` in the terminal.

## Structure

1. **api:** Package used to store the api user application.
1. **notify:** Package used to store the notify microservice
1. **kafka:** Package used to store the kafka server properties.
1. **Collections:** Package used to store the request collections for postman.
1. **docker-compose:** Docker-compose file used to store and run the application.



### Step by Step to Run the Project

1. **Clone the Repository**: First, clone the project repository to your local machine:
   ```bash
   git clone https://github.com/MatheusZago/desafio-3
   cd <PROJECT_FOLDER_NAME>
   ```


2. **Start the Container**: In the terminal, within the project folder, run the following command to start the container:
   ```bash
    docker-compose up -d
   ```



3. **Check running Containers using Docker Desktop**

        Open docker-desketop and check if the containers are running.

4. **Import Collection**

        import the requisitions from the Colections folders to postman in order to test the running api.

        (Obs: I was not able to implement the token validation in the update-password method.)

5. **Test the requests**

Use the requests to test the api functionality, below there are exemples for each request:

   ```bash
Request for register user

      {
    "username": "eva",
    "email": "eva@email.com",
    "password": "eva",
    "cep": "01001000"
    }  

-------------------

Request for token generation

   ```bash
 
    {
    "username": "eva",
    "password": "eva"
    }

-------------------

  Request for password update

  {
    "username": "eva",
    "oldPassword": "evanescence",
    "newPassword": "eva"
}

```

After realizing a register or update requisition, check the notify_service container, it will show messages delivered by kafka.

#### Examples:
-Received message: User: eva used a CREATE function </br>
-Received message: User: eva used an UPDATE function


**Stopping the Container**
When you finish using the service, you can stop the container with the command:

docker-compose down

With these steps, you should be able to test the parking service locally using Docker. Ensure that all services are working correctly and that you can access the database as needed.

