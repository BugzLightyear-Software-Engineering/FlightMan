# FlightMan

An air travel management server.

## Instructions to set-up this server:
1. Use the Ubuntu Operating System. For ensured reproducibility, use Ubuntu 22.04.
2. Install Java. For ensured reproducibility, use the steps described here: https://linuxhint.com/install-java-ubuntu-22-04/ to install at least version 17.
3. Install Maven. For ensured reproducibility, use the steps described here: https://linuxhint.com/install_apache_maven_ubuntu/ to install the latest version.
4. Clone this repository (say, using `git clone git@github.com:BugzLightyear-Software-Engineering/FlightMan.git`).
5. Run `cd FlightMan`.
6. Install the Maven Wrapper by running `mvn -N wrapper:wrapper`. This simplifies building and running the server.
7. Finally, run the server on your local system using the command: `./mvnw clean install && ./mvnw spring-boot:run`.
  
## How this application is deployed:
1. Developer pushes code to remote git repository.
2. A GitHub hook informs CircleCI of a push event. CircleCI uses the configuration file in the repository to execute a sequence of actions/stages, essentially invoking the CI pipeline.
3. In the pipeline, after we clone the project, 
3.1 Attempt to build the project using maven.
3.2 Run all the unit tests in the repo using maven. Store unit test results on CircleCI.
3.3 Run code styling checks. Store code styling reports on CircleCI.
3.4 Make a call to Sonarcloud (a hosted SonarQube) to perform static code analysis on the project.
4. If all of the above operations pass successfully, we inform Heroku that this commit is suitable to deploy.
5. Heroku then pulls the code from our repository, builds it into a production-ready version, and deploys it on a public domain.
