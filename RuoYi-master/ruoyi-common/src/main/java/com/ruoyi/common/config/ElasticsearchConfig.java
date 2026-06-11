package com.ruoyi.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Elasticsearch配置类
 * 使用elasticsearch-java原生客户端（兼容Spring Boot 4.x）
 *
 * <p>默认disabled=true，不影响正常启动。启用需：</p>
 * <ol>
 *   <li>安装Elasticsearch 8.x</li>
 *   <li>修改application.yml中elasticsearch.enabled=true</li>
 * </ol>
 */
@Configuration
public class ElasticsearchConfig
{
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchConfig.class);

    /**
     * ES配置属性（始终创建，供其他组件读取enabled状态）
     */
    @Bean
    @ConfigurationProperties(prefix = "elasticsearch")
    public ElasticsearchProperties elasticsearchProperties()
    {
        return new ElasticsearchProperties();
    }

    /**
     * 创建RestClient - 仅在ES启用时创建
     */
    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(prefix = "elasticsearch", name = "enabled", havingValue = "true")
    public RestClient restClient(ElasticsearchProperties properties)
    {
        try
        {
            RestClient client = RestClient.builder(
                    new HttpHost(properties.getHost(), properties.getPort(), properties.getScheme()))
                    .setRequestConfigCallback(requestConfigBuilder ->
                            requestConfigBuilder
                                    .setConnectTimeout(properties.getConnectTimeout())
                                    .setSocketTimeout(properties.getSocketTimeout())
                                    .setConnectionRequestTimeout(properties.getConnectionRequestTimeout())
                    )
                    .build();

            log.info("Elasticsearch RestClient创建成功: {}://{}:{}", properties.getScheme(), properties.getHost(), properties.getPort());
            return client;
        }
        catch (Exception e)
        {
            log.error("创建Elasticsearch RestClient失败", e);
            throw new RuntimeException("无法连接到Elasticsearch", e);
        }
    }

    /**
     * 创建ElasticsearchTransport - 仅在ES启用时创建
     */
    @Bean
    @ConditionalOnProperty(prefix = "elasticsearch", name = "enabled", havingValue = "true")
    public ElasticsearchTransport elasticsearchTransport(RestClient restClient)
    {
        return new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );
    }

    /**
     * 创建ElasticsearchClient - 仅在ES启用时创建
     */
    @Bean
    @ConditionalOnProperty(prefix = "elasticsearch", name = "enabled", havingValue = "true")
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport)
    {
        log.info("Elasticsearch Client创建成功");
        return new ElasticsearchClient(transport);
    }

    /**
     * Elasticsearch配置属性类
     */
    public static class ElasticsearchProperties
    {
        private String host = "localhost";
        private int port = 9200;
        private String username = "elastic";
        private String password = "changeme";
        private String scheme = "http";
        private boolean enabled = false;
        private int connectTimeout = 5000;
        private int socketTimeout = 30000;
        private int connectionRequestTimeout = 5000;

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }

        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getScheme() { return scheme; }
        public void setScheme(String scheme) { this.scheme = scheme; }

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }

        public int getConnectTimeout() { return connectTimeout; }
        public void setConnectTimeout(int connectTimeout) { this.connectTimeout = connectTimeout; }

        public int getSocketTimeout() { return socketTimeout; }
        public void setSocketTimeout(int socketTimeout) { this.socketTimeout = socketTimeout; }

        public int getConnectionRequestTimeout() { return connectionRequestTimeout; }
        public void setConnectionRequestTimeout(int connectionRequestTimeout) { this.connectionRequestTimeout = connectionRequestTimeout; }
    }
}
