package com.tramed.backend.applicationcore.systemcore.service.greeting;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GreetingService {

  public String sayHello(String name) {
    if (name != null && !name.isEmpty()) {
      return "Hello " + name + "!";
    }
    return "Hello World!";
  }
}
