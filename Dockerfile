FROM amazoncorretto:11-alpine

ENV PROJECT_HOME /opt/app

RUN mkdir -p $PROJECT_HOME

COPY ./target/ftp-over-http-server-0.0.1-SNAPSHOT.jar $PROJECT_HOME/app.jar

WORKDIR $PROJECT_HOME
CMD ["java", "-jar", "./app.jar" ]