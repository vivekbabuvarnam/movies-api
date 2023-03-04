`When the application is started all the tables will be prefilled with Test data!`
`You can use user: vivek and password: admin to generate token or to create new users in the API`

## To Test in Local
***
* Import the Collection postman_collection.json to the Postman from "Backbase Assignment\Postman_testing_collection"
* Navigate to MoviesApi -> "Local or Docker" folder
* Trigger the MoviesApi -> Local or Docker folder/Token Generation Request (/login)(It will get the JWT token for dummy ADMIN user vivek/admin)
* After this the postman wil store this bearer to the environment variable.
* Then you can go to below folder and trigger the request with the prefilled sample endpoints
    1. To Movie related
        	Movie Info/Movie Info With Bes Picture Oscar Info
        	Movie Info/Top 10 Movies based on Rating sorted with Box office value
    2. To Rating related
    	Movie Rating/Add Rating with imdb id
    	Add Rating to Movie with Title
    	Delete Rating Based on Id
    3. To Admin related
    	General/Upload Oscare info CSV
    4. To User Management related
    	User Role Management/Get List of Users
    	User Role Management/Create New User
    	User Role Management/Create New Role
    	User Role Management/Add Role To User

## To Test in Kubernetes
****************	

Import the Collection postman_collection.json to the Postman from the "Backbase Assignment\Postman_testing_collection"
Navigate to MoviesApi -> Kubernetes
Repeat the same step as above (Only the port number will be different in this case!)

## To test the OMDB Service
*************************
Import the Collection postman_collection.json to the Postman from the "Backbase Assignment\Postman_testing_collection"
Navigate to MoviesApi ->OmdbService
