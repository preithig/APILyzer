package com.softwareag.apilyzer.api;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

public interface IRuleFix {

  public boolean fix(Issue issue, OpenAPI openAPI, Object data);
}