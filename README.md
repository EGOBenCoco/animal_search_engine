


### Admin
Login: admin <br>
Password: admin
### User
Login: user <br>
Password: user

## Used Technologies:

* Spring 3.1 (Boot, Data, Security, JWT)
* Java 17
* JPA
* MYSQL
* Maven
* Junit
* Mockito
* JPQL Query
* Amazon S3
* Docker
* FlyWay

## About Project
### This application is designed to help you find lost animals

* User can register / log in.
* Creating advertisements for the search for lost animals indicating:

    * Type of animal (dog, cat, other)
    * Breeds
    * Gender
    * Cities where the animal was lost
    * Owner contact information
    * Photos of the animal
  

* Adding an animal's last known location to an ad


* Filtering ads by various criteria:
  
  * Type of animal
  *  Breed
  *  Gender
  *  City


* View all ads with paginations
* View ad details
* Adding comments to ads
* Editing and deleting your ads
* Update contact information
* Change Password
* Administrators can block and unblock users
* Integration with Amazon S3 for storing animal photos


## Screenshots

Ad filtering

![Alt text](https://github.com/EGOBenCoco/photo_ReadMe/blob/main/filter.PNG?raw=true)


Adding photos to Amazon S3:

![Alt text](https://github.com/EGOBenCoco/photo_ReadMe/blob/main/add_photo.PNG?raw=true)


Receive an ad by id :

![Alt text](https://github.com/EGOBenCoco/photo_ReadMe/blob/main/one_ad.PNG?raw=true)

## Deployment

* To launch the application, follow these steps:
  * 1 . Building the application <br>
```
  mvn clean install
  ```
  * 2 . Running Docker-Compose
  ```
  docker-compose up --build
  ```
* Examination:
  * Open  http://localhost:9090/api/v1/announcements 

## Testing

### TestContainers and Docker

This project utilizes TestContainers in
conjunction with Docker to facilitate testing.
The tests for repositories and controllers are designed to run in
Docker containers.
