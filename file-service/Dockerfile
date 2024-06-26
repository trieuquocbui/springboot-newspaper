FROM openjdk:22-jdk-alpine

COPY ./target/file-service-1.0-SNAPSHOT.jar file-service-1.0-SNAPSHOT.jar

ADD src/main/resources/images application/images

ENTRYPOINT ["java","-jar","/file-service-1.0-SNAPSHOT.jar"]