package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Missing2xxResponseRuleFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    Map<String, String> context = issue.getContext();
    ApiResponse response = new ApiResponse();
    response.setDescription(data.getDescription());
    PathItem pathItem = openAPI.getPaths().get(context.get("rulepath"));
    List<Operation> operations = pathItem.readOperations();
    List<Operation> targetOperation = operations.stream().filter(op -> op.getOperationId().equals(context.get("operationId"))).collect(Collectors.toList());
    targetOperation.get(0).getResponses().addApiResponse(data.getResponseStatusCode(), response);
    return openAPI;
  }
}
