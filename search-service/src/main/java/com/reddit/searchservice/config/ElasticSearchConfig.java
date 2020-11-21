package com.reddit.searchservice.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticSearchConfig {
    @Value("${spring.elasticsearch.rest.uris}")
    private String restURIs;

    @Value("${spring.data.elasticsearch.cluster-name}")
    private String EsClusterName;

    @Bean
    public RestHighLevelClient ElasticClient() {
        ClientConfiguration clientConfig =
                ClientConfiguration.builder().connectedTo(restURIs).build();
        return RestClients.create(clientConfig).rest();

    }
}
