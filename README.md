# Message processing application

## Getting started

This is small project for processing messages. Application accepts message objects from queue and puts them to cache. Every 10 messages we print out report about received objects and products (two types of objects contain data about products). For every 50 messages we print out report about messages which contain adjustments. Application never stops, every report is just printing out data in application at this time.     

## Prerequisites

* JDK 8
* Maven 3 or higher
* Git client
* Jboss 10 wildfly with queue java:/jboss/exported/jms/queue/MessageProcessingQueue

## Modules

* message.processing.core - business logic, end result is deployable war.
* message.processing.model - common classes, which are used in core and sender application (building this is project must be first, because others have dependency on this one)
* message.processing.sender - external sender to queue, currently is used just for testing.

## Install

Deploy message.processing.core-{module.version}.war to Jboss.

## Testing

For testing is created project message.processing.sender. It is external application, which is sending messages to queue. The following command runs unit tests:

	mvn clean test
	
## Build With
	Maven - dependency managment
	JDK 8 - Jave version 1.8.0_101

## Authors
	Tadej Kovačič
	
## Changelog

### 0.0.1 (23.2.2017)
- first version for deployment.
- added compiler configuration to pom.xml in core project


