package com.ala.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.util.Duration;

public class RedisConfiguration {

    @JsonProperty
    private String host;

    @JsonProperty
    private String port;

    @JsonProperty
    private Duration connectionTimeOut = Duration.seconds(5);

    @JsonProperty
    private Duration readTimeout = Duration.seconds(5);

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Duration getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(Duration connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}
