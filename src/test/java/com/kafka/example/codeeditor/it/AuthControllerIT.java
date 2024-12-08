package com.kafka.example.codeeditor.it;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.kafka.example.codeeditor.domain.dto.AuthResponse;
import com.kafka.example.codeeditor.domain.model.User;
import com.kafka.example.codeeditor.it.support.AbstractIntegrationTest;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class AuthControllerIT extends AbstractIntegrationTest {

  @Test
  @DisplayName("Should register a user, save to the database and return tokens")
  void shouldRegisterUser() {
    String payload = initRegisterRequest();

    String response = buildRequestSpecification()
        .contentType("application/json")
        .body(payload)
        .when()
        .post("/api/v1/auth/register")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("accessToken", notNullValue())
        .body("refreshToken", notNullValue())
        .extract().response().asString();

    log.info(response);

    try {
      AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
      assertThat(authResponse.getAccessToken()).isNotNull();
      assertThat(authResponse.getRefreshToken()).isNotNull();

    } catch (Exception e) {
      log.error("Error parsing response", e);
    }

    Optional<User> user = userRepository.findByEmail("yeganexelilova6466@gmail.com");
    assertTrue(user.isPresent(), "User should be saved in the database");
    assertEquals("Yegana", user.get().getName(), "User name should match");
    assertEquals("Khalil", user.get().getSurname(), "User surname should match");
  }

  @Test
  @DisplayName("Should login a user and return tokens")
  void shouldLoginUser() {
    String payload = initLoginRequest();

    String response = buildRequestSpecification()
        .contentType("application/json")
        .body(payload)
        .when()
        .post("/api/v1/auth/login")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("accessToken", notNullValue())
        .body("refreshToken", notNullValue())
        .extract().response().asString();

    log.info(response);

    try {
      AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
      assertThat(authResponse.getAccessToken()).isNotNull();
      assertThat(authResponse.getRefreshToken()).isNotNull();

    } catch (Exception e) {
      log.error("Error parsing response", e);
    }

    Optional<User> user = userRepository.findByEmail("yeganexelilova6466@gmail.com");
    assertTrue(user.isPresent(), "User should be saved in the database");
    assertEquals("yeganexelilova6466@gmail.com", user.get().getEmail());
  }

  private static String initRegisterRequest() {
    return """
        {
           "name": "Yegana",
           "surname": "Khalil",
           "email": "yeganexelilova6466@gmail.com",
           "password": "Pass1234@",
           "confirmPassword": "Pass1234@"
        }
        """;
  }
  private static String initLoginRequest() {
    return """
        {
           "email": "yeganexelilova6466@gmail.com",
           "password": "Pass1234@"
                   }
        """;
  }
}
