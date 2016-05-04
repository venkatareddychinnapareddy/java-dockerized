# java-dockerized
This project is a demonstration of how to dockerize a Java project. 

## The Java part

The Java project will get an input file, it will perform some modification over it and will generate the result as another file in a specific location. 
Besides some logs will be written.

## Dockerize the app using Dockerfile

The Java project will be included in a docker image which will provide the input and will store the logs and result in a persistent location in the host.
Just by running the docker container the java app will be executed.

### By using a Dockerfile

Write the Dockerfile which starts from a java image, then copy the jar of the application as well as the jar dependencies to the directory where the jar expect to find them. Besides copy the input file and the log configuration file. This file is the `Dockerfile` located in the root of the project.

Steps:

1. Generate the jar file for this project and get its dependencies. The pom file is configured to do so, you just need to type: `mvn clean package` and this command will generate the jar in the common path and its dependencies in the folder `dependency-jars` (defined in pom.xml).

1. Create the docker image - enter in the directory where `Dockerfile` lives and type: `docker build -t <user>/<image_name> <path_to_this_file>`. 
For instance: `docker build -t joseoc/java-example-001 .`

1. Create a container and run it: `docker run <user>/<image_name>`. 
For instance: `docker run joseoc/java-example-001`

### By maven

The option described above needs 2-step process: generate jar + generate docker image.
You can use a maven plugin to do those two steps in one by adding to the pom the plugin `docker-maven-plugin`. There're two ways of using it, the first one is just writing in the pom the configuration for your docker image but this have some commands not supported. 
The other option is to write your Dockerfile and tell maven to execute it. I've chosen this second option.

In the pom file I've written the name for the docker image and directory where the resources for docker are, including the Dockerfile:
```
					<imageName>joseoc/java-example-001</imageName>
					<dockerDirectory>buildDockerUsingMaven</dockerDirectory>
```
Within the directory `buildDockerUsingMaven` I've written the Dockerfile (slightly different from the first I wrote) and the resources I want to copy into the docker container. Within this directory I've copied these resources with the same directory structure I want to have in the container.
The jar files aren't here since they are copied by the maven plugin.

Having this you just have to type the command: 
`mvn clean package docker:build` and the project is deployed and the docker image created. 

## Testing the docker image

Within the folder *docker-tests* you'll find how the docker image is tested using ruby.