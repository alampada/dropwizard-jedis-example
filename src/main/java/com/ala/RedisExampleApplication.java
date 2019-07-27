package com.ala;

import com.ala.configuration.RedisExampleConfiguration;
import com.ala.health.RedisHealthCheck;
import com.ala.repository.RedisUserRepository;
import com.ala.repository.UserRepository;
import com.ala.resources.RedisExampleResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class RedisExampleApplication extends Application<RedisExampleConfiguration> {

    public static void main(String[] args) throws Exception {
        new RedisExampleApplication().run(args);
    }

    public void run(RedisExampleConfiguration redisExampleConfiguration, Environment environment) throws Exception {
        final ManagedJedisPool managedJedisPool = new ManagedJedisPool(redisExampleConfiguration.getMasterRedis());
        environment.lifecycle().manage(managedJedisPool);
        environment.healthChecks().register("master-redis", new RedisHealthCheck(managedJedisPool.getJedisPool()));

        final UserRepository userRepository = new RedisUserRepository(managedJedisPool.getJedisPool(), new ObjectMapper());

        final RedisExampleResource redisExampleResource = new RedisExampleResource(userRepository);
        environment.jersey().register(redisExampleResource);

    }
}
