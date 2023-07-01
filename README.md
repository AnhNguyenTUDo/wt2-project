# Webtechnologien 2 Project from group-g12

A small web application, where the users can send messages globally (everyone can see the sent messages), 
and able to change, delete their own sent messages. To do that they must first register into
the system (the username must be unique). The users can only access the messages after a succesful login 
with the registered data (username and password).

This project uses the required Specification and frameworks presented in the lecture
  * a persistence layer using the JPA specification,
  * a REST interface* using the JAX-RS or Spring specification,
  * a web front-end (which uses the REST interface) using Angular 15, and 
  * a security/authentication mechanism using Apache Shiro.

## Structure

The application is implemented based on the example06 from the lecture, which also follows a basic 3 layer structure, 
separating the persistence, business and presentation layer.

* **persistence**: A maven module to manage the database entities of the application. For this project, 
three data model are implemented:
  * `DBIdentified`: representing no real entity, its purpose is to generate primary keys for the two entities below.
  * `DBUser`: representing an entity for users, that has attributes id, username and password and messages(DBMessage).
  * `DBMessage`: Representing an entity for messages, that has attributes id, content and sender(DBUser).
  
* **business**: A maven module containing the RESTful application interface. There are four packages:
  * `de.ls5.wt2.aal` has `LearningREST` class for Active Automata Learning Tool ALEX specifically.
  * `de.ls5.wt2.auth` for authentication and authorization purposes. It has three classes:
    * `AdminInitializer` for initializing an admin account.
    * `MyRealm` is a basic realm for basic authentication.
    * `UserREST` for handling authentication, registration and checking the role of the authenticated user using 
    the tools of the Spring-Web specification.
  * `de.ls5.wt2.rest` has `MessageREST` class for managing the message system using the tools of the Spring-Web 
  specification.
  * `de.ls5.wt2.service` has `MessageService` and `UserService` for managing `DBUser` and `DBMessage` in database.
  
* **presentation**: A maven module for the web resources accessed by the users browser, which has
  * Angular framework for user interface implementation
  * Configuration for Shiro (`ShiroConfig.java`) and Angular (`AngularConfig.java`).
  * Main Class `Application`. 
  

## Deployment

By running `mvn clean install -Pwith-frontend` in parent module, a `.war` package (which packs frontend and backend
resources together) under `target` folder 
in `WebApp-Project-presentation` can be found can executed with `java -jar WebApp-Project-presentation-0.1-SNAPSHOT.war`. 
The application can thus be accessed in [http://localhost:8080](http://localhost:8080)

In **Development mode**, the backend server can be started by simply following the steps above but runs with `mvn package` 
instead, or runs the main class `Application` directly in IDE. The frontend server can be started after downloading all 
the necessary dependencies by first runs `npm install` followed by `ng serve --proxy-config proxy.conf.json`, 
which can accessed in [http://localhost:4200](http://localhost:4200)


## Angular

The Angular project is located in `WebApll-Project-presentation/src/main/angular` and uses sass for styling.
The main configuration file (`AngularConfig.java`) can be found in the presentation module.

The user interface is designed so that the user have at first only accesses to the login and register page. 
The user can only access the message system after a successful registration followed by a successful login.
  

## Shiro

The main configuration file (`ShiroConfig.java`) can be found in the presentation module.
It contains the `MyRealm` realm, which is located in the business module. Since this project uses basic authentication,
in `MyRealm` only two methods are implemented:
  * `doGetAuthenticationInfo` for authentication tasks
  * `doGetAuthorizationInfo` for authorization tasks. There are two roles 'user' and 'admin'. Automatically, every registerd users
  have 'user' role. 

If a user has admin account (has 'admin' name), they have additionaly 'admin' role. An Admin has permission to change
and delete anyone's messages. Here Shiro is responsible to provide the info whether a user is an admin or not. The permissons are implemented
accordingly based on the provided info in Angular frontend.

