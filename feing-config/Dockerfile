FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER longfei

VOLUME /tmp

ENV TZ=Asia/Shanghai
COPY ./target/feing-config-1.0.jar app.jar

ENTRYPOINT ["java", "-Xmx256m", "-Xms256m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
