package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleExecutionEngine;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.manager.ApilyzerManager;
import com.softwareag.apilyzer.model.Category;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.RulesConfiguration;
import com.softwareag.apilyzer.model.SubCategory;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.*;

@Component
public class RuleExecutionEngine implements IRuleExecutionEngine {

  private Map<String, Double> categoryMaxScoreMap = new HashMap<>();
  private Map<String, Double> categoryActualScoreMap = new HashMap<>();

  private ApilyzerManager manager;

  private RuleFactory ruleFactory = new RuleFactory();

  @Autowired
  public void setManager(ApilyzerManager manager) {
    this.manager = manager;
  }

  @Override
  public List<IRuleSpecification> getAllRules() {
    List<IRuleSpecification> specs = new ArrayList<>();

    List<RulesConfiguration> rules = manager.getRules().getRules();
    for (RulesConfiguration rule : rules) {
      String ruleName = rule.getRuleName();
      if (rule.isEnabled()) {
        specs.add(ruleFactory.resolveRule(ruleName));
      }
    }
    return specs;
  }

  @Override
  public EvaluationResult evaluate(OpenAPI openAPI) {

    initCategoryScores();

    EvaluationResult result = new EvaluationResult();

    addBasicInfo(result, openAPI);

    evaluateRuleSpecification(openAPI, result);

    calculateCategoryScore(result);

    calculateAPIScore(result);

    return result;
  }

  private void initCategoryScores() {
    categoryMaxScoreMap.put(CategoryEnum.API_STANDARDS.name(), 0.0);
    categoryMaxScoreMap.put(CategoryEnum.EASE_OF_USE.name(), 0.0);
    categoryMaxScoreMap.put(CategoryEnum.SECURITY_STANDARDS.name(), 0.0);
    categoryMaxScoreMap.put(CategoryEnum.PERFORMANCE_STANDARDS.name(), 0.0);

    categoryActualScoreMap.put(CategoryEnum.API_STANDARDS.name(), 0.0);
    categoryActualScoreMap.put(CategoryEnum.EASE_OF_USE.name(), 0.0);
    categoryActualScoreMap.put(CategoryEnum.SECURITY_STANDARDS.name(), 0.0);
    categoryActualScoreMap.put(CategoryEnum.PERFORMANCE_STANDARDS.name(), 0.0);

  }

  private void calculateCategoryScore(EvaluationResult result) {
    for (Category category : result.getCategories()) {
      double max = categoryMaxScoreMap.get(category.getName());
      double actual = categoryActualScoreMap.get(category.getName());
      DecimalFormat dec = new DecimalFormat("#0.00");
      if (max > 0.0) {
        category.setScore(Double.parseDouble(dec.format(actual / max)) * 100);
      }
    }
  }

  private void calculateAPIScore(EvaluationResult result) {
    CategoryEnum[] categories = CategoryEnum.values();
    double apiScore = 0;
    for (CategoryEnum category : categories) {
      Double categoryMaxScore = categoryMaxScoreMap.get(category.name());
      Double categoryActualScore = categoryActualScoreMap.get(category.name());
      DecimalFormat dec = new DecimalFormat("#0.00");
      if (categoryMaxScore > 0) {
        Double categoryScore = Double.parseDouble(dec.format(categoryActualScore / categoryMaxScore)) * 100;
        apiScore += categoryScore;
      } else {
        apiScore += 100;
      }
    }
    apiScore = apiScore / categories.length;
    result.setScore(apiScore);
  }

  private void evaluateRuleSpecification(OpenAPI openAPI, EvaluationResult result) {
    List<IRuleSpecification> rules = getAllRules();
    for (IRuleSpecification rule : rules) {

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

      s.getIssueList().addAll(rule.getIssues());
    } else {
      Category category = any.get();
      Optional<SubCategory> scOptional = category.getSubCategories()
          .stream().filter(s -> s.getName().equals(rule.getSubCategoryName().name())).findAny();
      if (scOptional.isPresent()) {
        SubCategory subCategory = scOptional.get();
        subCategory.getIssueList().addAll(rule.getIssues());
      } else {
        SubCategory s = createSubCategory(rule);
        category.getSubCategories().add(s);
        s.getIssueList().addAll(rule.getIssues());
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

  private void updateScore(Map<String, Double> map, CategoryEnum categoryName, SeverityEnum severity, int count) {
    map.putIfAbsent(categoryName.name(), 0.0);
    Double previousMax = map.get(categoryName.name());

    double newMax = previousMax + (getScoreBySeverity(severity) * count);
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
