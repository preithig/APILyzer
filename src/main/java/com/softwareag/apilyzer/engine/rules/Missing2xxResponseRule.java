package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Missing2xxResponseRule extends AbstractRuleSpecification {

  @Override
  public String getRuleName() {
    return RuleEnum.OPERATION_2XX_RESPONSE.getRulename();
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.API_STANDARDS;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.RESPONSE_DETAILS;
  }

  @Override
  public SeverityEnum getSeverity() {
    return SeverityEnum.MEDIUM;
  }

  @Override
  public String getSummary() {
    return RuleEnum.OPERATION_2XX_RESPONSE.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.OPERATION_2XX_RESPONSE.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.OPERATION_2XX_RESPONSE.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }

  @Override
  public void execute(OpenAPI api) {
    Set<String> paths = api.getPaths().keySet();

    for (String path : paths) {
      AtomicBoolean positiveStatusCode = new AtomicBoolean(false);
      PathItem pathItem = api.getPaths().get(path);
      List<Map.Entry<String, Operation>> operations = getOperations(pathItem);
      if (operations != null) {
        for (Map.Entry<String, Operation> operationEntry : operations) {
          totalCount += 1;
          Set<String> statusCodes = operationEntry.getValue().getResponses().keySet();
          for (String status : statusCodes) {
            if (status.startsWith("2")) {
              successCount += 1;
              positiveStatusCode.set(true);
              break;
            }
          }
          if (!positiveStatusCode.get()) {
            Issue issue = createIssue(buildContext(path, operationEntry.getKey()));
            issue.setErrorInfo("Missing in the operation:: " + operationEntry.getKey() + "  in the path:: " + path);
            issues.add(issue);
          }

        }
      }

    }
  }

  private Map<String, String> buildContext(String path, String operationId) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.OPERATION_2XX_RESPONSE.name());
    context.put("rulepath", path);
    context.put("operationId", operationId);
    return context;
  }

  @Override
  public List<Issue> getIssues() {
    return issues;
  }


}
