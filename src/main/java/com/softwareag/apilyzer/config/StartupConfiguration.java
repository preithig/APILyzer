package com.softwareag.apilyzer.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"org.softwareag.techinterrupt.apilyzer"})
public class StartupConfiguration {


  @Value("${elasticsearch.host:localhost}")
  public String host;
  @Value("${elasticsearch.port:9300}")
  public int port;

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

 /* @Bean
  public Client client() {
    TransportClient client = null;
    try {
      System.out.println("host:" + host + "port:" + port);
      client = new PreBuiltTransportClient(Settings.EMPTY)
          .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return client;
  }*/

  @Bean
  public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
    return new ElasticsearchRestTemplate(restHighLevelClient);
  }


}
