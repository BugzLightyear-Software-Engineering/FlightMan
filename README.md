# FlightMan

This is an air travel management server. In this, we want to build an API (no GUI)
mimicking airlines' flight-booking, ticketing, and customer management system. Our
server will employ a sophisticated algorithm to suggest a set of "best" sequences
of flights to a user, given his start airport, destination airport, and dates of
arrival and departure. From this set, the user can pick a sequence and book her/his
tickets. In summary, the server will allow its clients to fetch flight and customer
information and create/update/delete ticket bookings. We will support multiple
clients at the same time and will maintain the data for the flight, ticket, and
customer information persistently in our database.


## Instructions to set up our server:
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

## Instructions to navigate our server:
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

## Operational entry points to our server:
Each of the below entry points are only accessible to authorized users, i.e.,
users who have entered their username and correct password when prompted.

1. `/airports` (i.e., https://bugzlightyear-flightman.herokuapp.com/api/airports):
Displays a list of all the airports in the database or `HTTP NO_CONTENT` if none
are available.

2. `/bookings` (i.e., https://bugzlightyear-flightman.herokuapp.com/api/bookings):
Displays a list of all the bookings in the database for a particular user and if
not user is supplied, it displays all the bookings.

3. `/flights` (i.e., https://bugzlightyear-flightman.herokuapp.com/api/flights):
Displays all the flights from a source to a destination airport. If the airports
supplied aren't in the database, displays `HTTP NO_CONTENT`. If a source is not
supplied, all flights going to that destination are displayed and vice versa.

4. `/models` (i.e., https://bugzlightyear-flightman.herokuapp.com/api/models):
Displays all the flight models in the database or `HTTP NO_CONTENT` if none are
there.

5. `/users` (i.e., https://bugzlightyear-flightman.herokuapp.com/api/users):
Displays all the users registered in the database or `HTTP NO_CONTENT` if none
are there.

--------------------------------------------------------------------------------

## A basic ER diagram conceptualizing our databse can be found below:

![alt text](https://github.com/BugzLightyear-Software-Engineering/FlightMan/blob/main/ER_Diagram.png)

--------------------------------------------------------------------------------

## Third-party code:
As of now, We do not have any third-party code in our repository other than the
SpringBoot library code

--------------------------------------------------------------------------------

## How this application is deployed (something users do not need to care about):
1. Developer pushes code to remote git repository.

2. A GitHub hook informs CircleCI of a push event. CircleCI uses the
configuration file in the repository to execute a sequence of actions/stages,
essentially invoking the CI pipeline.

3. In the pipeline, after the project is cloned, (1) attempt to build the project
using maven, (2) run all the unit tests in the repository using maven and store
the unit test results on CircleCI, (3) run code styling checks and store code
styling reports on CircleCI, and (4) make a call to SonarCloud (a hosted
SonarQube) to perform static code analysis on the project.

4. If all of the above operations pass successfully, we inform Heroku that this
commit is suitable to deploy.

5. Heroku then pulls the code from our repository, builds it into a
production-ready version, and deploys it on a public domain.
