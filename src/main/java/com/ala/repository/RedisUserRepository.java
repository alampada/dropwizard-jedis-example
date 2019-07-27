package com.ala.repository;

import com.ala.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.io.IOException;

public class RedisUserRepository implements UserRepository {

    private static final Log LOG = LogFactory.getLog(RedisUserRepository.class);

    private final JedisPool jedisPool;

    private final ObjectMapper objectMapper;

    public RedisUserRepository(JedisPool jedisPool, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.jedisPool = jedisPool;
    }

    public User getUser(int id) {
        try (Jedis jedis = jedisPool.getResource()) {
            return objectMapper.readValue(jedis.get(String.valueOf(id)), User.class);
        }
        catch (IOException e) {
            LOG.error("unable to read user json", e);
            throw new RuntimeException(e);
        }
    }

    public void storeUser(User user) {
        try {
            final String jsonStr = objectMapper.writeValueAsString(user);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.setex(String.valueOf(user.getId()), 30, jsonStr);
            }
        }
        catch (JsonProcessingException e) {
            LOG.error("unable to convert to json", e);
        }
    }
}
