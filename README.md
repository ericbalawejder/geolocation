## Geolocation
Provide the geolocation of a given IP address. Built with Java 17, 
[Dropwizard](https://www.dropwizard.io/en/latest/), 
[Jdbi3](https://jdbi.org/) and [MySQL](https://www.mysql.com/). Uses the 
[ip-api](https://ip-api.com/docs/api:json) which is intended for developers who want to write applications 
that can query IP-API.

### To Run:
We must run the SQL scripts below to create the user, database and table.<br>
[create_user.sql](https://github.com/ericbalawejder/geolocation/blob/main/mysql/create_user.sql) <br>
[create_geolocation.sql](https://github.com/ericbalawejder/geolocation/blob/main/mysql/create_geolocation.sql)

After cloning, build the jar file:
```
$ ./gradlew clean assemble
```
or to run the tests with the build:
```
$ ./gradlew clean build 
```
To start the server:
```
$ ./gradlew run
```
Visit the path `http://localhost:8080/api/geolocation/hello` to check if the application is up.

Visit the resource path `http://localhost:8080/api/geolocation/ip/<xxx.xxx>`

where `<xxx.xxx>` is a valid IPv4 or IPv6 address.

#### Tests
To run the tests:
```
$ ./gradlew test
```

### Thoughts
In order of the requirements.

1) The api response contains longitude and latitude. I chose decimal because pinpoint accuracy wasn't an 
initial requirement. [Spatial data types](https://dev.mysql.com/doc/refman/5.7/en/spatial-types.html) for 
longitude and latitude DECIMAL() vs POINT(). 


2) I used an external MySQL datasource. I did not create the database and tables with Jdbi like I normally 
would with Spring Boot's Spring Data JPA annotations in the entity class. We must run the SQL scripts below 
to create the user, database and table.


3) `/api/geolocation/ip/<ip-address>` Updated to use an `Optional<T>` over `Exception` logic. Check the cache. 
If not in the cache, get from the database and load in the cache. If not in the database, make external api 
call with query.


4) I want to throw the checked exceptions themselves without adding ugly try/catches to the stream.
[How can I throw CHECKED exceptions from inside Java 8 streams?](https://stackoverflow.com/questions/27644361/how-can-i-throw-checked-exceptions-from-inside-java-8-streams)


5) `GeolocationTest` tests the serialization and deserialization of an api response and tests for missing and 
extra fields. The problem with testing against an external service is that you do not manage it's state. 
The tests cases may show varying results even if you did not change anything. We could create a mock of the 
api the test object would access. Then we can send requests to the test object and check in the mocked api 
whether the expected requests came in. We can also fake success or error responses and check how the test 
object reacts. The `GeolocationResourceTests` do not register the resources properly. I used a `Dao` -> `Service` -> `Resource` 
with `ExceptionMapper` pattern. This is a configuration issue that I need more time to understand. 
In Spring Boot, the component scan uses class based annotations 
`@Repository` -> `@Service` -> `@WebMvcTest` with internal `@MockBean` and `@Autowired` dependency injection.
I like to separate the test and database dependencies with an `@ActiveProfile("test")` configuration.
I did not test the other CRUD operations because they were just for development purposes.


6) Added `date_created DATETIME(6)` to the schema and [`@Timestamped`](https://jdbi.org/#_timestamped) 
annotation to the `insertGeolocation()` method in the dao to bind an `OffsetDateTime`, of which the value 
is the current time, under the binding `:now`. This field is not part of the deserialization/serialization
of the api response to the Geolocation POJO and vise versa.


7) Did not implement due to time constraints. I would pull a record and do a conditional check for the
elapsed time: <br> 
`time.now() - geolocation.getCreatedOn() > 5 minutes ? makeExternalAPICall() : geolocation`


8) I wrote custom exceptions to be caught in an exception handler/mapper. Spring Boot uses `@ControllerAdvice` 
and `@ExceptionHandler` annotated methods whose signature picks up exceptions thrown from the application. 
I tried to implement this logic but the `RuntimeExceptionMapper` doesn't work as intended. The logger is 
configured to the console output for simplicity. This would usually get configured to an external
directory or service. The ip address query is validated with the Apache Commons Validator `InetAddressValidator`
class. This is a well vetted tool.


I enjoyed this challenge and would need more time to sort out the configuration details since I'm more familiar
with the Spring Boot way. 

### Style
Java line length < 80 or 80 - 120 characters? <br>
New line before end of a class.