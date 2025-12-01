package prmn.front.vaadin.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SecurityConfigTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  void disableRedirects() {
    restTemplate.getRestTemplate().setRequestFactory(new SimpleClientHttpRequestFactory() {
      @Override
      protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        super.prepareConnection(connection, httpMethod);
        connection.setInstanceFollowRedirects(false);
      }
    });
  }

  @Test
  void signedViewRequiresAuthentication() {
    // 未ログイン状態で/signedへアクセスするとログインビューが返ることを検証する
    ResponseEntity<String> response = restTemplate.getForEntity(url("/signed"), String.class);

    assertEquals(HttpStatus.FOUND, response.getStatusCode());
    assertEquals("/SignIn", response.getHeaders().getLocation().getPath());
  }

  @Test
  void signInPathIsPublic() {
    // /SignInは誰でもアクセスできHTTP200を返すことを検証する
    ResponseEntity<String> response = restTemplate.getForEntity(url("/SignIn"), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  private String url(String path) {
    return "http://localhost:" + port + path;
  }
}
