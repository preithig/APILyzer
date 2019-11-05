package com.softwareag.apilyzer.endpoints;

import com.softwareag.apilyzer.openapi.OpenAPIParser;
import com.softwareag.apilyzer.service.ApilyzerService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/rest")
public class ApilyzerController {

  private ApilyzerService apilyzerService;

  @Autowired
  public void setApilyzerService(ApilyzerService apilyzerService) {
    this.apilyzerService = apilyzerService;
  }

  @PostMapping("/evaluate")
  public void evaluate(@RequestParam("file") MultipartFile multipartFile) {
    try {

      InputStream inputStream = multipartFile.getInputStream();
      String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

      OpenAPIParser parser = new OpenAPIParser();
      parser.parse(json);
      apilyzerService.save(json);


    } catch (IOException e) {

    }

  }

  @GetMapping("/history")
  public Object history() {
    return null;
  }

  @PostMapping("/issue/{id}/fix")
  public void fix() {

  }

}
