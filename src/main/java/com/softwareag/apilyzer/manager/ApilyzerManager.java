package com.softwareag.apilyzer.manager;

import com.softwareag.apilyzer.engine.IssuesUtil;
import com.softwareag.apilyzer.exception.NotValidAPIException;
import com.softwareag.apilyzer.model.*;
import com.softwareag.apilyzer.openapi.OpenAPIParser;
import com.softwareag.apilyzer.repository.RulesRepository;
import com.softwareag.apilyzer.service.ApiService;
import com.softwareag.apilyzer.service.EvaluationService;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class ApilyzerManager {

  private ApiService apiService;
  private EvaluationService evaluationService;
  private IssuesUtil issuesUtil;
  private RulesRepository rulesRepository;

  @Autowired
  public void setIssuesUtil(IssuesUtil issuesUtil) {
    this.issuesUtil = issuesUtil;
  }

  @Autowired
  public void setEvaluationService(EvaluationService evaluationService) {
    this.evaluationService = evaluationService;
  }

  @Autowired
  public void setApiService(ApiService apiService) {
    this.apiService = apiService;
  }

  @Autowired
  public void setRulesRepository(RulesRepository rulesRepository) {
    this.rulesRepository = rulesRepository;
  }

  public EvaluationResult evaluate(String json) throws NotValidAPIException, IOException {

    OpenAPI openAPI = OpenAPIParser.parse(json);
    if (openAPI == null) {
      throw new NotValidAPIException("This OpenAPI is invalid");
    }
    EvaluationResult evaluationResult = evaluationService.evaluate(openAPI);
    apiService.save(openAPI, evaluationResult.getId());
    return evaluationResult;
  }

  public List<EvaluationResult> history() {
    return evaluationService.history();
  }

  public EvaluationResult fix(String evaluationId, String issueId, FixData fixData) throws IOException {

    Issue issue = issuesUtil.getIssue(issueId);
    Api api = apiService.findByEvaluationId(evaluationId);
    OpenAPI openApi = OpenAPIParser.parse(api.getApi());
    OpenAPI fixedOpenApi = evaluationService.fix(issue, openApi, fixData);
    EvaluationResult evaluationResult = evaluationService.evaluate(fixedOpenApi);
    apiService.save(fixedOpenApi, evaluationResult.getId());
    return evaluationResult;

  }

  public EvaluationResult getEvaluationResult(String evaluationID) {
    return evaluationService.getEvaluationResult(evaluationID);
  }

  public Api download(String evaluationId) {
    Api api = apiService.findByEvaluationId(evaluationId);
    return api;
  }

  public Rules getRules() {
    Instant instant = Instant.now();
    long timeStampMillis = instant.toEpochMilli();
    List<Rules> rulesList = rulesRepository.findByCreationDateIsLessThanEqualOrderByCreationDateDesc(timeStampMillis);
    return rulesList.get(0);
  }

}
