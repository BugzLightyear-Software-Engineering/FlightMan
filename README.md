# FlightMan

An air travel management system.

## Instructions to setup dev env
1. Clone this repository
2. If not already installed:
    * Install Java Standard Edition Development Kit - https://www.oracle.com/java/technologies/downloads. This is used to build  apps using the Java Programming Language. 
    * Install Maven - https://maven.apache.org/download.cgi. This is a project build, reporting and documentation management tool.
3. Add Maven binaries to system path, as directed in https://maven.apache.org/install.html
4. Install Maven Wrapper - run "mvn -N wrapper:wrapper" from the project root directory. This simplifies building and running the server.
4. Run "mvnw.cmd clean install" and then "mvnw.cmd spring-boot:run" (windows) 
  (or) "./mvnw clean install && ./mvnw spring-boot:run"     (linux) 
  from the project root directory to run the server on your local system.
