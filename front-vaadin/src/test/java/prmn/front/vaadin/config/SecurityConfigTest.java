package prmn.front.vaadin.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void signedViewRequiresAuthentication() throws Exception {
    // 未ログイン状態で/signedへアクセスすると/SignInへリダイレクトされることを検証する
    mockMvc.perform(get("/signed"))
      .andExpect(status().isFound())
      .andExpect(redirectedUrlPattern("**/SignIn"));
  }

  @Test
  void signInPathIsPublic() throws Exception {
    // /SignInは誰でもアクセスできHTTP200を返すことを検証する
    mockMvc.perform(get("/SignIn"))
      .andExpect(status().isOk());
  }
}
