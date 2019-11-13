package com.softwareag.apilyzer.engine.fixes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.apptik.json.JsonElement;
import io.apptik.json.generator.JsonGenerator;
import io.apptik.json.schema.SchemaV4;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MissingRequestBodyExampleFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    Map<String, String> context = issue.getContext();
    PathItem pathItem = openAPI.getPaths().get(context.get("rulepath"));
    List<Map.Entry<String, Operation>> operations = IRuleFix.getOperations(pathItem);
    Optional<Map.Entry<String, Operation>> operationOptional = operations.stream().filter(op -> op.getKey().equals(context.get("operationId"))).findAny();
    RequestBody requestBody = operationOptional.get().getValue().getRequestBody();
    MediaType mediaType = requestBody.getContent().get(context.get("contentKey"));
    try {
      if (context.get("contentKey").equals("application/json")) {

        Schema schema = mediaType.getSchema();
        io.apptik.json.schema.Schema ss = new SchemaV4().wrap(JsonElement.readFrom(new ObjectMapper().writeValueAsString(schema)).asJsonObject());
        JsonElement elem = new JsonGenerator(ss, null).generate();
        mediaType.setExample(elem.toString());
      }
    } catch (IOException e) {

    }
    return openAPI;
  }
}
