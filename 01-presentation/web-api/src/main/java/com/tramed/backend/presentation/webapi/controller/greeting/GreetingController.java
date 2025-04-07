package com.tramed.backend.presentation.webapi.controller.greeting;

import com.tramed.backend.applicationcore.systemmanagement.service.GreetingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tra-med-api/greeting")
public class GreetingController {

  private final GreetingService greetingService;

  @RequestMapping("/hello-world")
  public String greeting(@RequestParam(value = "name", required = false) String name) {
    return greetingService.sayHello(name);
  }
}
