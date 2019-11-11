package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Rules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulesRepository extends ElasticsearchRepository<Rules, String> {

  List<Rules> findByCreationDateIsLessThanEqualOrderByCreationDateDesc(long date);

}
