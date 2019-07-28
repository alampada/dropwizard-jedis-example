package com.ala.repository;

import com.ala.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisUserRepositoryTest {

    @Mock
    private JedisPool masterPool;

    @Mock
    private JedisPool readPool;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Jedis jedis;

    private RedisUserRepository redisUserRepository;

    @BeforeEach
    public void setUpTestObject() {
        redisUserRepository = new RedisUserRepository(masterPool, readPool, objectMapper);
    }

    @Test
    void shouldReturnUserDetails() throws Exception {
        int id = 42;
        String jsonUser = "jsonStr";
        User user = new User(1, "foo", "bar");
        when(readPool.getResource()).thenReturn(jedis);
        when(jedis.get(String.valueOf(id))).thenReturn(jsonUser);
        when(objectMapper.readValue(jsonUser, User.class)).thenReturn(user);

        assertEquals(user, redisUserRepository.getUser(42));
    }

    @Test
    void shouldStoreUserDetails() throws Exception {
        String jsonStr = "jsonStr";
        User user = new User(1, "foo", "bar");
        when(objectMapper.writeValueAsString(user)).thenReturn(jsonStr);
        when(masterPool.getResource()).thenReturn(jedis);

        redisUserRepository.storeUser(user);
        verify(jedis).setex("1", 30, jsonStr);
    }

}