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
  
 ## How this app is deployed

1. Developer pushes code to remote git repository.
2. A GitHub hook informs CircleCI of a push event. CircleCI uses the configuration file in the repository to execute a sequence of actions/stages, essentially invoking the CI pipeline.
3. In the pipeline, after we clone the project, 
3.1 Attempt to build the project using maven.
3.2 Run all the unit tests in the repo using maven. Store unit test results on CircleCI.
3.3 Run code styling checks. Store code styling reports on CircleCI.
3.4 Make a call to Sonarcloud (a hosted SonarQube) to perform static code analysis on the project.
4. If all of the above operations pass successfully, we inform Heroku that this commit is suitable to deploy.
5. Heroku then pulls the code from our repository, builds it into a production-ready version, and deploys it on a public domain.
