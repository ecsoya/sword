FROM openjdk:latest
ARG LOADER_JARS=jars
COPY ${LOADER_JARS} /jars
ARG JAR_FILE=app.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 8063
ENV JAVA_OPTS="-Xms512m -Xmx2g -Djava.security.egd=file:/dev/./urandom -Dloader.path=/jars  -Duser.timezone=GMT+08"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]