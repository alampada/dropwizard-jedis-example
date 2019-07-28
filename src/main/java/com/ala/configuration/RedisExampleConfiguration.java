package com.ala.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RedisExampleConfiguration extends Configuration {

    @JsonProperty
    private RedisConfiguration masterRedis;

    @JsonProperty
    private RedisConfiguration readRedis;

    public RedisConfiguration getMasterRedis() {
        return masterRedis;
    }

    public RedisConfiguration getReadRedis() {
        return readRedis;
    }

    public void setMasterRedis(RedisConfiguration masterRedis) {
        this.masterRedis = masterRedis;
    }

    public void setReadRedis(RedisConfiguration readRedis) {
        this.readRedis = readRedis;
    }
}
