package com.ala.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RedisExampleConfiguration extends Configuration {

    @JsonProperty
    private RedisConfiguration masterRedis;

    public RedisConfiguration getMasterRedis() {
        return masterRedis;
    }

    public void setMasterRedis(RedisConfiguration masterRedis) {
        this.masterRedis = masterRedis;
    }
}
