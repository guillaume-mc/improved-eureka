package com.mastercard.foundry.template.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for the Mint Test API.
 * This class is responsible for bootstrapping and launching the Spring Boot application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mastercard.foundry.template"})
public class MintTestApiJavaApplication {

  /**
   * Main method, used as the entry point for the application.
   *
   * @param args Command line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(MintTestApiJavaApplication.class, args);
  }

}