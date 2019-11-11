package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Rules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends ElasticsearchRepository<Rules, String> {
}
