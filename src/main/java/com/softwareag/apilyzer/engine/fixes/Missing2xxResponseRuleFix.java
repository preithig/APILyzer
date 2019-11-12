package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Missing2xxResponseRuleFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    Map<String, String> context = issue.getContext();
    ApiResponse response = new ApiResponse();
    response.setDescription(data.getDescription());
    PathItem pathItem = openAPI.getPaths().get(context.get("rulepath"));
    List<Map.Entry<String, Operation>> operations = IRuleFix.getOperations(pathItem);
    Optional<Map.Entry<String, Operation>> operationOptional = operations.stream().filter(op -> op.getKey().equals(context.get("operationId"))).findAny();
    operationOptional.get().getValue().getResponses().addApiResponse(data.getResponseStatusCode(), response);
    return openAPI;
  }


}
