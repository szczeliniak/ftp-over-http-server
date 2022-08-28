# FTP over HTTP server
### Luxdone recruitment task

## Endpoints

All endpoints and available at **/swagger-ui.html**

## Run application

### Intellij (dev mode)

1. Run infrastructure with command **docker-compose -f docker-compose-dev.yaml up -d** in root directory.
2. Run application by starting class **FtpOverHttpServerApplication** with **dev** profile.

Stop dev infrastructure command: **docker-compose -f docker-compose-dev.yaml down**.

### Docker (prod mode)

1. Build application with command **./mvnw clean install**.
2. Run application and infrastructure with command **docker-compose up -d**.

Stop application command: **docker-compose down**