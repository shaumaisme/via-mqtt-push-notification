#README

##TODO

1. Detailing this README

2. Add scheduled notifications send

3. ...

###Introduction
This project is intended for proof of concept for sending notification to MQTT subscribers client. From this application, notification is stored in database and will not published yet message will be published to ActiveMQ's JMS topic. By internal mechanism of ActiveMQ, that message will be pushed to all MQTT subscribers.


###Configuring ActiveMQ
Make sure ActiveMQ configured for accepting MQTT subscription. Default configuration of ActiveMQ broker is already enable MQTT broker. 

