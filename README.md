# esb-demo
ESB Demo

| Use case | Description |
|:--------|:----------|
| 1 | Create a one way SOAP service to process an incoming string and break into a fixed length elements and transform into an XML format and put on a queue. WS-Security username-token mechanism to be used as Authentication mechanism.|
| 2 | SOAP web service takes an input of a correlation ID and picks up the message from message queue based on that correlation ID on the message header. Based on the value from the response, the message is routed to different service Endpoints at the same time.|
| 3 | In this use case a RESTful web service is hosted using OAuth authentication. A request will be saved into a database and the response will be returned to the service consumer. This request and response should be in JSON format.|
| 4 | Service is listening to a message queue and when a message arrives on the queue the service consumes it and sends to a remote web service using a username/password which is stored in a configuration file. configuration file is read once at system start and cached in memory.|
| 6 | HTTP RESTful aweb service is hosted using transport level username/password security. It transforms JSON messages to XML and sends it to remote SOAP/HTTP service which also uses transport level username password security.|
| 7 | A JMS messages will be dynamically routed to a destination Queue based on business rules stored.|
