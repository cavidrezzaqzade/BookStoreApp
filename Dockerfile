#FROM ubuntu:latest
#RUN apt-get -y update
#RUN apt-get install -y tzdata
#RUN apt-get -y install apache2
#RUN apt-get install -y maven
#RUN apt-get install -y vim
#ADD . /var/book-app-auth
#RUN cd /var/book-app-auth && mvn clean install
##EXPOSE 80
#EXPOSE 2929
#CMD apachectl -D FOREGROUND

###minimal versiya budur docker-compose ile
FROM openjdk:11
ADD target/book-app-auth.jar book-app-auth.jar
ENTRYPOINT ["java", "-jar","book-app-auth.jar"]
EXPOSE 8090

### securityden
## Build stage
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean install
#
## Package stage
#FROM openjdk:11-jre-slim
#COPY --from=build /home/app/target/book-app-auth.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]
