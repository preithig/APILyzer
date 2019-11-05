package com.softwareag.apilyzer.api;

import com.softwareag.apilyzer.model.EvaluationResult;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public interface IRuleExecutionEngine {

    List<IRuleSpecification> getAllRules();

    EvaluationResult evaluate(OpenAPI openAPI);
}
