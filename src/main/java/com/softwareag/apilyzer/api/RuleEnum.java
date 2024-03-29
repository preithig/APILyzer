package com.softwareag.apilyzer.api;

public enum RuleEnum {
  MISSING_INFO_DESC("MISSING_INFO_DESC", "Description is missing in the Info section", "Description is missing in the Info section", "To fix this issue, please give a value to the  description field in the Info section", "EASE_OF_USE"),
  MISSING_SERVER_DESC("MISSING_SERVER_DESC", "Description is missing in the Server section", "Description is missing in the Server section", "To fix this issue, please give a value to the  description field in the Server section", "EASE_OF_USE"),
  HTTP_SECURITY_SCHEME("HTTP_SECURITY_SCHEME", "API accepts HTTP requests in the clear", "The API accepts HTTP communications in the clear. HTTP traffic is not encrypted and can thus be easily intercepted.", "Remove http from the schemes list, and only include https", "SECURITY_STANDARDS"),
  MISSING_SERVER_INFORMATION("MISSING_SERVER_INFORMATION", "Server information is missing", "Information about the server is missing in the document", "To fix this issue, please give the server information data at the root level", "API_STANDARDS"),
  OPERATION_2XX_RESPONSE("OPERATION_2XX_RESPONSE", "Operation must have at least one 2xx response.", "Any API operation (endpoint) can fail but presumably it is also meant to do something constructive at some point.It is important to have 2xx response details for each endpoint", "To fix this issue, add a 2xx response details for the endpoint", "API_STANDARDS"),
  MISSING_REQUEST_BODY_EXAMPLE("MISSING_REQUEST_BODY_EXAMPLE", "Example is missing for the request body", "Example is missing for the request body", "To fix this issue, plese add an example as per the provided schema", "EASE_OF_USE"),
  MISSING_RESPONSE_EXAMPLE("MISSING_RESPONSE_EXAMPLE", "Example is missing for the response section", "Example is missing for the response section", "To fix this issue, please add an example as per the provided schema", "EASE_OF_USE");

  private String rulename, summary, description, remedy, category;


  RuleEnum(String rulename, String summary, String description, String remedy, String category) {
    this.rulename = rulename;
    this.summary = summary;
    this.description = description;
    this.remedy = remedy;
    this.category = category;
  }

  public String getCategory() {
    return category;
  }

  public String getRulename() {
    return rulename;
  }

  public String getSummary() {
    return summary;
  }

  public String getDescription() {
    return description;
  }

  public String getRemedy() {
    return remedy;
  }


  public static RuleEnum get(String rulename) {
    for (RuleEnum ruleEnum : RuleEnum.values()) {
      if (ruleEnum.getRulename().equals(rulename)) {
        return ruleEnum;
      }
    }
    return null;
  }


}
