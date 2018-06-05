FROM java:8-jre

ADD ./target/planets-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/planets-service.jar"]

EXPOSE 80