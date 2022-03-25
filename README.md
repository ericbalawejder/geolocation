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


3) `/api/geolocation/ip/<ip-address>` If a query is not in the db, throw an exception. Catch the exception 
and make the external api call.


4) The cache works when a geolocation is present in the database but when an external api call is required,
the exception is thrown but not caught in the intended place. The cache is currently not in use. Below is the 
intended logic and code but it is disabled in the endpoint.
```java
public class GeolocationResource {
    ...
    ...
    public GeolocationResource(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(4)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<>() {
                            @Override
                            public Geolocation load(String query) {
                                // If query is not in the cache, this method load() gets executed.
                                // It looks in the db, if present, load it in the cache and return it. 
                                // If query is not in the db, GeolocationNotFoundException is thrown 
                                // here and not in the try block of getGeolocation(). 
                                // makeExternalAPICall() doesn't get executed.
                                return geolocationService.getGeolocation(query);
                            }
                        }
                );
    }

    @GET
    @Path("/ip/{query}")
    public Response getGeolocation(@PathParam("query") String query) throws MalformedURLException {
        if (!InetAddressValidator.getInstance().isValid(query)) {
            throw new IPAddressFormatException();
        }
        try {
            // Get query from the cache
            final Geolocation geolocation = cache.get(query);
            return Response.ok()
                    .entity(geolocation)
                    .build();

        } catch (GeolocationNotFoundException | ExecutionException e) {
            LOGGER.info("making external api call to " + ENDPOINT + query);
            return makeExternalAPICall(query);
        }
    }
}
```


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


6) I did not implement the timestamp logic after having issues with the cache and tests.


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