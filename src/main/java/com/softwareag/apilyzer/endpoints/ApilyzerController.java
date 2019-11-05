package com.softwareag.apilyzer.endpoints;

import com.softwareag.apilyzer.manager.ApilyzerManager;
import com.softwareag.apilyzer.model.EvaluationResult;
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

}
