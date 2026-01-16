package com.mastercard.foundry.template.webapp;

import com.mastercard.foundry.template.service.LibrarySample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * HelloController exposes several endpoints for demonstration purposes.
 * It includes a simple hello endpoint and an addition endpoint using the
 * service module.
 */
@RestController
public class HelloController {

  /**
   *  helloEndpoint provides a "Hello World!" message.
   *
   * @return a Mono containing the "Hello World!" message.
   */
  @GetMapping("/hello")
  public Mono<String> helloEndpoint() {
    return Mono.just("Hello World!");
  }

  /**
   * add endpoint takes two integers as path variables and returns their sum.
   * It utilizes the LibrarySample#add method from the template-service module.
   *
   * @param x the first integer.
   * @param y the second integer.
   * @return a Mono containing the sum of x and y as a String.
   */
  @GetMapping("/add/{x}/{y}")
  public Mono<String> add(@PathVariable int x, @PathVariable int y) {
    int result = LibrarySample.add(x, y);
    return Mono.just(String.valueOf(result));
  }
}