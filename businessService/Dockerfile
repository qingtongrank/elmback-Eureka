FROM meddream/jdk17:latest
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]