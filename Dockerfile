FROM openjdk:8-jdk-alpine

ENV API_VERSION 1.0-SNAPSHOT

ADD target/kube-aws-iam-controller-java-example-$API_VERSION.jar awsiamexample.jar

# http://blog.sokolenko.me/2014/11/javavm-options-production.html
ENTRYPOINT java $JAVA_OPTS\
                -jar awsiamexample.jar
