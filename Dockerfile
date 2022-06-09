FROM openjdk:11
MAINTAINER fi.free
COPY target/demo-0.5.0.jar demo-0.5.0.jar
ENTRYPOINT ["java","-jar","/demo-0.5.0.jar"]