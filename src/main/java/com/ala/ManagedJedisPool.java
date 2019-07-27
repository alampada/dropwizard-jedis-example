package com.ala;

import com.ala.configuration.RedisConfiguration;
import io.dropwizard.lifecycle.Managed;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;

public class ManagedJedisPool implements Managed {

    private static final Log LOG = LogFactory.getLog(ManagedJedisPool.class);

    private final JedisPool jedisPool;

    public ManagedJedisPool(RedisConfiguration redisConfiguration) {
        jedisPool = new JedisPool(new JedisPoolConfig(),
                URI.create(String.format("redis://%s:%s", redisConfiguration.getHost(), redisConfiguration.getPort())),
                (int) redisConfiguration.getConnectionTimeOut().getQuantity(),
                (int) redisConfiguration.getReadTimeout().getQuantity());
    }

    public void start() throws Exception {
        LOG.info("starting..");
    }

    public void stop() throws Exception {
        LOG.info("Stopping");
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
