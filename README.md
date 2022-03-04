[ATP_TOUR_SCRIPT]:scripts/atp-tour-db.sql
[ATP_TOUR_SPRING_PROJECT]:atp-tour
[ATP_TOUR_ANGULAR_PROJECT]:atp-tour-angular
[NODE_MODULES]:atp-tour-angular/node_modules
[ATP_COUNTRY]: atp-tour/src/main/java/com/silab/atptour/entity/Country.java
[ATP_TOURNAMENT]: atp-tour/src/main/java/com/silab/atptour/entity/Tournament.java
[ATP_PLAYER]: atp-tour/src/main/java/com/silab/atptour/entity/Player.java
[ATP_MATCH]: atp-tour/src/main/java/com/silab/atptour/entity/Match.java
[ATP_STATISTICS]: atp-tour/src/main/java/com/silab/atptour/entity/Statistics.java
[ATP_INCOME]: atp-tour/src/main/java/com/silab/atptour/entity/Income.java
[ATP_USER]: atp-tour/src/main/java/com/silab/atptour/entity/User.java
[ATP_ROLE]: atp-tour/src/main/java/com/silab/atptour/entity/Role.java
[ATP_TOUR_POSTMAN]: atp-tour.postman_collection.json

# atp-tour
Spring-Angular app for following events on the ATP tour.

# Requirements
App is composed of Spring Boot 2.6.2 used for backend and Angular 13.1.2 used for frontend. For data storage MySQL 8.0.22 was used.

Necessary installations and setup for running the app locally:
> * JDK 1.11+
> * Apache Maven 3.3+
> * NodeJS 14.15.0+
> * MySQL 5.6+

If you wish to run the app on docker you need to install  Docker Engine 1.13.0+

## Database

### Relational Data Model
Below you can find relational data model used in atp-tour database:

> * Country(__id__, name, code_name)
> * Tournament(__id__, name, start_date, completion_date, tournament_type, *host_country*)
> * Player(__id__, first_name, last_name, date_of_birth, current_points, live_points, player_rank, *birth_country*)
> * Matches(*__tournament_id__*, *__first_player_id__*, *__second_player_id__*, match_date, round, result, *winner*)
> * Statistics(__id__, *__tournament_id__*, *__first_player_id__*, *__second_player_id__*, first_player_points, second_player_points, first_player_aces, second_player_aces, first_player_break_points, second_player_break_points, first_player_first_serves_in, second_player_first_serves_in, first_player_second_serves_in, second_player_second_serves_in)
> * Income(*__tournament_id__*, *__player_id__*, points)

> * User(__id__, first_name, last_name, username, password, enabled)
> * Role(__id__, name)
> * Users_Roles(*__user_id__*, *__role_id__*)

### Database Creation
In order to create atp-tour database just run this [script][ATP_TOUR_SCRIPT]. Script creates atp-tour database with all
necessary tables. Script also contains sql queries for inserting:
> * 217 countries from all around the world.
> * admin and user roles
> * admin user

If you are using Docker, script will be executed during mysql container creation. 


## Running App Locally

### Running Spring Boot App
In order to run Spring Boot app locally you will need to follow the next steps:
1. Navigate to [atp-tour][ATP_TOUR_SPRING_PROJECT] folder and open Command line.
2. Run __mvn clean install__ command to install all necessary dependencies and build Spring Boot project
3. After previous command is done executing and you have received BUILD SUCCESS message run __mvn spring-boot:run__. 
After a few seconds app will be running on __localhost:8080__

### Running Angular App
In order to run Angular app locally your will need to follow the next steps: 
1. Navigate to [atp-tour-angular][ATP_TOUR_ANGULAR_PROJECT] folder and open Command line.
2. Run __npm install__ comand so you can install dependencies to local [node_modules][NODE_MODULES] folder
3. Next run __npm run build --prod__ command in order to build Angular project
4. Finally run __npm start__ command to start atp-tour-angular app. After a few second app will be running on __localhost:4200__

## Running App Using Docker

While in root folder open CLI with your Docker running open CLI and run __docker compose up__ command. It will pull necessary images, create containers and setup the project. Just as it was the case for running app locally, Spring Boot app is running on __localhost:8080__ and Angular app is running on __localhost:4200__

## Using the App

When you have started the app either by running it locally or by Docker open your browser and go to __localhost:4200__ where login page should open. Login page is always showed if user isn't logged id. To be able to see or update any site content [user][ATP_USER] has to be logged in. Users with admin role can execute CRUD operations on entity classes, whereas users with user [role][ATP_ROLE] can only see site content. You can also register with user role and after that login to the site. When registering a new user for username enter your email and for password enter atleast 8 characters. If you have run [sql script][ATP_TOUR_SCRIPT] admin user with username __admin@admin.com__ and password __admin123__ should've been created. When you login players page will be opened automatically. 

On left side of the header you can see three links tournament, tournament and matches.
Player page display all players which represents entities of [Player][ATP_PLAYER] class. Inside a search field you can filter players by first and last name. Clicking on column name you can sort values ascending or descending. Also below the table you can paginate through and display other players. If you are logged in as admin user you can add new players pressing on add player button which will open a popup for adding new player. It is important to know that player can't be yonger than 16 years comparing to the present date, to select a valid birth [country][ATP_COUNTRY] and that all fields are mandatory. Clicking on table row player's data will be displayed and admin users can also update player's first name, last name or live points.

 When you press on tournament inside the header entities of [Tournament][ATP_TOURNAMENT] class are displayed inside paginated table. 
 As it was the case for the players you can search tournaments by name and sort them clicking on column name. As it was the case previously admin users can add new tournaments or update existing ones. When adding new tournament you must select a future date, enter a valid host [country][ATP_COUNTRY], select exactly 16 participants and that all fields are mandatory. When tournament is saved first round matches for that tournament are created automatically. Clicking on table row will display adequate tournament. Users with admin role can update or delete tournaments if they haven't already started.

 Clicking on matches inside the header all [matches][ATP_MATCH] will be displayed. Here you can filter matches by tournaments, by one player or find H2H matches. Matches results can be updated if user has admin role and match is finished. Also it is important to first filter matches by tournament only if you want to update the results. When you have selected the results for the match winner will be displayed automatically. After you have entered the results click on Update matches button, after which next round matches will be created. For every player on the tournament instance of [Income][ATP_INCOME] is created where points income is saved for every player. Income is increased for every match winner. For every match [statistics][ATP_STATISTICS] are saved and they can be displayed if the match has finished by double clicking on the adequate row in the table. Admin users can save statistics values.

 Every day scheduled job is run and it check if any of the tournaments that starts on present date was played last year. If it was, tournament participants live points are reduced by the amount of the income on the previous tournament.
 Also every Monday ATP list is updated by equalizing live points with current points and ranking players on the list.

 Entities can also be created using [Postman collection][ATP_TOUR_POSTMAN]



