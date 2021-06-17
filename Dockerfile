FROM adoptopenjdk:11-jre-hotspot
ADD ./target/client-0.0.1-SNAPSHOT.jar /usr/src/client-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "client-0.0.1-SNAPSHOT.jar"]
