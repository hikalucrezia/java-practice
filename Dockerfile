# Use the official Ubuntu image as a parent image
FROM ubuntu:latest

# Update the package repository and install necessary tools
RUN apt-get update && apt-get install -y wget software-properties-common locales

# Generate and set the locale to Japanese
RUN locale-gen ja_JP.UTF-8
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP:ja
ENV LC_ALL ja_JP.UTF-8

# Install OpenJDK 21
RUN apt-get update && apt-get install -y openjdk-21-jdk

# Verify installation
RUN java -version

# Set JAVA_HOME environment variable
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64

# Set the default command to run when starting the container
CMD ["bash"]
