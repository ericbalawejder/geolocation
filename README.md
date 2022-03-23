### Geolocation
Provide the geolocation of a given IP address. Built with Java 17, 
[Dropwizard](https://www.dropwizard.io/en/latest/), 
[Jdbi3](https://jdbi.org/) and [MySQL](https://www.mysql.com/). Uses the 
[ip-api](https://ip-api.com/docs/api:json) which is intended for developers who want to write applications 
that can query IP-API.

#### To Run:
After cloning, build the jar file:
```
$ ./gradlew clean assemble
```
or to show the test output:
```
$ ./gradlew clean build 
```
To start the server:
```
$ ./gradlew run
```
Visit the resource path `http://localhost:8080/api/geolocation/hello`

##### Tests
To run the tests:
```
$ ./gradlew test
```

##### Schema
```sql
mysql> desc geolocation;
+-------------+---------------+------+-----+---------+----------------+
| Field       | Type          | Null | Key | Default | Extra          |
+-------------+---------------+------+-----+---------+----------------+
| id          | int           | NO   | PRI | NULL    | auto_increment |
| query       | varchar(200)  | YES  |     | NULL    |                |
| status      | varchar(200)  | YES  |     | NULL    |                |
| country     | varchar(200)  | YES  |     | NULL    |                |
| countryCode | varchar(200)  | YES  |     | NULL    |                |
| region      | varchar(200)  | YES  |     | NULL    |                |
| regionName  | varchar(200)  | YES  |     | NULL    |                |
| city        | varchar(200)  | YES  |     | NULL    |                |
| zip         | varchar(200)  | YES  |     | NULL    |                |
| lat         | decimal(10,8) | YES  |     | NULL    |                |
| lon         | decimal(11,8) | YES  |     | NULL    |                |
| timezone    | varchar(200)  | YES  |     | NULL    |                |
| isp         | varchar(200)  | YES  |     | NULL    |                |
| org         | varchar(200)  | YES  |     | NULL    |                |
| asHandle    | varchar(200)  | YES  |     | NULL    |                |
+-------------+---------------+------+-----+---------+----------------+
```

#### Thoughts
https://dev.mysql.com/doc/refman/5.7/en/data-types.html <br>
Spatial data type for longitude and latitude
DECIMAL() vs POINT()


#### Style
Java line length 80 - 120?<br>
New line before end of class