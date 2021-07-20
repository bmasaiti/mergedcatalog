FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} mergecatalog.jar
MKDIR csvdata/
COPY 
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=default","/mergecatalog.jar"]
