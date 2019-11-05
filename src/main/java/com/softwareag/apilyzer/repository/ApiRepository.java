package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Api;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends ElasticsearchRepository<Api, String> {

}
