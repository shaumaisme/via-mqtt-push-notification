#README

###TODO

1. Detailing this README
2. Add scheduled notifications send
3. ...

###Introduction
This project is intended for proof of concept for sending notification to MQTT subscribers client. For simulation, notification item submitted using http request (look bellow) and then stored in database. Not published yet message will be published to ActiveMQ's JMS topic periodically every 5 seconds. By internal mechanism of ActiveMQ, that message will be pushed to all MQTT subscribers.

###Running Project
####Requirements
Following are requirements for running this project

0. Java 8 installed on your machine, this project uses lambda expression, especially in configuration of notification messages flow (Provided by [spring integration](http://projects.spring.io/spring-integration/) and spring [integration java dsl](https://github.com/spring-projects/spring-integration-java-dsl/wiki/Spring-Integration-Java-DSL-Reference) projects).
1. Maven installed. Or optionally use STS IDE or IntelliJ IDE.
2. [ActiveMQ](http://activemq.apache.org). This project tested with version [5.12.0](http://activemq.apache.org/activemq-5120-release.html).
3. MQTT client Android application. Search with "MQTT client" on Google Play store. This project tested using [this android app](https://play.google.com/store/apps/details?id=com.deepeshc.mqttrec).
4. Postman chrome browser extension (Optional, you also can use curl).
5. Postgresql for storing notification record. If you want to use embedded database, add [hsqldb](http://mvnrepository.com/artifact/org.hsqldb/hsqldb/2.3.3) dependency into project's pom.xml, remove postgresql dependency, and comment all ```spring.datasource``` config line on ```src/main/resources/application.properties```

####Configuring ActiveMQ
First, we have to make sure MQTT transport connector enable on ActiveMQ so that client can make MQTT subscription to broker. Default configuration of ActiveMQ broker is already enabling MQTT broker. Please read [this link](http://activemq.apache.org/mqtt.html) for details.

####Running Project
Run ActiveMQ by executing ```./bin/activemq start``` command from ActiveMQ installation directory.

Run Android MQTT client, set broker url (make sure broker is reachable by your Android network), port, username and password. For default installation of ActiveMQ, use ```admin``` and ```admin``` for username and password respectively. Then create subscription to following topics ```user/notification/#```, ```user/notification/+```, ```user/notification/123456``` and ```user/notification/654321``` (Assume we have user with id ```123456``` and ```654321```).

This project is spring-boot based project, so you can run this project with either run ```com.jakartawebs.learn.MqttNotificationApplication``` main class or using maven command ```mvn spring-boot:run```. Look [here](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html) for details.

####Adding Notification
Use postman (or curl) to add notification item to be processed (pushed to MQTT subscribers). Set ```content-type``` header with value ```application/json```. Try submit following as request body for not targeted notifications

```
{"subject":"Test Not Targeted Notification", "content":"Test notification content for all users"}
```

and following for targeted (user specific) notifications (for our example purpose, use 123456 or 654321 target users)

```
{"subject":"Test Targeted Notification", "content":"Test notification content for user 123456", "targets":["123456"]}
```

```
{"subject":"Test Targeted Notification", "content":"Test notification content for user 654321", "targets":["654321"]}
```

```
{"subject":"Test Targeted Notification", "content":"Test notification content for users 123456 and 654321", "targets":["123456", "654321"]}
```

Look at your Android MQTT client application, is any message received there?

###Problems
Drop me your questions on zakyalvan at gmail dot com.