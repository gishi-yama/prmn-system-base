package prmn.front.vaadin.service.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

class AuthenticatedUserServiceTest {

  private AuthenticatedUserService service;

  @BeforeEach
  void setUp() {
    service = new AuthenticatedUserService();
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenAuthenticationIsNull() {
    // 認証情報がnullの場合は空を返す
    SecurityContextHolder.clearContext();

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenAuthenticationIsNotOAuth2AuthenticationToken() {
    // 認証情報がOAuth2AuthenticationTokenでない場合は空を返す
    UsernamePasswordAuthenticationToken notOAuth2Token =
        new UsernamePasswordAuthenticationToken("user", "password");
    SecurityContextHolder.getContext().setAuthentication(notOAuth2Token);

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenEmailAttributeIsMissing() {
    // メール属性がない場合は空を返す
    OAuth2User user = new DefaultOAuth2User(
        java.util.Collections.emptyList(),
        Map.of("sub", "user-id"),
        "sub");
    OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
        user, java.util.Collections.emptyList(), "azure");
    SecurityContextHolder.getContext().setAuthentication(token);

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenEmailAttributeIsNotString() {
    // メール属性がStringでない場合は空を返す
    OAuth2User user = new DefaultOAuth2User(
        java.util.Collections.emptyList(),
        Map.of("sub", "user-id", "email", 12345),
        "sub");
    OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
        user, java.util.Collections.emptyList(), "azure");
    SecurityContextHolder.getContext().setAuthentication(token);

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenEmailAttributeIsBlank() {
    // メール属性がブランクの場合は空を返す
    OAuth2User user = new DefaultOAuth2User(
        java.util.Collections.emptyList(),
        Map.of("sub", "user-id", "email", "   "),
        "sub");
    OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
        user, java.util.Collections.emptyList(), "azure");
    SecurityContextHolder.getContext().setAuthentication(token);

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmpty_whenEmailAttributeIsEmptyString() {
    // メール属性が空文字の場合は空を返す
    OAuth2User user = new DefaultOAuth2User(
        java.util.Collections.emptyList(),
        Map.of("sub", "user-id", "email", ""),
        "sub");
    OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
        user, java.util.Collections.emptyList(), "azure");
    SecurityContextHolder.getContext().setAuthentication(token);

    Optional<String> result = service.findEmail();

    assertThat(result).isEmpty();
  }

  @Test
  void findEmail_shouldReturnEmail_whenAllConditionsAreMet() {
    // すべての条件を満たす場合はメールを返す
    String expectedEmail = "test@example.com";
    OAuth2User user = new DefaultOAuth2User(
        java.util.Collections.emptyList(),
        Map.of("sub", "user-id", "email", expectedEmail),
        "sub");
    OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(
        user, java.util.Collections.emptyList(), "azure");
    SecurityContextHolder.getContext().setAuthentication(token);

    Optional<String> result = service.findEmail();

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(expectedEmail);
  }
}
