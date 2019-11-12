package com.softwareag.apilyzer.engine.fixes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MissingResponseExampleFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    Map<String, String> context = issue.getContext();
    PathItem pathItem = openAPI.getPaths().get(context.get("rulepath"));
    List<Map.Entry<String, Operation>> operations = IRuleFix.getOperations(pathItem);
    Optional<Map.Entry<String, Operation>> operationOptional = operations.stream().filter(op -> op.getKey().equals(context.get("operationId"))).findAny();
    ApiResponse apiResponse = operationOptional.get().getValue().getResponses().get(context.get("responseKey"));
    MediaType mediaType = apiResponse.getContent().get(context.get("contentKey"));

    try {
      if (context.get("contentKey").equals("application/json")) {

        Schema schema = mediaType.getSchema();
        String schemaType = schema.getType();

        if (schemaType.equalsIgnoreCase("string")) {

        } else {
          Map<String, Schema> stringSchemaMap = schema.getProperties();
          Set<String> keys = stringSchemaMap.keySet();
          JsonNode jsonNode = new ObjectMapper().createObjectNode();
          for (String key : keys) {
            Schema s = stringSchemaMap.get(key);

            if (s.getType().equalsIgnoreCase("string") && s.getPattern() != null) {
              ((ObjectNode) jsonNode).put(key, "123");
            } else if (s.getType().equalsIgnoreCase("Integer") && s.getMinimum() != null
                && s.getMaximum() != null) {
              ((ObjectNode) jsonNode).put(key, (int) (Math.random() * (s.getMaximum().intValue() - s.getMinimum().intValue())) + s.getMinimum().intValue());
            }
          }

          mediaType.setExample(new ObjectMapper().writeValueAsString(jsonNode));
        }

      }
    } catch (IOException e) {
      e.printStackTrace();
      ;
    }

    return openAPI;
  }
}
