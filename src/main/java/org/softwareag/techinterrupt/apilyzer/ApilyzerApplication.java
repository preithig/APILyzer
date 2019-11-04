package org.softwareag.techinterrupt.apilyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApilyzerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApilyzerApplication.class, args);
  }

  @GetMapping("/")
  public String get(){
    return "This";
  }



}
