package com.softwareag.apilyzer.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

public class OpenAPIParser {

  public static OpenAPI parse(String json) {
    SwaggerParseResult swaggerParseResult = new OpenAPIV3Parser().readContents(json);
    return swaggerParseResult.getOpenAPI();
  }
}
