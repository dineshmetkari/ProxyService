FROM java:8
VOLUME /tmp
ADD target/globalweatherproxyservice-1.0-SNAPSHOT.jar  proxyservice.jar
RUN bash -c 'touch /proxyservice.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /proxyservice.jar"]

   