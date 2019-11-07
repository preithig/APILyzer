package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
      List<Operation> operations = api.getPaths().get(path).readOperations();
      totalCount += operations.size();
      for (Operation op : operations) {
        Set<String> statusCodes = op.getResponses().keySet();
        for (String status : statusCodes) {
          if (status.startsWith("2")) {
            successCount += 1;
            positiveStatusCode.set(true);
            break;
          }
        }
        if (!positiveStatusCode.get()) {
          issues.add(createIssue(buildContext(path, op)));
        }
      }
    }
  }

  private Map<String, String> buildContext(String path, Operation op) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.OPERATION_2XX_RESPONSE.name());
    context.put("rulepath", path);
    context.put("operationId",op.getOperationId());
    return context;
  }

  @Override
  public List<Issue> getIssues() {
    return issues;
  }


}
