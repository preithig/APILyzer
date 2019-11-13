package com.softwareag.apilyzer.endpoints;

import com.itextpdf.text.DocumentException;
import com.softwareag.apilyzer.exception.NotValidAPIException;
import com.softwareag.apilyzer.manager.ApilyzerManager;
import com.softwareag.apilyzer.model.Api;
import com.softwareag.apilyzer.model.EvaluationResult;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Rules;
import com.softwareag.apilyzer.report.APILyzerReport;
import com.softwareag.apilyzer.repository.RulesRepository;
import com.softwareag.apilyzer.service.EvaluationService;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ApilyzerController {

  private ApilyzerManager manager;

  private EvaluationService evaluationService;

  private RulesRepository rulesRepository;

  @Autowired
  public void setManager(ApilyzerManager manager) {
    this.manager = manager;
  }

  @Autowired
  public void setEvaluationService(EvaluationService evaluationService) {
    this.evaluationService = evaluationService;
  }

  @Autowired
  public void setRulesRepository(RulesRepository rulesRepository) {
    this.rulesRepository = rulesRepository;
  }

  @PostMapping("/evaluate")
  public ResponseEntity<EvaluationResult> evaluate(@RequestParam("file") MultipartFile multipartFile) {
    try {

      InputStream inputStream = multipartFile.getInputStream();
      String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

      EvaluationResult evaluationResult = manager.evaluate(json);
      return new ResponseEntity<>(evaluationResult, HttpStatus.OK);

    } catch (IOException | NotValidAPIException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

  }

  @GetMapping("/history")
  public List<EvaluationResult> history() {
    return manager.history();
  }

  @PostMapping("/evaluations/{evaluationId}/issues/{issueId}/fix")
  public ResponseEntity<EvaluationResult> fix(@PathVariable String evaluationId, @PathVariable String issueId, @RequestBody FixData fixData) {
    try {
      new URL(fixData.getUrl());
      EvaluationResult evaluationResult = manager.fix(evaluationId, issueId, fixData);
      return new ResponseEntity<>(evaluationResult, HttpStatus.OK);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

    }
  }

  @PostMapping("/rules")
  public ResponseEntity<Rules> createRules(@RequestBody Rules rulesConfigurations) {
    rulesConfigurations.setCreationDate(new Date());
    rulesConfigurations = rulesRepository.save(rulesConfigurations);
    return new ResponseEntity<>(rulesConfigurations, HttpStatus.OK);
  }

  @GetMapping("/rules")
  public Rules getRules() {
    return manager.getRules();
  }

  @GetMapping("{id}/report")
  public ResponseEntity generateReport(@PathVariable String id) {
    EvaluationResult result = evaluationService.getEvaluationResult(id);
    try {
      APILyzerReport report = new APILyzerReport(result.getCategories());
      byte[] content = report.export();
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Api_Analysis_" + result.getApiName() + ".pdf")
          .contentLength(content.length)
          .contentType(MediaType.parseMediaType("application/octet-stream"))
          .body(content);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }


  @GetMapping("/{id}/download")
  public ResponseEntity downloadAPI(@PathVariable String id) {
    Api api = manager.download(id);
    byte[] content = api.getApi().getBytes(StandardCharsets.UTF_8);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + api.getApiName() + ".json")
        .contentLength(content.length)
        .contentType(MediaType.parseMediaType("application/octet-stream"))
        .body(content);
  }

}
