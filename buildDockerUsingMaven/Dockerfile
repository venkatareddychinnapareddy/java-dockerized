# It generates the docker image to run the java project using maven: mvn clean package docker:build
# The jar will be generated, jar dependencies copied to the target folder and the docker image created.
# You can directly use it: docker run <image>
#
# In the pom file we have the base directory specified with the tag 'dockerDirectory'.
# Within this directory copy the resources you need in the container such as the input files and the log4j configuration file.


FROM java:8

COPY app /app
COPY tmp /tmp

VOLUME ["/tmp/docker/input", "/tmp/docker/output", "/tmp/docker/data/logs"]

WORKDIR /app/java_project/

CMD ["java", "-Dlog4j.configurationFile=/app/java_project/log4j2.xml", "-jar", "dockerized-0.0.1-SNAPSHOT.jar"]
