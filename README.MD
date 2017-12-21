# I. Quick Start Steps 
## 0. Prerequisite
[Single Node Geode Server by Spring Boot](https://github.com/komushi/spring-boot-Geode-server)
[scs-source-http](https://github.com/komushi/scs-source-http)
[scs-processor-geocoding-reverse](https://github.com/komushi/scs-processor-geocoding-reverse)
[RabbitMQ](http://brewformulas.org/Rabbitmq)

## 1. Download and Build

```
git clone https://github.com/komushi/scs-sink-gemfire.git
cd scs-sink-gemfire
mvn clean package docker:build
docker push komushi/scs-sink-gemfire
```


## 2. Run in Java CLI
### 2-1. Command to start jar
```
java -jar target/scs-sink-gemfire-1.0.0.RELEASE.jar --spring.cloud.stream.defaultBinder=<rabbit/kinesis/kafka> --gemfire.pool.host-addresses=<gemfire-cacheserver-address:port>
```

### 2-2. Test with scs-source-http and scs-processor-geocoding-reverse
```
java -jar target/scs-source-http-1.0.0-RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit 

java -jar target/scs-processor-geocoding-reverse-1.0.0-RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --logging.level.info.cloudnative=TRACE --properties.mongo.hostName=localhost

java -jar target/scs-sink-gemfire-1.0.0.RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --gemfire.pool.host-addresses=localhost:40404 

curl -H "Content-Type: text/plain" -X POST -d'468244D1361B8A3EB8D206CC394BC9E9,BB899DFEA9CC964B50C540A1D685CCFB,2013-01-01 00:00:00,2013-01-01 00:04:00,240,1.71,139.752268,35.677043,139.771699,35.697283,CSH,6.50,0.50,0.50,0.00,0.00,7.50' 'http://localhost:9000'
```

## 3. Run with docker deployment
### 3-1. Local Test with RabbitMQ
```
docker run komushi/scs-sink-gemfire --spring.cloud.stream.defaultBinder=rabbit --spring.rabbitmq.host=docker.for.mac.localhost --spring.rabbitmq.port=5672
```

### 3-2. Deploy on AWS
```
docker run komushi/scs-sink-gemfire --gemfire.pool.host-addresses=<gemfire-cacheserver-address>:40404
```

## 4. Default Parameters - application.properties
```
spring.cloud.stream.bindings.input.destination=transform_geotuple
spring.cloud.stream.bindings.input.group=gemfire_rawjson 
spring.cloud.stream.defaultBinder=kinesis
cloud.aws.region.static=us-east-1
gemfire.pool.connect-type=server
gemfire.pool.host-addresses=docker.for.mac.localhost:40404
gemfire.region.region-name=RegRaw
gemfire.json=true
gemfire.key-expression="payload.getField('uuid')"
```