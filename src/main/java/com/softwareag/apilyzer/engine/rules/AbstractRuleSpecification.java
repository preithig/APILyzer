package com.softwareag.apilyzer.engine.rules;

import com.google.common.collect.Lists;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public abstract class AbstractRuleSpecification implements IRuleSpecification {
  List<Issue> issues = Lists.newArrayList();
  int totalCount = 0;
  int successCount = 0;

  @Override
  public int getTotalCount() {
    return totalCount;
  }

  @Override
  public int getSuccessCount() {
    return successCount;
  }


  @Override
  public List<Issue> getIssues() {
    return issues;
  }

  protected Issue createIssue(Map<String, String> context) {
    Issue issue = new Issue();
    issue.setDescription(getDescription());
    issue.setErrorInfo(getErrorInfo());
    issue.setName(getRuleName());
    issue.setRemedy(getRemedy());
    issue.setSeverity(getSeverity().name());
    issue.setSummary(getSummary());
    issue.setContext(context);
    return issue;
  }

  static List<Map.Entry<String, Operation>> getOperations(PathItem p) {
    Predicate<Map.Entry<String, Operation>> nonEmptyPredicate = (e) -> e.getValue() != null;

    Map<String, Operation> opMap = new HashMap<>();
    opMap.put("GET", p.getGet());
    opMap.put("POST", p.getPost());
    opMap.put("PUT", p.getPut());
    opMap.put("DELETE", p.getDelete());
    opMap.put("PATCH", p.getPatch());
    opMap.put("HEAD", p.getHead());
    opMap.put("OPTIONS", p.getOptions());
    opMap.put("TRACE", p.getTrace());

    return opMap.entrySet().stream().filter(nonEmptyPredicate)
        .collect(toList());
  }

}
