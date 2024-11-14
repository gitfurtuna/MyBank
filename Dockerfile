FROM  openjdk:24-ea-23-jdk
WORKDIR /app
COPY /out/artifacts/mybanktrue_jar/mybanktrue.jar /app/bank.jar
ENTRYPOINT ["java", "-jar", "bank.jar"]