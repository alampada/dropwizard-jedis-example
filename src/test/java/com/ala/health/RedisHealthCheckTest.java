package com.ala.health;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RedisHealthCheckTest {

    @Mock
    JedisPool jedisPool;

    @InjectMocks
    RedisHealthCheck redisHealthCheck;

    @Test
    void shouldReturnHealthyWhenHealth() throws Exception {
        Jedis jedis = mock(Jedis.class);
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.echo("health")).thenReturn("health");

        assertTrue(redisHealthCheck.check().isHealthy());
    }

    @Test
    void shouldThrowfJedisThrows() throws Exception {
        Jedis jedis = mock(Jedis.class);
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.echo("health")).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> redisHealthCheck.check().isHealthy());
    }

    @Test
    void shouldReturnUnhealthyIfJedisDoesntEcho() throws Exception {
        Jedis jedis = mock(Jedis.class);
        when(jedisPool.getResource()).thenReturn(jedis);
        when(jedis.echo("health")).thenReturn("bam");

        assertFalse(redisHealthCheck.check().isHealthy());

    }
}