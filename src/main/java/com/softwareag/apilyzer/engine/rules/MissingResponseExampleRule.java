package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.RuleEnum;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MissingResponseExampleRule extends MissingExampleRule {

  public void execute(OpenAPI api) {

    Paths paths = api.getPaths();
    Set<String> keys = paths.keySet();

    for (String path : keys) {
      PathItem pathItem = paths.get(path);
      if (pathItem.getServers() == null || pathItem.getServers().size() == 0) {

        List<Map.Entry<String, Operation>> operations = getOperations(pathItem);

        if (operations != null) {
          for (Map.Entry<String, Operation> operationEntry : operations) {
            if (operationEntry.getKey().equals("PUT") || (operationEntry.getKey().equals("POST"))) {
              Operation operation = operationEntry.getValue();
              ApiResponses apiResponses = operation.getResponses();
              if (apiResponses != null) {
                totalCount += 1;
                Set<String> keySet = apiResponses.keySet();
                for (String key : keySet) {
                  ApiResponse apiResponse = apiResponses.get(key);
                  Content content = apiResponse.getContent();
                  if (content != null) {
                    Set<String> contentkeys = content.keySet();
                    for (String contentkey : contentkeys) {
                      MediaType mediaType = content.get(contentkey);
                      Object example = mediaType.getExample();
                      if (example == null) {
                        //create issue
                      }
                    }
                  }
                }
              }
            }
          }
        }
      } else {
        totalCount += 1;
        successCount += 1;
      }

    }

  }

  private Map<String, String> buildContext(Server server) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.MISSING_RESPONSE_EXAMPLE.name());
    context.put("rulepath", server.getUrl());
    return context;
  }

  @Override
  public String getRuleName() {
    return RuleEnum.MISSING_RESPONSE_EXAMPLE.getRulename();
  }

  @Override
  public String getSummary() {
    return RuleEnum.MISSING_RESPONSE_EXAMPLE.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.MISSING_RESPONSE_EXAMPLE.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.MISSING_REQUESTBODY_EXAMPLE.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }


}
