# Technical Test - API Rest

This is a REST API developed as part of a Technical Test to manage products and prices. It is built with Spring Boot and packaged in a Docker container. Below, you'll find instructions on how to run the project, how to run tests, and some code specifications.

## Requisitos

To run and test this project, you need to have the following installed:

- Docker (to run the application in a container).
- Maven (for building and running tests).
- Java 17 or higher (to run the project).

## Running the Project

### 1. Clone the Repository

First, clone this repository to your local machine:

```bash
git clone https://github.com/jaimegarita/technical_test.git
cd technical_test
```

### 2. Run the Project Locally
If you prefer to run the project locally without Docker, use Maven to compile and run the application.

```bash
./mvnw spring-boot:run
```

### 3. Run the Project in Docker
To run a container, follow these steps:

```bash
docker build -t technical_test .
docker run -p 8080:8080 technical_test
```

### 4. Run Tests

Unit tests are executed when building the image. If they fail, the image will not be built.

#### To run tests locally

 ```bash
./mvnw test
```

### 5. E2E Tests with Postman

E2E tests can be run using the Postman collection found in the PRICES_E2E.postman_collection.json file inside the /technical_test directory.

#### 1. Importar la colección en Postman

- Open Postman
- Click on the import icon in the top-left corner.
- Select the PRICES_E2E.postman_collection.json file from the directory where you cloned the repository.
- Click Import.

#### 2. Run the Collection Tests in Postman
- Select the imported collection in Postman.
- Click on Run to execute all the tests defined in the collection.
- Postman will execute the API requests and show the test results.