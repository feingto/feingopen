FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER longfei

VOLUME /tmp

RUN apk update && apk upgrade
RUN apk add --no-cache bash
ENV TZ=Asia/Shanghai
COPY ./target/feing-account-1.0.jar app.jar
COPY ./wait-for-it.sh /wait-for-it.sh

ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
