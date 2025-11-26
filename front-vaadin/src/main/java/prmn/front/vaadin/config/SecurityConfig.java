package prmn.front.vaadin.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import prmn.front.vaadin.view.SignInView;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // OAuth2ログインの設定とログアウト後の遷移先を定義し、Vaadin側の標準設定を適用する
    http
      .oauth2Login(oauth2 -> oauth2
        .loginPage(SignInView.ROUTE)
        .defaultSuccessUrl("/signed", true))
      .logout(logout -> logout.logoutSuccessUrl(SignInView.ROUTE));

    super.configure(http);
    setLoginView(http, SignInView.class);
  }
}
