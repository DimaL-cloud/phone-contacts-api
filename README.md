# phone-contacts-api
A service for keeping all contacts together

## Stack 💻
Java, Spring Boot, Data, Security, PostgreSQL, Lombok, Flyway, Docker, Kubernetes, Gmail SMTP, Azure Blob storage

## System design diagram 💡
![phone-contacts-api-system-design](https://github.com/DimaL-cloud/phone-contacts-api/assets/78265212/096101b1-20d1-478f-b0b1-3d1b14b32827)

## Installation

1. Clone the repository:
```
git clone https://github.com/DimaL-cloud/phone-contacts-api.git
```
2. Configure .env file in resources
```
spring.datasource.url=jdbc:postgresql://${DATASOURCE_HOST:localhost}:5444/phone_contacts_db
spring.datasource.username=admin
spring.datasource.password=admin

spring.flyway.url=jdbc:postgresql://${DATASOURCE_HOST:localhost}:5444/phone_contacts_db
spring.flyway.user=admin
spring.flyway.password=admin

spring.mail.username=example@gmail.com
spring.mail.password=password

azure.storage.connection-string={BLOB STORAGE CONNECTION STRING}
azure.storage.container-name=images
```
3. Start database using Docker Compose:
```
docker-compose up -d
```
4. Navigate to the project directory:
```
cd phone-contacts-api
```
5. Build the project using Maven:
```
mvn package
```
6. Run the Spring Boot app:
```
java -jar target/phone-contacts-api-0.0.1.jar
```
