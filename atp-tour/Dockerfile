FROM openjdk:11
ARG JAR_FILE=./target/atp-tour-1.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]