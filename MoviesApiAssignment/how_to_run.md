# To Run with Local IDE
***
1. Import the MoviesApiAssignment folder as an existing project to your IDE
2. set your maven in IDE settings
 ```sh
3. run -> mvn clean install  from the terminal from "Backbase Assignment\MoviesApiAssignment" folder
```
4. Navigate to MoviesapiApplication.java and then run the application
   or
   Add the Spring boot Configuration in IDE pointing the Main class to com.backbase.rest.moviesapi      .MoviesapiApplication and then start the configuration

* To test the endpoints, Use the postman collection and refer to the how_to_test.md

_To generate Code coverate report(IT and Unit test) inside targer folder -> run __mvn surefire-report:report__ (Currently the coverage is at around 75% to 80%)_

# To Run as Docker Image on Local Machine 
***

### (Docker needs to be installed in the machine, Better to have docker desktop app)

#### 1. API and DB as single container

* Navigate to the ""Backbase Assignment\MoviesApiAssignment"" folder
* create API Image from docker file
```sh
		docker build -t moviesapi ./
		docker pull postgres
		docker compose -f docker-compose.yml up -d
```
`This will spin up the API and Postgres DB in the docker container (Exposing 8080 for API)
	Test the endpoints on the same way as done in testing with IDE!`

#### 2. API and DB as Seperate containers
##### Database
* Navigate to the "Backbase Assignment\DockerImagesAndKubernetes\Docker_Database" folder
* Follow the steps specified in the commandsToExecute.txt in that folder to spin up the Postgres Database as seperate      container (Exposing 54320 for DB)
				
##### API
		
* Navigate to the "Backbase Assignment\DockerImagesAndKubernetes\Docker_Api" folder
* Follow the steps specified in the commandsToExecute.txt in that folder to spin up the API as container (Exposing 8080 for API)
	`Test the endpoints on the same way as done in testing with IDE!`

#### 3. To Deploy the application in Kubernetes (You need to have Docker Desktop with Kubenetes enabled or minikube or some other supporting environement)

##### Database
		Navigate to "Backbase Assignment\DockerImagesAndKubernetes\kubernetes\K8s_DB" folder
		Follow the steps specified in the CommandsToBeExecuted.txt in that folder to spin up the Postgres Database as seperate POD (Exposing 30002 for DB)
		
##### API	
		
* Update the application.yaml postgres port in the springdatasource as 30002 (cannot maintain the same port 54320 due to NODEPORT!).
```sh
Create a new JAR with "mvn clean install"
```
* Create the API image as mentioned in step 2
		
* Navigate to "Backbase Assignment\DockerImagesAndKubernetes\kubernetes\K8s_API" folder
* Follow the steps specified in the CommandsToBeExecuted.txt in that folder to spin up the API as seperate POD (Exposing 30001 for DB)
		
`Use the postman Assignment/MoviesApi/Kubernetes Folder to test the application running in K8s.`






