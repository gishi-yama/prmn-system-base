package prmn.frontvaadin.model;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Repository
public class HelloAPIRepository {

  private final RestClient restClient;

  public HelloAPIRepository(RestClient.Builder builder) {
    this.restClient = builder
      .baseUrl("http://localhost:8081")
      .build();
  }

  public Greeting greet() {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("mailaddress", "prmn@example.com");

    return restClient.post()
      .uri("/greet")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(body)
      .retrieve()
      .body(Greeting.class);
  }

}
