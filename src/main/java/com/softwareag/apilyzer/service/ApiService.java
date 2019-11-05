package com.softwareag.apilyzer.service;

import com.softwareag.apilyzer.model.Api;
import com.softwareag.apilyzer.repository.ApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiService {

  private ApiRepository apiRepository;

  @Autowired
  public void setApiRepository(ApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  public Api save(String json, String evaluationId) {
    Api api = new Api();
    api.setApi(json);
    api.setEvaluationId(evaluationId);
    return apiRepository.save(api);
  }

  public Api get(String id) {
    return apiRepository.findById(id).get();
  }


}
