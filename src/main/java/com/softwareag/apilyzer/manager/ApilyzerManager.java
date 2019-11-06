package com.softwareag.apilyzer.manager;

import com.softwareag.apilyzer.engine.IssuesUtil;
import com.softwareag.apilyzer.model.Api;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.openapi.OpenAPIParser;
import com.softwareag.apilyzer.service.ApiService;
import com.softwareag.apilyzer.service.EvaluationService;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApilyzerManager {

  private ApiService apiService;
  private EvaluationService evaluationService;
  private IssuesUtil issuesUtil;

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

  public EvaluationResult evaluate(String json) {

    OpenAPI openAPI = OpenAPIParser.parse(json);
    EvaluationResult evaluationResult = evaluationService.evaluate(openAPI);
    apiService.save(json, evaluationResult.getId());
    return evaluationResult;
  }

  public List<EvaluationResult> history() {
    return evaluationService.history();
  }

  public EvaluationResult fix(String evaluationId, String issueId, String value) {

    Issue issue = issuesUtil.getIssue(issueId);
    Api api = apiService.findByEvaluationId(evaluationId);
    OpenAPI openApi = OpenAPIParser.parse(api.getApi());
    evaluationService.fix(issue, openApi, value);

    return null;
  }
}
