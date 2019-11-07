package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.servers.Server;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class MissingServerInformationRule extends AbstractRuleSpecification {
  @Override
  public String getRuleName() {
    return RuleEnum.MISSING_SERVER_INFORMATION.name();
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.API_STANDARDS;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.MISSING_FIELDS;
  }

  @Override
  public SeverityEnum getSeverity() {
    return SeverityEnum.HIGH;
  }

  @Override
  public String getSummary() {
    return RuleEnum.MISSING_SERVER_INFORMATION.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.MISSING_SERVER_INFORMATION.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.MISSING_SERVER_INFORMATION.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }

  @Override
  public void execute(OpenAPI api) {

    List<Server> serverList = api.getServers();

    if (!serverList.isEmpty() && serverList.size() > 0) {
      Optional<Server> validServer = serverList.stream().filter(server -> !server.getUrl().equals("/")).findAny();

      if (validServer.isPresent()) {
        totalCount = 1;
        successCount = 1;
        return;
      }
    }

    Paths paths = api.getPaths();
    Set<String> keys = paths.keySet();

    for (String path : keys) {
      PathItem pathItem = paths.get(path);
      if (pathItem.getServers() == null || pathItem.getServers().size() == 0) {

        List<Map.Entry<String, Operation>> operations = getOperations(pathItem);

        if (operations != null) {
          for (Map.Entry<String, Operation> operationEntry : operations) {
            totalCount += 1;
            if (operationEntry.getValue().getServers() == null || operationEntry.getValue().getServers().size() == 0) {
              Issue issue = createIssue(buildContext(path, operationEntry.getKey(), operationEntry.getValue()));
              issue.setErrorInfo("Server information is missing for the operation " + operationEntry.getKey() + " which is in the path " + path);
              issues.add(issue);
            } else {
              successCount += 1;
            }
          }
        }
      } else {
        totalCount += 1;
        successCount += 1;
      }

    }
  }

  private Map<String, String> buildContext(String path, String operationId, Operation operation) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.MISSING_SERVER_INFORMATION.name());
    context.put("path", path);
    context.put("operationId", operationId);
    return context;
  }

}
