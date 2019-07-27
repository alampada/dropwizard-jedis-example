package com.ala.health;

import com.codahale.metrics.health.HealthCheck;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisHealthCheck extends HealthCheck {

    private final JedisPool jedisPool;

    public RedisHealthCheck(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    protected Result check() throws Exception {
        try (Jedis jedis = jedisPool.getResource()) {
            String echo = jedis.echo("health");
            return "health".equals("health") ? Result.healthy() : Result.unhealthy("got: " + echo);
        }
    }
}
