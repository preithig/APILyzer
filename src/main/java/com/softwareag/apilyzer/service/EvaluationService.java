package com.softwareag.apilyzer.service;

import com.softwareag.apilyzer.engine.RuleExecutionEngine;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.repository.EvaluationResultRepository;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

  private EvaluationResultRepository evaluationResultRepository;

  @Autowired
  public void setEvaluationResultRepository(EvaluationResultRepository evaluationResultRepository) {
    this.evaluationResultRepository = evaluationResultRepository;
  }

  public EvaluationResult evaluate(OpenAPI openAPI) {
    EvaluationResult evalutionResult = new RuleExecutionEngine().evaluate(openAPI);
    evalutionResult = evaluationResultRepository.save(evalutionResult);
    return evalutionResult;
  }
}
