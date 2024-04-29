FROM openjdk:20
RUN mkdir /app
COPY target/AeroTran-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
CMD ["java", "-jar", "./AeroTran-0.01-SNAPSHOT.JAR"]