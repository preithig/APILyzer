package com.softwareag.apilyzer.service;

import com.google.common.collect.Lists;
import com.softwareag.apilyzer.engine.FixUtil;
import com.softwareag.apilyzer.engine.RuleExecutionEngine;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.repository.EvaluationResultRepository;
import com.softwareag.apilyzer.repository.IssuesRepository;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EvaluationService {

  private EvaluationResultRepository evaluationResultRepository;
  private IssuesRepository issuesRepository;

  @Autowired
  public void setIssuesRepository(IssuesRepository issuesRepository) {
    this.issuesRepository = issuesRepository;
  }

  @Autowired
  public void setEvaluationResultRepository(EvaluationResultRepository evaluationResultRepository) {
    this.evaluationResultRepository = evaluationResultRepository;
  }


  public EvaluationResult evaluate(OpenAPI openAPI) {
    EvaluationResult evalutionResult = new RuleExecutionEngine().evaluate(openAPI);

    evalutionResult.getCategories().stream().forEach(category -> {
      category.getSubCategories().stream().forEach(subCategory -> {
        List<Issue> issueList = subCategory.getIssueList();
        for (Issue issue : issueList) {
          Issue issueSaved = issuesRepository.save(issue);
          subCategory.getIssues().add(issueSaved.getId());
        }
      });
    });

    evalutionResult.getCategories().stream().forEach(category -> {
      category.getSubCategories().stream().forEach(subCategory -> {
        subCategory.setIssueList(null);
      });
    });

    evalutionResult = evaluationResultRepository.save(evalutionResult);

    return getEvaluationResult(evalutionResult.getId());
  }

  private EvaluationResult getEvaluationResult(String id) {
    Optional<EvaluationResult> evaluationResult = evaluationResultRepository.findById(id);

    if (!evaluationResult.isPresent()) {
      return null;
    }
    evaluationResult.get().getCategories().stream().forEach(category -> {
      category.getSubCategories().stream().forEach(subCategory -> {
        subCategory.setIssueList(Lists.newArrayList());
        for (String issueId : subCategory.getIssues()) {

          subCategory.getIssueList().add(issuesRepository.findById(issueId).get());
        }
      });
    });
    return evaluationResult.get();
  }

  public List<EvaluationResult> history() {
    Iterable<EvaluationResult> evaluationResultIterable = evaluationResultRepository.findAll();
    return StreamSupport.stream(evaluationResultIterable.spliterator(), false).collect(Collectors.toList());
  }

  public EvaluationResult findById(String id) {
    return evaluationResultRepository.findById(id).get();
  }

  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    return FixUtil.fix(issue, openAPI, data);
  }
}
