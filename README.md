# FTP over HTTP server
### Luxdone recruitment task

## Endpoints

All endpoints and available at **/swagger-ui.html**.
By default, the application works on port 8080

## Run application
To run application, firstly, you have to install Docker and Intellij (to run application in dev mode).
Maven Wrapper embedded in project may be used to build the application

### Intellij (dev mode)

1. Run infrastructure with command **docker-compose -f docker-compose-dev.yaml up -d** in root directory.
2. Run application by starting class **FtpOverHttpServerApplication** with **dev** profile (VM options: -Dspring.profiles.active=dev).

Stop dev infrastructure command: **docker-compose -f docker-compose-dev.yaml down**.

### Docker (prod mode)
##### This method does not work yet as there is some connection problem with FPTD. To be investigated. 

1. Build application with command **./mvnw clean install**.
2. Run application and infrastructure with command **docker-compose up -d --build**.

Stop application command: **docker-compose down**

### Dummy scheduler

The mechanism to handle file status changes. For demo purposes it is scheduled task executed every minute.
If file is not downloadable or modifiable, then wait a minute for task to be finished.