## Development Plan for a Shipment Tracker Application (Jira Board Style)
#### Epic 1: Project Setup
##### User Story 1.1: Set up project structure and basic configurations

Tasks: Create a Git repository and initialize the Spring Boot application .

Define Maven/Gradle dependencies for Spring Boot, Spring Web, Spring Data, Spring Cloud (Eureka/Config), and Spring Security.
Set up profiles for local, dev, and prod environments.
Integrate Swagger for API documentation.
#### Epic 2: User Management Microservice
##### User Story 2.1: Implement User Registration and Login

Tasks: Define user entity, DTOs, and repository.

Set up Spring Security with JWT authentication.
Create REST APIs for registration and login.
Write unit and integration tests.

##### User Story 2.2: Add Role-Based Access Control
Tasks: Implement roles like ADMIN, MANAGER, and USER.
Configure security filters for role-based endpoint access.

#### Epic 3: Shipment Management Microservice
##### User Story 3.1: Set up Shipment Tracking API 

Tasks: Define shipment entity, DTOs, and repository.

Create CRUD APIs for shipments (create, update status, delete, view details).
Add validation for shipment attributes like tracking ID, sender/receiver details, and statuses.
##### User Story 3.2: Implement Shipment Status Updates
Tasks:
Create an endpoint to update shipment status (e.g., "Dispatched," "In Transit," "Delivered").
Implement event-driven communication (e.g., via RabbitMQ/Kafka) for status changes.
Store status history in the database.
#### Epic 4: Notification Microservice
##### User Story 4.1: Set up Notifications for Shipment Updates
Tasks:
Create a listener for shipment status change events.
Integrate an email or SMS gateway.
Send notifications to users upon shipment status changes.
Write unit and integration tests.
#### Epic 5: Discovery and Configuration Service
##### User Story 5.1: Implement Service Discovery with Eureka
Tasks:
Set up Eureka Server for service discovery.
Register microservices (User, Shipment, Notification) with Eureka.
Configure Eureka health checks.
##### User Story 5.2: Set up Centralized Configuration with Spring Cloud Config
Tasks:
Create a Spring Cloud Config Server.
Store application configuration in Git for centralized access.
Update microservices to fetch configuration from the config server.
#### Epic 6: API Gateway and Load Balancing
##### User Story 6.1: Implement an API Gateway
Tasks:
Set up Spring Cloud Gateway for routing and rate limiting.
Configure routes for microservices (User, Shipment, Notification).
Implement global exception handling and request logging.
##### User Story 6.2: Add Load Balancing
Tasks:
Configure Ribbon/Load Balancer in API Gateway.
Test load balancing between multiple instances of microservices.
#### Epic 7: Database Setup and Management
##### User Story 7.1: Set up Databases for Microservices
Tasks:
Create separate databases for User, Shipment, and Notification services.
Use Spring Data JPA for ORM and define repositories.
Configure Flyway or Liquibase for database migrations.
##### User Story 7.2: Implement Data Backup and Recovery
Tasks:
Set up periodic database backups.
Test backup and recovery scenarios.
#### Epic 8: Performance and Monitoring
##### User Story 8.1: Implement Metrics and Monitoring
Tasks:
Integrate Prometheus for metrics collection.
Set up Grafana dashboards for visualizing performance.
Add log aggregation with ELK Stack (Elasticsearch, Logstash, Kibana).
##### User Story 8.2: Conduct Load Testing
Tasks:
Use JMeter or Gatling to simulate load on APIs.
Optimize performance bottlenecks in the application.
#### Epic 9: Deployment
##### User Story 9.1: Set up CI/CD Pipeline
Tasks:
Configure Jenkins/GitHub Actions for build and deployment.
Write Dockerfiles for containerizing microservices.
Set up Kubernetes or Docker Swarm for container orchestration.
##### User Story 9.2: Deploy to Cloud
Tasks:
Use AWS/Azure/GCP for hosting microservices.
Configure auto-scaling and load balancing.
Perform final end-to-end testing.
#### Epic 10: Documentation and Handover
##### User Story 10.1: Document APIs and Architecture
Tasks:
Update Swagger documentation for all APIs.
Create architecture diagrams (service interaction, database schema).
Write user guides and deployment manuals.
##### User Story 10.2: Conduct Knowledge Transfer
Tasks:
Organize handover sessions for developers and support teams.
Provide training for using monitoring and troubleshooting tools.



## Database schema
use dbdiagram.io to view the tables 
un-comment first 

[//]: # ()
[//]: # ()
[//]: # (Table user{)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (id long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (name string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (email string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (password string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (phonenumber string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (updatedAt timestamp)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (createdAt timestamp)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (table tracking_Details{)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (id long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (location string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (delivery_status enum)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (shimentId long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (table shipment{)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (id long [primary key])

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (originAddress long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (destinationAddress long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (shipmentStatus enum)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (userId long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (table address{)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (id long [primary key])

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (addressLine1 string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (addressLine2 string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (pincode string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (state string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (table package_details{)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (id long [primary key])

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (shipment long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (packageType string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (packageValue string)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (weight long)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (})

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Ref: shipment.userId > user.id)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Ref: tracking_Details.shimentId > shipment.id)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Ref: package_details.shipment > shipment.id)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Ref: shipment.originAddress - address.id)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # (Ref: shipment.destinationAddress - address.id)

[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
[//]: # ()
