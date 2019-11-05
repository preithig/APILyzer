package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.model.EvaluationResult;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.Collections;
import java.util.List;

public class RuleExecutionEngine implements IRuleExecutionEngine {

    @Override
    public List<IRuleSpecification> getAllRules() {
        return Collections.emptyList();
    }

    @Override
    public EvaluationResult evaluate(OpenAPI openAPI) {

        EvaluationResult result = new EvaluationResult();

        addBasicInfo(result, openAPI);

        evaluateRuleSpecification(result, openAPI);

        evaluateAPIScore(result, openAPI);

        return result;
    }

    private void evaluateAPIScore(EvaluationResult result, OpenAPI openAPI) {

        int sum = 0;
        result

    }

    private void evaluateRuleSpecification(EvaluationResult result, OpenAPI openAPI) {

    }

    private void addBasicInfo(EvaluationResult result, OpenAPI openAPI) {
        result.setApiName(openAPI.getInfo().getTitle());
    }
}
