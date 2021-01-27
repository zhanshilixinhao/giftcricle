FROM reg.qeetan.com:7000/gc/java11:v1

ADD giftcricle-0.0.1-SNAPSHOT.jar giftcricletest.jar

ENTRYPOINT ["java","-jar","giftcricletest.jar"]


