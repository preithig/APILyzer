package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.Issue;
import com.softwareag.apilyzer.model.SubCategory;
import io.swagger.v3.oas.models.OpenAPI;

import java.text.DecimalFormat;
import java.util.*;
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

        evaluateRuleSpecification(openAPI, result);

        calculateCategoryScore(result);

        calculateAPIScore(result);

        return result;
    }

    private void calculateCategoryScore(EvaluationResult result) {

    }

    private void calculateAPIScore(EvaluationResult result) {
        AtomicInteger sum = new AtomicInteger();
        result.getCategories().forEach(c-> sum.addAndGet(c.getScore()));
        DecimalFormat dec = new DecimalFormat("#0.00");
        result.setScore(Integer.valueOf(dec.format(sum.get()/result.getCategories().size()))*100);
    }

    private void evaluateRuleSpecification(OpenAPI openAPI, EvaluationResult result) {
        for (IRuleSpecification rule : getAllRules()) {
            boolean passed = rule.execute(openAPI);
            if(passed) {
                updateMaxScore(rule.getCategoryName(), rule.getSeverity());
                updateActualScore(rule.getCategoryName(), rule.getSeverity());
            } else {
                updateMaxScore(rule.getCategoryName(), rule.getSeverity());
                updateResult(rule, result);
            }
        }
    }

    private void updateResult(IRuleSpecification rule, EvaluationResult result) {
        Optional<Category> any = result.getCategories().stream().filter(c -> c.getName().equals(rule.getCategoryName().name())).findAny();
        if(!any.isPresent()) {
            Category c = new Category();
            c.setName(rule.getCategoryName().name());
            result.getCategories().add(c);

            SubCategory s = createSubCategory(rule);
            c.getSubCategories().add(s);

            Issue i = createIssue(rule);
            s.getIssues().add(i);
        } else {
            Category category = any.get();
            Optional<SubCategory> scOptional = category.getSubCategories()
                    .stream().filter(s -> s.getName().equals(rule.getSeverity().name())).findAny();
            if (scOptional.isPresent()) {
                SubCategory subCategory = scOptional.get();
                subCategory.getIssues().add(createIssue(rule));
            } else {
                SubCategory s = createSubCategory(rule);
                category.getSubCategories().add(s);
                Issue i = createIssue(rule);
                s.getIssues().add(i);
            }
        }
    }

    private SubCategory createSubCategory(IRuleSpecification rule) {
        SubCategory s = new SubCategory();
        s.setName(rule.getSubCategoryName().name());
        return s;
    }

    private Issue createIssue(IRuleSpecification rule) {
        Issue i = new Issue();
        i.setName(rule.getRuleName());
        i.setDescription(rule.getDescription());
        i.setErrorInfo(rule.getErrorInfo());
        i.setSeverity(rule.getSeverity().name());
        i.setSummary(rule.getSummary());
        i.setRemedy(rule.getRemedy());
        i.setContext(rule.getContext());
        return i;
    }

    private void updateActualScore(CategoryEnum categoryName, SeverityEnum severity) {
        updateScore(categoryActualScoreMap, categoryName, severity);
    }

    private void updateMaxScore(CategoryEnum categoryName, SeverityEnum severity) {
        updateScore(categoryMaxScoreMap, categoryName, severity);
    }

    private void updateScore(Map<String, Integer> map, CategoryEnum categoryName, SeverityEnum severity) {
        map.putIfAbsent(categoryName.name(), 0);
        Integer previousMax = map.get(categoryName.name());

        int newMax = previousMax + getScoreBySeverity(severity);
        map.put(categoryName.name(), newMax);
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
