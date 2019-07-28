package com.ala;

import com.ala.configuration.RedisConfiguration;
import com.ala.configuration.RedisExampleConfiguration;
import com.ala.health.RedisHealthCheck;
import com.ala.repository.RedisUserRepository;
import com.ala.repository.UserRepository;
import com.ala.resources.RedisExampleResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import redis.clients.jedis.JedisPool;

public class RedisExampleApplication extends Application<RedisExampleConfiguration> {

    public static void main(String[] args) throws Exception {
        new RedisExampleApplication().run(args);
    }

    public void run(RedisExampleConfiguration redisExampleConfiguration, Environment environment) throws Exception {
        final JedisPool masterRedis = buildAndRegisterRedisPool(redisExampleConfiguration.getMasterRedis(),
                environment, "master-redis");

        final JedisPool readRedis = buildAndRegisterRedisPool(redisExampleConfiguration.getMasterRedis(),
                environment, "read-redis");

        final UserRepository userRepository = new RedisUserRepository(masterRedis, readRedis, new ObjectMapper());

        final RedisExampleResource redisExampleResource = new RedisExampleResource(userRepository);
        environment.jersey().register(redisExampleResource);
    }

    private JedisPool buildAndRegisterRedisPool(RedisConfiguration configuration,
                                                Environment environment,
                                                String name) {
        final ManagedJedisPool managedJedisPool = new ManagedJedisPool(configuration);
        environment.lifecycle().manage(managedJedisPool);
        environment.healthChecks().register(name, new RedisHealthCheck(managedJedisPool.getJedisPool()));
        return managedJedisPool.getJedisPool();

    }
}
