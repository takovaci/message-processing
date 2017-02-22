# Message processing application

## Getting started
This is small project for processing messages. Application accepts message objects from queue and puts them to cache. Every 10 messages we print out report about received objects and products (two types of objects contain data about products). For every 50 messages we print out report about messages which contain adjustments. Application never stops, every report is just    

## Prerequisites

* JDK 8
* Maven 3 or higher
* Git client
* Jboss 10 wildfly with queue java:/jboss/exported/jms/queue/MessageProcessingQueue

## Modules

	message.processing.core - business logic, end result is deployable war.
	message.processing.model - common classes, which are used in core and sender application
	message.processing.sender - external sender to queue, currently is used just for testing.

## Testing

For testing is created project message.processing.sender. It is external application, which is sending messages to queue. The following command runs unit tests:

	mvn clean test

## Authors
	Tadej Kovačič
