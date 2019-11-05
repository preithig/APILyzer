package com.softwareag.apilyzer.endpoints;

import com.itextpdf.text.DocumentException;
import com.softwareag.apilyzer.manager.ApilyzerManager;
import com.softwareag.apilyzer.model.*;
import com.softwareag.apilyzer.report.APILyzerReport;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ApilyzerController {


  private ApilyzerManager manager;

  @Autowired
  public void setManager(ApilyzerManager manager) {
    this.manager = manager;
  }

  @PostMapping("/evaluate")
  public ResponseEntity<EvaluationResult> evaluate(@RequestParam("file") MultipartFile multipartFile) {
    try {

      InputStream inputStream = multipartFile.getInputStream();
      String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

      EvaluationResult evaluationResult = manager.evaluate(json);
      return new ResponseEntity<>(evaluationResult, HttpStatus.OK);

    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

  }

  @GetMapping("/history")
  public List<EvaluationResult> history() {
    return manager.history();
  }

  @PostMapping("/issue/{id}/fix")
  public ResponseEntity<EvaluationResult> fix(String value) {

    EvaluationResult evaluationResult = manager.fix(value);
    return new ResponseEntity<>(evaluationResult, HttpStatus.OK);

  }

  @GetMapping("{id}/report")
  public ResponseEntity generateReport(@PathVariable String id) {
    //Need to get the evaluation result based on the id
    EvaluationResult result = new EvaluationResult();
    Issue issue = new Issue();
    issue.setSummary("Numeric parameter 'limit' of type 'integer' has no maximum defined");
    issue.setDescription("Some numeric parameters in your API do not have the maximum value specified.");
    issue.setRemedy("Set both the minimum and maximum values for numeric parameters to limit the accepted values to the range that works for your application.");
    issue.setSeverity("Low");

    Issue issue1 = new Issue();
    issue1.setSummary("String parameter 'petId' has no pattern defined");
    issue1.setDescription("Some string parameters in your API do not define any pattern for the accepted strings. This means that they do not limit the values that get passed to the API.");
    issue1.setRemedy("Set a well-defined regular expression in the pattern field of string parameters. This ensures that only strings matching the set pattern get passed to your API.");
    issue1.setSeverity("Low");

    Issue issue2 = new Issue();
    issue2.setSummary("Response that should contain a body has no schema defined");
    issue2.setDescription("You have not defined any schemas for responses that should contain a body.");
    issue2.setRemedy("Define schemas for all responses that should have a body.Alternatively, if you do not want to include a body, you can change the HTTP status code in the response to one that should not have a body.");
    issue2.setSeverity("High");

    List<Issue> issuesList = new ArrayList<>();
    issuesList.add(issue);
    issuesList.add(issue1);

    List<Issue> issuesList1 = new ArrayList<>();
    issuesList1.add(issue2);

    SubCategory subCategory = new SubCategory();
    subCategory.setName("Parameters");
    subCategory.setIssues(issuesList);

    SubCategory subCategory1 = new SubCategory();
    subCategory1.setName("Response Definition");
    subCategory1.setIssues(issuesList1);

    List<SubCategory> subCategoryList = new ArrayList<>();
    subCategoryList.add(subCategory);
    subCategoryList.add(subCategory1);

    Category category = new Category();
    category.setName("API Standard");
    category.setScore(70);
    category.setSubCategory(subCategoryList);

    List<Category> categoryList = new ArrayList<>();
    categoryList.add(category);
    try {
      APILyzerReport report = new APILyzerReport(categoryList);
      byte[] content = report.export();
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Api_Analysis_" + "test" + ".pdf")
          .contentLength(content.length)
          .contentType(MediaType.parseMediaType("application/octet-stream"))
          .body(content);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }

}
