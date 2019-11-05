package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.model.EvaluationResult;
import io.swagger.v3.oas.models.OpenAPI;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RuleExecutionEngine implements IRuleExecutionEngine {

    private Map<String, Integer> categoryMaxScoreMap = new HashMap<>();
    private Map<String, Integer> categoryActualScoreMap = new HashMap<>();

    @Override
    public List<IRuleSpecification> getAllRules() {
        return Collections.emptyList();
    }

    @Override
    public EvaluationResult evaluate(OpenAPI openAPI) {

        EvaluationResult result = new EvaluationResult();

        addBasicInfo(result, openAPI);

        evaluateRuleSpecification(result, openAPI);

        evaluateAPIScore(result);

        return result;
    }

    private void evaluateAPIScore(EvaluationResult result) {
        AtomicInteger sum = new AtomicInteger();
        result.getCategories().forEach(c-> sum.addAndGet(c.getScore()));
        DecimalFormat dec = new DecimalFormat("#0.00");
        result.setScore(Integer.valueOf(dec.format(sum.get()/result.getCategories().size()))*100);
    }

    private void evaluateRuleSpecification(EvaluationResult result, OpenAPI openAPI) {
        for (IRuleSpecification rule : getAllRules()) {
            boolean passed = rule.execute(openAPI);
            if(passed) {
                updateMaxScore(rule.getCategoryName(), rule.getSeverity());
                //updateActualScore(rule.getCategoryName(), rule.getSeverity());
            }
        }
    }

    private void updateMaxScore(CategoryEnum categoryName, SeverityEnum severity) {
        categoryMaxScoreMap.putIfAbsent(categoryName.name(), 0);
        Integer previousMax = categoryMaxScoreMap.get(categoryName.name());

        int newMax = previousMax + getScoreBySeverity(severity);
        categoryMaxScoreMap.put(categoryName.name(), newMax);
    }

    private Integer getScoreBySeverity(SeverityEnum severity) {
        switch (severity) {
            case LOW: return 1;
            case MEDIUM: return 2;
            case HIGH: return 3;
            default:
                return 1;
        }
    }

    private void addBasicInfo(EvaluationResult result, OpenAPI openAPI) {
        result.setApiName(openAPI.getInfo().getTitle());
    }
}
