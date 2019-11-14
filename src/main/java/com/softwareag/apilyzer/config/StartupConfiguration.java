package com.softwareag.apilyzer.config;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.model.Rules;
import com.softwareag.apilyzer.model.RulesConfiguration;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.softwareag.apilyzer"})
public class StartupConfiguration {


  @Value("${elasticsearch.host:localhost}")
  public String host;

  @Value("${elasticsearch.port:9300}")
  public int port;

  private ElasticsearchRepository<Rules, String> repository;

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  @Autowired
  public void setRepository(ElasticsearchRepository<Rules, String> repository) {
    this.repository = repository;
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient restHighLevelClient) {
    return new ElasticsearchRestTemplate(restHighLevelClient);
  }

  @PostConstruct
  public void insertDefaultRules() {
    Rules rules = new Rules();
    List<RulesConfiguration> rulesConfigurationList = new ArrayList<>();
    RuleEnum[] ruleSets = RuleEnum.values();
    for (RuleEnum ruleSet : ruleSets) {
      RulesConfiguration rulesConfiguration = new RulesConfiguration();
      rulesConfiguration.setRuleName(ruleSet.getRulename());
      rulesConfiguration.setEnabled(true);
      rulesConfiguration.setCategory(ruleSet.getCategory());
      rulesConfiguration.setSummary(ruleSet.getSummary());
      rulesConfigurationList.add(rulesConfiguration);
    }
    rules.setRules(rulesConfigurationList);
    rules.setCreationDate(new Date());
    repository.save(rules);
  }


}
