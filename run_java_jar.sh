#!/bin/bash

# Run the java app generated with the command 'mvn package'.
# Its dependencies are within the folder target/dependency-jars
# The pom.xml file tells maven to make the pom executable adding the Manifest with the classpath needed.

java -jar ../target/dockerized-0.0.1-SNAPSHOT.jar