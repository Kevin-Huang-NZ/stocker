package com.mahara.stocker;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnnotationTest {
  @BeforeAll
  public static void setup() {
    log.debug("BeforeAll");
  }

  @BeforeEach
  public void init() {
    log.debug("BeforeEach");
  }

  @Order(2)
  @Test
  public void test12() {
    log.debug("test 2");
  }

  @Order(1)
  @Test
  public void test13() {
    log.debug("test 1");
  }

  @Order(3)
  @Test
  public void test33() {
    log.debug("test 3");
  }

  @AfterEach
  public void end() {
    log.debug("AfterEach");
  }


  @AfterAll
  public static void destroy() {
    log.debug("AfterAll");
  }
}
