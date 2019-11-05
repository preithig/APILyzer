package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.model.EvalutionResult;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.Collections;
import java.util.List;

public class RuleExecutionEngine implements IRuleExecutionEngine {

    @Override
    public List<IRuleSpecification> getAllRules() {
        return Collections.emptyList();
    }

    @Override
    public EvalutionResult evaluate(OpenAPI openAPI) {

        EvalutionResult result = new EvalutionResult();

        addBasicInfo(result, openAPI);

        evaluateRuleSpecification(result, openAPI);

        evaluateAPIScore(result, openAPI);

        return result;
    }

    private void evaluateAPIScore(EvalutionResult result, OpenAPI openAPI) {

    }

    private void evaluateRuleSpecification(EvalutionResult result, OpenAPI openAPI) {

    }

    private void addBasicInfo(EvalutionResult result, OpenAPI openAPI) {
        result.setApiName(openAPI.getInfo().getTitle());
    }
}
