FROM reg.qeetan.com:7000/gc/java11:v1

COPY target/giftcricle-0.0.1-SNAPSHOT.jar /

ENTRYPOINT ["java","-jar","giftcricle-0.0.1-SNAPSHOT.jar"]


