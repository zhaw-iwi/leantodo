FROM alpine:latest
RUN apk add --no-cache openjdk21
COPY target/LeanToDo-1.0-SNAPSHOT.jar \
  /home/
CMD ["java","-jar","/home/LeanToDo-1.0-SNAPSHOT.jar"]
