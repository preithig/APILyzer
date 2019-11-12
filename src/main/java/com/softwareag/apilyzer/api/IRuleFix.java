package com.softwareag.apilyzer.api;

import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public interface IRuleFix {

  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data);

  static List<Map.Entry<String, Operation>> getOperations(PathItem p) {
    Predicate<Map.Entry<String, Operation>> nonEmptyPredicate = (e) -> e.getValue() != null;

    Map<String, Operation> opMap = new HashMap<>();
    opMap.put("GET", p.getGet());
    opMap.put("POST", p.getPost());
    opMap.put("PUT", p.getPut());
    opMap.put("DELETE", p.getDelete());
    opMap.put("PATCH", p.getPatch());
    opMap.put("HEAD", p.getHead());
    opMap.put("OPTIONS", p.getOptions());
    opMap.put("TRACE", p.getTrace());

    return opMap.entrySet().stream().filter(nonEmptyPredicate)
        .collect(toList());
  }
}
