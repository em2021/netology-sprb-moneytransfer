FROM openjdk:8-jdk-alpine
EXPOSE 5500
ADD build/libs/netology-moneytransfer-0.0.1-SNAPSHOT.jar moneytransferapp.jar
ENTRYPOINT ["java","-jar","/moneytransferapp.jar"]