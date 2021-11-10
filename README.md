## Azure Service RabbitMQ Connector

This spring boot application connects to rabbitmq and azure service bus and transfer messages from both directions. 

rabbitmq has shavel plugin that can directly connects to service bus. for whatever reasons if you dont have that plugin in rabbitmq or you application on private data center is not allowed to talk to azure service other the http ports, then this is useful.