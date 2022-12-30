FROM openjdk:17
WORKDIR /app
#ADD ./target/ms-scheduling-0.0.1-SNAPSHOT.jar ms-scheduling.jar
COPY ./target/*.jar /app/ms-user.jar
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.config.additional-location=classpath:application-docker.yml", "-jar", "/app/ms-user.jar"]








#FROM openjdk:17
#WORKDIR /app
#COPY . .
#CMD [ "java", "-jar",  "spring-boot:run" ]