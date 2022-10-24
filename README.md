# FlightMan

An air travel management server.

## Instructions to set-up this server:
1. Use the Ubuntu Operating System. For ensured reproducibility, use Ubuntu
22.04.

2. Install Java. For ensured reproducibility, use the steps described here:
https://linuxhint.com/install-java-ubuntu-22-04/ to install at least version 17.

3. Install Maven. For ensured reproducibility, use the steps described here:
https://linuxhint.com/install_apache_maven_ubuntu/ to install the latest version.

4. Clone this repository (say, using `git clone
git@github.com:BugzLightyear-Software-Engineering/FlightMan.git`).

5. Run `cd FlightMan`.

6. Install the Maven Wrapper by running `mvn -N wrapper:wrapper`. This
simplifies building and running the server.

7. Finally, run the server on your local system using the command: `./mvnw clean
install && ./mvnw spring-boot:run`.

--------------------------------------------------------------------------------

## Instructions to navigate the server:
As of now, we have a basic server with HTTP authentication for users.

1. To access the server, go to:
https://bugzlightyear-flightman.herokuapp.com/api/ (as an alternative, when
running the server on your local system, go to http://localhost:8080/api).

2. You will be prompted to enter your username and password. Our server's users
will be vast, comprising both other applications as well as end users.

3. For the basic demo, we have authorized users namely, "srishti", "ajay",
"abhilash", "miloni", "otito", "peter", and "professor_kaiser", each with their
secure passwords.

4. The passwords have been encoded and stored in the server to ensure a nice
level of security.

5. Once your authorization is successful, you will see the page "Hello Authorized
User!" on your screen.

--------------------------------------------------------------------------------

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
