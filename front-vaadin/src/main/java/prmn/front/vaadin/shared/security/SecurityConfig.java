package prmn.front.vaadin.shared.security;

import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import prmn.front.vaadin.publicarea.signin.presentation.SignInView;

@EnableWebSecurity
@Configuration
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public class SecurityConfig {

  private final OAuth2AuthorizationRequestResolver authorizationRequestResolver;

  public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
    // Azure ADのアカウント選択ダイアログを強制するため、prompt=select_accountを常に付与する
    DefaultOAuth2AuthorizationRequestResolver defaultResolver =
      new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
    defaultResolver.setAuthorizationRequestCustomizer(builder ->
      builder.additionalParameters(params -> params.put("prompt", "select_account")));
    this.authorizationRequestResolver = defaultResolver;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Spring Security 6推奨のDSLでOAuth2ログインとログアウト遷移を構成し、VaadinSecurityConfigurerを適用する
    http
      .oauth2Login(oauth2 -> oauth2
        .authorizationEndpoint(endpoint -> endpoint.authorizationRequestResolver(authorizationRequestResolver))
        .loginPage(SignInView.ROUTE)
        .defaultSuccessUrl("/signed", true))
      .logout(logout -> logout.logoutSuccessUrl(SignInView.ROUTE));

    return http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
      configurer.loginView(SignInView.class);
    }).build();
  }
}
