# Test docker image

We want to make sure the docker image is valid for us.

## What it'll be checked

The docker image must have:
* Java JRE version 8 installed
* The command `java` must exist and be a symbolic link.
* That command should respond with the correct version.
* The jar file must exist in the correct folder and be readable.
* The log4j2.xml file must exist in the correct folder and be readable.

## Requirements

You'll need to have docker installed, obviously, and also ruby and the ruby-bundle.
In the `docker-tests` directory run `bundle install` to have the dependencies ruby needs.

## How to run it

Go to the directory `docker-tests` in a terminal and executes `bash docker_build.sh`.

This script: 

1. Removes the image from your local repository
1. Builds the image using the `Dockerfile` in the root directory of this project
1. Executes the tests by running `rake spec`
1. Pushes the docker image to the registry
1. Deletes the image from your local repository
