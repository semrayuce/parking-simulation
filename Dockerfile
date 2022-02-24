# define base docker image
FROM openjdk:8
LABEL maintainer="semrayuce"
ADD target/parking-rest-api-2.1.6.RELEASE.jar parking-garage-simulation.jar
ENTRYPOINT ["java", "-jar", "parking-garage-simulation.jar"]