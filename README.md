# Introduction
This is a playground for testing Spring Boot &amp; JPA/Hibernate

# Run
### Backend
Install [MySQL](https://dev.mysql.com/downloads/mysql/) on your system.

Copy the contents of the `dot_env_example` to a new file called `.env` in the same directory and adjust credentials for production DB.

Install [Maven](https://maven.apache.org/download.cgi) and run `mvn install` from the root directory (where the `pom.xml` file is located) to install the required dependencies.

To run, execute `mvn spring-boot:run` from the root directory. 
The backend will be available via http://localhost:8080. 

### Frontend

# Test

# Development
### Backend
Copy `settings.xml` from the root directory to `/Users/<User_Name>/.m2` (Windows, Mac) or `/home/<User_Name>/.m2` (Linux).

For code formatting, run `mvn spring-javaformat:apply` from the root directory.
It performs automatic code formatting.
