package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Api;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ApiRepository extends ElasticsearchRepository<Api, String> {

}
