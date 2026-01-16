package com.mastercard.foundry.template.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibrarySampleTest {
  @Test
  void add() {
    assertEquals(5, LibrarySample.add(2, 3));
  }
}
