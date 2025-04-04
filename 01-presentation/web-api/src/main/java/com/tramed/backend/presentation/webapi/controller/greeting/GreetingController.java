package com.tramed.backend.presentation.webapi.controller.greeting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tra-med-api/greeting")
public class GreetingController {
  @RequestMapping("/hello-world")
  public String greeting() {
    return "Hello World!";
  }
}
