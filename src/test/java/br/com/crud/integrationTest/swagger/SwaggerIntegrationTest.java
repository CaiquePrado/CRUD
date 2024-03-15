package br.com.crud.integrationTest.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.crud.config.TestsConfig;
import br.com.crud.integrationTest.testcontainers.AbstractIntegrationTest;
 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {
 
  @Test
  @DisplayName("Should Display Swagger UI Page")
  void testShouldDisplaySwaggerUiPage() {
      var content = given()
          .basePath("/swagger-ui/index.html")
          .port(TestsConfig.SERVER_PORT)
          .when()
              .get()
          .then()
              .statusCode(200)
          .extract()
              .body()
                  .asString();
      assertTrue(content.contains("Swagger UI"));
  }
}
