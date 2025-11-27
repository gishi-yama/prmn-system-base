package prmn.front.vaadin.security;

import com.vaadin.flow.spring.annotation.UIScope;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AuthenticatedUserService {

  public Optional<String> findEmail() {
    // SecurityContextからOAuth2Userを取り出し、メール属性が無い場合は未ログイン扱いで空を返す
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof OAuth2AuthenticationToken token)) {
      return Optional.empty();
    }
    OAuth2User user = token.getPrincipal();
    return Optional.ofNullable(user.getAttribute("email"))
      .filter(String.class::isInstance)
      .map(String.class::cast)
      .filter(text -> !text.isBlank());
  }
}
