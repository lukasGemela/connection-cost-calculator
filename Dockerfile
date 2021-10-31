FROM openjdk:11-jre-slim

COPY ./build/libs/cost-calculator-1.0-SNAPSHOT.jar ./app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]
