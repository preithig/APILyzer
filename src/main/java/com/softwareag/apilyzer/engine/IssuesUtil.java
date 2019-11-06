package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.repository.IssuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IssuesUtil {

  private IssuesRepository issuesRepository;

  @Autowired
  public void setIssuesRepository(IssuesRepository issuesRepository) {
    this.issuesRepository = issuesRepository;
  }

  public String createIssue(IRuleSpecification rule, Map<String, String> context) {
    Issue issue = new Issue();
    issue.setDescription(rule.getDescription());
    issue.setErrorInfo(rule.getErrorInfo());
    issue.setName(rule.getRuleName());
    issue.setRemedy(rule.getRemedy());
    issue.setSeverity(rule.getSeverity().name());
    issue.setSummary(rule.getSummary());
    issue.setContext(context);
    issue = issuesRepository.save(issue);
    return issue.getId();

  }

}
