package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Issue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuesRepository extends ElasticsearchRepository<Issue, String> {
}
