package com.softwareag.apilyzer.endpoints;

import com.itextpdf.text.DocumentException;
import com.softwareag.apilyzer.manager.ApilyzerManager;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.report.APILyzerReport;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
  public void fix() {

  }

  @GetMapping("{id}/report")
  public ResponseEntity generateReport(@PathVariable String id) {
    //Need to get the evaluation result based on the id
    EvaluationResult result = new EvaluationResult();
    try {
      APILyzerReport report = new APILyzerReport(result.getCategories());
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }

}
