FROM eclipse-temurin:17.0.15_6-jre-noble
LABEL maintainer="r15priyanshu" website="https://www.linkedin.com/in/r15priyanshu/"
RUN mkdir -p /app/expensemate
WORKDIR /app/expensemate
COPY target/expensemate.jar .
EXPOSE 8080
CMD ["sh", "-c", "java $JAVA_OPTS -jar expensemate.jar"]