package prmn.front.vaadin.presentation.publicarea;

import static org.mockito.Mockito.*;

import com.vaadin.flow.router.BeforeEnterEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import prmn.front.vaadin.presentation.signedarea.SignedMainView;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SignInViewTest {

  @Mock
  private BeforeEnterEvent beforeEnterEvent;

  private SignInView signInView;
  private SecurityContext originalSecurityContext;

  @BeforeEach
  void setUp() {
    originalSecurityContext = SecurityContextHolder.getContext();
    signInView = new SignInView("azure");
  }

  @AfterEach
  void tearDown() {
    SecurityContextHolder.setContext(originalSecurityContext);
  }

  @Test
  void beforeEnterForwardsToSignedMainViewWhenUserIsAuthenticated() {
    // 認証済みユーザーを設定
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        "user@example.com",
        null,
        List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    // beforeEnterを実行
    signInView.beforeEnter(beforeEnterEvent);

    // SignedMainViewへのフォワードが呼ばれたことを検証
    verify(beforeEnterEvent).forwardTo(SignedMainView.class);
  }

  @Test
  void beforeEnterDoesNotForwardWhenUserIsUnauthenticated() {
    // 未認証ユーザー（匿名）を設定
    Authentication authentication = new AnonymousAuthenticationToken(
        "anonymous",
        "anonymous",
        List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
    );
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    // beforeEnterを実行
    signInView.beforeEnter(beforeEnterEvent);

    // フォワードが呼ばれないことを検証（サインイン画面を表示）
    verify(beforeEnterEvent, never()).forwardTo(SignedMainView.class);
  }

  @Test
  void beforeEnterDoesNotForwardWhenAuthenticationIsNull() {
    // 認証情報がnullの場合
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(null);
    SecurityContextHolder.setContext(securityContext);

    // beforeEnterを実行
    signInView.beforeEnter(beforeEnterEvent);

    // フォワードが呼ばれないことを検証
    verify(beforeEnterEvent, never()).forwardTo(SignedMainView.class);
  }

  @Test
  void beforeEnterDoesNotForwardWhenAuthenticationIsNotAuthenticated() {
    // 認証されていないユーザー
    Authentication authentication = mock(Authentication.class);
    when(authentication.isAuthenticated()).thenReturn(false);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);

    // beforeEnterを実行
    signInView.beforeEnter(beforeEnterEvent);

    // フォワードが呼ばれないことを検証
    verify(beforeEnterEvent, never()).forwardTo(SignedMainView.class);
  }
}
