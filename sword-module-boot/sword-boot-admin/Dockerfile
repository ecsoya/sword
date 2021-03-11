FROM java:8
ARG LOADER_JARS=jars
COPY ${LOADER_JARS} /jars
ARG JAR_FILE=admin.jar
COPY ${JAR_FILE} /admin.jar
EXPOSE 8061
ENV JAVA_OPTS="-Xms128m -Xmx512m -Djava.security.egd=file:/dev/./urandom -Dloader.path=/jars  -Duser.timezone=GMT+08"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /admin.jar"]