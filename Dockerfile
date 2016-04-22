# It generates the docker image to run the java project
# Before running this script you have to run the maven command: mvn clean package
# To generate the docker image run: docker build -t <user>/<image_name> <path_to_this_file>


FROM java:8

WORKDIR /app/java_project/

# Copy the jar
COPY target/dockerized-0.0.1-SNAPSHOT.jar ./
# Copy dependencies
COPY target/dependency-jars/* ./dependency-jars/
# Copy input data
COPY src/test/resources/file.txt /tmp/docker/input/
COPY src/main/resources/log4j2.xml ./log4j2.xml

# Volumes
VOLUME ["/tmp/docker/input", "/tmp/docker/output", "/tmp/docker/data/logs"]

# Run the jar (CMD)
CMD ["java", "-Dlog4j.configurationFile=/app/java_project/log4j2.xml", "-jar", "dockerized-0.0.1-SNAPSHOT.jar"]
