package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.EvaluationResult;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationResultRepository extends ElasticsearchRepository<EvaluationResult, String> {
}
