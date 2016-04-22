# java-dockerized
This project is a demonstration of how to dockerize a Java project and how it works. 

# The Java part

The Java project will get some input, it will perform some modification over it and will generate the result in a specific location. 
Besides some logs will be written.

# The docker part

The Java project will be included in a docker image which will provide the input and will store the logs and result in a persistent location in the host.
Just by running the docker container the java app will be executed.

To run it:
1. Create the docker image - enter in the directory where `Dockerfile` lives and type: `docker build -t <user>/<image_name> <path_to_this_file>`
2. Create a container and run it: `docker run --rm -ti <user>/<image_name>`.
