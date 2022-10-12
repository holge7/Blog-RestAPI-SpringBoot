# define base docker image
FROM openjdk:18
LABEL maintainer="holge"
# Copy the jar file to own docker image
ADD target/Blog-1.1.jar blog-docker.jar

#This configure how own application must run
ENTRYPOINT ["java", "-jar", "blog-docker.jar"]