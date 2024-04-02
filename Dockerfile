FROM alpine:3.16.0

RUN apk add --no-cache java-cacerts

ENV JAVA_HOME=/opt/openjdk-17

ENV PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

ENV JAVA_VERSION=17-ea+14