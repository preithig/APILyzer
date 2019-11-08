package com.softwareag.apilyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareag.apilyzer.model.Api;
import com.softwareag.apilyzer.repository.ApiRepository;
import io.swagger.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class ApiService {

  private ApiRepository apiRepository;

  @Autowired
  public void setApiRepository(ApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  public Api save(OpenAPI openAPI, String evaluationId) throws IOException {
    Api api = new Api();
    api.setApi(Json.pretty(openAPI));
    api.setApiName(openAPI.getInfo().getTitle());
    api.setEvaluationId(evaluationId);
    return apiRepository.save(api);
  }

  public Api get(String id) {
    return apiRepository.findById(id).get();
  }

  public Api findByEvaluationId(String eId) {
    Optional<Api> apiOptional = apiRepository.findApiByEvaluationId(eId);
    if (apiOptional.isPresent()) {
      return apiOptional.get();
    }
    return null;
  }


}
