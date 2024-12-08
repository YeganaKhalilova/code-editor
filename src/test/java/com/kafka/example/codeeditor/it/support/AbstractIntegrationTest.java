package com.kafka.example.codeeditor.it.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.example.codeeditor.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public abstract class AbstractIntegrationTest {

  @LocalServerPort
  protected int port;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected UserRepository userRepository;

  @AfterEach
  void cleanup() {
    userRepository.deleteAll();
  }

  protected RequestSpecification buildRequestSpecification() {
    return RestAssured.given().port(port);
  }

}
