# webapp


Prerequisites for building and deploying:
1)	Java 17 with Maven
2)	MySQL Database 8.0.32
3)	IntelliJ IDEA
4)	Git bash

Instructions:

Project zip can be downloaded from GitHub and imported locally using IntelliJ IDEA. The dependencies needed for the project are included in pom.xml file.  
Database connection can be set up using application.properties file by passing the server, port, username and password details. A database with the name ‘userwebapp’ needs to be created with schema named ‘users’.
The project once imported to IntelliJ can be built using it by pressing Ctrl + F9 or running Build option from Menu Bar and the same can be deployed using the IntelliJ tool by executing the Run option on pressing Ctrl + Shift F10. RESTAPI can then be test using any web browser or Testing Tool like POSTMAN...


Prerequisites:

Visual Studio Code or any other IDE for Java development
POSTMAN
MySQL or any other database management system
Java JDK
Apache Tomcat
AWS account
Terraform
Create a new Java Web application using any IDE of your choice. This application should include RESTful APIs to perform CRUD operations on users and products.
Create a new Tomcat server instance and configure it to run your Java Web application.
Create a new MySQL database and configure your Java Web application to use this database for data storage.
Once you have tested your application locally, create a new directory and create a shell script file with the required dependencies. The .sh file should include the following commands:
Install Java JDK
Install Apache Tomcat
Install MySQL and create the database schema
Copy the Java Web application to the Tomcat webapps directory
Start the Tomcat service
Create a new Packer file and include the following configuration details:
Source AMI to build the new image from
Provisioner section to run the shell script file created in step 4
Builders section to create the new AMI
Run the Packer build command to create the new AMI.
Use Terraform to create a new EC2 instance from the AMI created in step 6.
Obtain the public IP address of the EC2 instance and test the APIs using POSTMAN.
Once you have tested the APIs and made any necessary changes, create a pull request with a detailed description of the changes.
Store the Image details in the RDS and meta data in S3 for future use.
The endpoints available for operations include GET, POST, PUT, PATCH, and DELETE requests for users and products. The HTTP messages that can be received include "200 OK," "201 Created," "204 No Content," "400 Bad Request," "401 Unauthenticated", "404 Not Found" and "403 Forbidden".

Java Application also log messages when each API is called along with collecting the count for each API call.
Packer was updated to create amazon cloud watch service and config file for it which would pass the Statsd data to cloud watch displaying the call of counts for each API...
