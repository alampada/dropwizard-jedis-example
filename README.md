# Example Dropwizard Application that uses Jedis to connect to Redis

Example dropwizard application exposing a API to create a "user" and store it in redis.
Users in redis are stored for 30 seconds.

The configuration (`config.yml`) expects one "master" redis endpoint for writes and a redis endpoint for reads.
For example to leverage [this](https://aws.amazon.com/about-aws/whats-new/2019/06/amazon-elasticache-launches-reader-endpoint-for-redis/).

To run the application, update config.yml to point to the appropriate redis endpoints and then run:

```$xslt
mvn package
java -jar target/dropwizard-redis-1.0-SNAPSHOT.jar server config.yml
```
 
The application provides the following endpoints:

Create a "user" through a POST request, such as:


```
POST localhost:8080/user
{
    "id": 2,
    "firstName": "foo",
    "lastName": "bar"
}
```

and retrieve the user through GET:

```
GET localhost:8080/user/2
```

