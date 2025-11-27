package prmn.front.vaadin.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import prmn.front.vaadin.view.SignInView;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

  private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

  public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
    // Azure ADでアカウント選択ダイアログを確実に表示するため、prompt=select_accountを追加するResolverを用意する
    DefaultOAuth2AuthorizationRequestResolver defaultResolver =
      new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
    defaultResolver.setAuthorizationRequestCustomizer(builder ->
      builder.additionalParameters(params -> params.put("prompt", "select_account")));
    this.authorizationRequestResolver = defaultResolver;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // OAuth2ログインの設定とログアウト後の遷移先を定義し、Vaadin側の標準設定を適用する
    http
      .oauth2Login(oauth2 -> oauth2
        .authorizationEndpoint(endpoint -> endpoint.authorizationRequestResolver(authorizationRequestResolver))
        .loginPage(SignInView.ROUTE)
        .defaultSuccessUrl("/signed", true))
      .logout(logout -> logout.logoutSuccessUrl(SignInView.ROUTE));

    super.configure(http);
    setLoginView(http, SignInView.class);
  }
}
