package com.softwareag.apilyzer.service;

import com.softwareag.apilyzer.engine.FixUtil;
import com.softwareag.apilyzer.engine.RuleExecutionEngine;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.repository.EvaluationResultRepository;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EvaluationService {

  private EvaluationResultRepository evaluationResultRepository;
  private RuleExecutionEngine executionEngine;


  @Autowired
  public void setExecutionEngine(RuleExecutionEngine executionEngine) {
    this.executionEngine = executionEngine;
  }

  @Autowired
  public void setEvaluationResultRepository(EvaluationResultRepository evaluationResultRepository) {
    this.evaluationResultRepository = evaluationResultRepository;
  }


  public EvaluationResult evaluate(OpenAPI openAPI) {
    EvaluationResult evalutionResult = executionEngine.evaluate(openAPI);
    evalutionResult = evaluationResultRepository.save(evalutionResult);
    return evalutionResult;
  }

  public List<EvaluationResult> history() {
    Iterable<EvaluationResult> evaluationResultIterable = evaluationResultRepository.findAll();
    return StreamSupport.stream(evaluationResultIterable.spliterator(), false).collect(Collectors.toList());
  }

  public EvaluationResult findById(String id) {
    return evaluationResultRepository.findById(id).get();
  }

  public OpenAPI fix(Issue issue, OpenAPI openAPI, Object data) {
    return FixUtil.fix(issue, openAPI, data);
  }
}
