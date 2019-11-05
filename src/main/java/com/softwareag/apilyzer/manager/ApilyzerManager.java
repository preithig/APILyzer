package com.softwareag.apilyzer.manager;

import com.softwareag.apilyzer.model.EvalutionResult;
import com.softwareag.apilyzer.openapi.OpenAPIParser;
import com.softwareag.apilyzer.service.ApiService;
import com.softwareag.apilyzer.service.EvaluationService;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApilyzerManager {

  private ApiService apiService;
  private EvaluationService evaluationService;


  @Autowired
  public void setEvaluationService(EvaluationService evaluationService) {
    this.evaluationService = evaluationService;
  }

  @Autowired
  public void setApiService(ApiService apiService) {
    this.apiService = apiService;
  }

  public EvalutionResult evaluate(String json) {

    OpenAPI openAPI = OpenAPIParser.parse(json);
    EvalutionResult evaluationResult = evaluationService.evaluate(openAPI);
    apiService.save(json, evaluationResult.getId());
    return evaluationResult;
  }
}
