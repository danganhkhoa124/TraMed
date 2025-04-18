package com.tramed.backend.presentation.webapi.controller.greeting;

import com.tramed.backend.applicationcore.systemcore.service.greeting.GreetingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tra-med-api/greeting")
public class GreetingController {

  private final GreetingService greetingService;

  @GetMapping("/hello-world")
  public String greeting(@RequestParam(value = "name", required = false) String name) {
    return greetingService.sayHello(name);
  }
}
