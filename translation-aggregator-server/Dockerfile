FROM openjdk:8-jre-alpine

# Use non-root user for install and run your application
RUN addgroup -g 1024 newuserkk \
    && adduser \
        -u 1024 \
        -G newuserkk \
        -s /bin/bash \
        -D \
        newuserkk

RUN mkdir /app
RUN chown -R newuserkk /app

USER newuserkk
WORKDIR /home/newuserkk

COPY ./build/libs/translation-aggregator-0.0.1.jar /app/translation-aggregator.jar
COPY ./src/main/resources/static /app/src/main/resources/static
COPY ./keys /app/keys
WORKDIR /app

CMD ["java", "-server", "-jar", "translation-aggregator.jar"]
