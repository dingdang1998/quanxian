FROM java:8
MAINTAINER oket
ADD target/auth-1.0-SNAPSHOT.jar /opt/oket/app.jar
RUN touch /opt/oket/app.jar
EXPOSE 9401
ENV JAVA_OPTS="-Xms128m -Xmx256m"
ENTRYPOINT ["java", "-jar","/opt/oket/app.jar"]