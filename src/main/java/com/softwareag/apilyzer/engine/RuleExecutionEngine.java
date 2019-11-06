package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.engine.rules.MissingDescriptionRule;
import com.softwareag.apilyzer.engine.rules.SecuritySchemeRule;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.SubCategory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RuleExecutionEngine implements IRuleExecutionEngine {

  private Map<String, Integer> categoryMaxScoreMap = new HashMap<>();
  private Map<String, Integer> categoryActualScoreMap = new HashMap<>();

  private SecuritySchemeRule securitySchemeRule;
  private MissingDescriptionRule missingDescriptionRule;

  @Autowired
  public void setSecuritySchemeRule(SecuritySchemeRule securitySchemeRule) {
    this.securitySchemeRule = securitySchemeRule;
  }

  @Autowired
  public void setMissingDescriptionRule(MissingDescriptionRule missingDescriptionRule) {
    this.missingDescriptionRule = missingDescriptionRule;
  }

  @Override
  public List<IRuleSpecification> getAllRules() {
    List<IRuleSpecification> specs = new ArrayList<>();

    specs.add(securitySchemeRule);
    specs.add(missingDescriptionRule);

    return specs;
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
    for (Category category : result.getCategories()) {
      int max = categoryMaxScoreMap.get(category.getName());
      int actual = categoryActualScoreMap.get(category.getName());

      DecimalFormat dec = new DecimalFormat("#0.00");
      category.setScore(Integer.valueOf(dec.format(actual / max)) * 100);
    }
  }

  private void calculateAPIScore(EvaluationResult result) {
    AtomicInteger sum = new AtomicInteger();
    result.getCategories().forEach(c -> sum.addAndGet(c.getScore()));
    DecimalFormat dec = new DecimalFormat("#0.00");
    result.setScore(Integer.valueOf(dec.format(sum.get() / result.getCategories().size())) * 100);
  }

  private void evaluateRuleSpecification(OpenAPI openAPI, EvaluationResult result) {
    for (IRuleSpecification rule : getAllRules()) {

      rule.execute(openAPI);

      updateMaxScore(rule.getCategoryName(), rule.getSeverity(), rule.getTotalCount());

      updateActualScore(rule.getCategoryName(), rule.getSeverity(), rule.getSuccessCount());

      if (rule.getIssues() != null && !rule.getIssues().isEmpty()) {
        updateResult(rule, result);
      }
    }
  }

  private void updateResult(IRuleSpecification rule, EvaluationResult result) {
    Optional<Category> any = result.getCategories().stream()
        .filter(c -> c.getName().equals(rule.getCategoryName().name())).findAny();
    if (!any.isPresent()) {
      Category c = new Category();
      c.setName(rule.getCategoryName().name());
      result.getCategories().add(c);

      SubCategory s = createSubCategory(rule);
      c.getSubCategories().add(s);

      s.getIssues().addAll(rule.getIssues());
    } else {
      Category category = any.get();
      Optional<SubCategory> scOptional = category.getSubCategories()
          .stream().filter(s -> s.getName().equals(rule.getSeverity().name())).findAny();
      if (scOptional.isPresent()) {
        SubCategory subCategory = scOptional.get();
        subCategory.getIssues().addAll(rule.getIssues());
      } else {
        SubCategory s = createSubCategory(rule);
        category.getSubCategories().add(s);
        s.getIssues().addAll(rule.getIssues());
      }
    }
  }

  private SubCategory createSubCategory(IRuleSpecification rule) {
    SubCategory s = new SubCategory();
    s.setName(rule.getSubCategoryName().name());
    return s;
  }

  private void updateActualScore(CategoryEnum categoryName, SeverityEnum severity, int totalCount) {
    updateScore(categoryActualScoreMap, categoryName, severity, totalCount);
  }

  private void updateMaxScore(CategoryEnum categoryName, SeverityEnum severity, int successCount) {
    updateScore(categoryMaxScoreMap, categoryName, severity, successCount);
  }

  private void updateScore(Map<String, Integer> map, CategoryEnum categoryName, SeverityEnum severity, int count) {
    map.putIfAbsent(categoryName.name(), 0);
    Integer previousMax = map.get(categoryName.name());

    int newMax = previousMax + (getScoreBySeverity(severity) * count);
    map.put(categoryName.name(), newMax);
  }

  private Integer getScoreBySeverity(SeverityEnum severity) {
    switch (severity) {
      case LOW:
        return 1;
      case MEDIUM:
        return 2;
      case HIGH:
        return 3;
      default:
        return 1;
    }
  }

  private void addBasicInfo(EvaluationResult result, OpenAPI openAPI) {
    result.setApiName(openAPI.getInfo().getTitle());
  }
}
