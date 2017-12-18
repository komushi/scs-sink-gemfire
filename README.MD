## Test with http source and geocoding processor

```
java -jar target/scs-source-http-1.0.0-RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit 

java -jar target/scs-processor-geocoding-reverse-1.0.0-RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE --properties.mongo.hostName=localhost

java -jar target/scs-sink-gemfire-1.0.0.RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --gemfire.region.region-name=RegRaw --gemfire.json=true --gemfire.key-expression="payload.getField('uuid')" --gemfire.pool.host-addresses=localhost:40404 --spring.cloud.stream.bindings.input.destination=transform_geotuple --spring.cloud.stream.bindings.input.group=gemfire_rawjson --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE

curl -H "Content-Type: text/plain" -X POST -d'468244D1361B8A3EB8D206CC394BC9E9,BB899DFEA9CC964B50C540A1D685CCFB,2013-01-01 00:00:00,2013-01-01 00:04:00,240,1.71,139.752268,35.677043,139.771699,35.697283,CSH,6.50,0.50,0.50,0.00,0.00,7.50' 'http://localhost:9000'

```


## Test with http source
```
java -jar target/scs-source-http-1.0.0-RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --spring.cloud.stream.bindings.input.contentType=application/json --spring.cloud.stream.bindings.output.destination=transform_geotuple

java -jar target/gemfire-sink-rabbit-1.3.0.RELEASE.jar --spring.cloud.stream.defaultBinder=rabbit --gemfire.region.region-name=RegRaw --gemfire.json=true --gemfire.key-expression="payload.getField('uuid')" --spring.cloud.stream.bindings.input.destination=transform_geotuple --spring.cloud.stream.bindings.input.group=gemfire_rawjson --logging.level.io.pivotal.spring.cloud.stream.sink=TRACE

curl -H "Content-Type: application/json" -X POST -d'{"uuid":"3e6ba464-ee0f-4d13-91b0-5bd2721830f0","route":"1010340_1010050","timestamp":1513579606338,"pickupAddress":"千代田区 外神田","dropoffAddress":"千代田区 霞が関","pickupLatitude":35.697283,"pickupLongitude":139.771699,"dropoffLatitude":35.677043,"dropoffLongitude":139.752268,"pickupDatetime":"2013-01-01 00:00:00","dropoffDatetime":"2013-01-01 00:04:00","pickupBlockCode":"1010340","pickupBlock":"外神田","pickupDistrictCode":"101","pickupDistrict":"千代田区","dropoffBlockCode":"1010050","dropoffBlock":"霞が関","dropoffDistrictCode":"101","dropoffDistrict":"千代田区"}' 'http://localhost:9000'

curl -H "Content-Type: application/json" -X POST -d'{"uuid":"2e6ba464-ee0f-4d13-91b0-5bd2721830f0","route":"1010340_1010050","timestamp":1513579606338,"pickupAddress":"千代田区 外神田","dropoffAddress":"千代田区 霞が関","pickupLatitude":35.697283,"pickupLongitude":139.771699,"dropoffLatitude":35.677043,"dropoffLongitude":139.752268,"pickupDatetime":"2014-01-01 00:00:00","dropoffDatetime":"2014-01-01 00:04:00","pickupBlockCode":"1010340","pickupBlock":"外神田","pickupDistrictCode":"101","pickupDistrict":"千代田区","dropoffBlockCode":"1010050","dropoffBlock":"霞が関","dropoffDistrictCode":"101","dropoffDistrict":"千代田区"}' 'http://localhost:9000'
```
