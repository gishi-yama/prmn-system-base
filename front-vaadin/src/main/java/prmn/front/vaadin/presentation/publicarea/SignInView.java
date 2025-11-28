package prmn.front.vaadin.presentation.publicarea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import prmn.front.vaadin.presentation.signedarea.SignedMainView;

@Route(SignInView.ROUTE_PATH)
@AnonymousAllowed
public class SignInView extends VerticalLayout implements BeforeEnterObserver {

  public static final String ROUTE_PATH = "SignIn";
  public static final String ROUTE = "/" + ROUTE_PATH;

  private final String authorizationPath;

  public SignInView(@Value("${app.security.azure.registration-id}") String registrationId) {
    // コンストラクタではAzure ADの認可エンドポイントへの導線と画面表示を初期化する
    this.authorizationPath = "/oauth2/authorization/" + registrationId;

    add(new Span("大学アカウントでサインインしてください。"));
    add(new Paragraph("自動的にAzure ADへ遷移しない場合は下のボタンを押してください。"));
    Button startButton = new Button("Azure ADでサインイン", event -> redirectToProvider());
    add(startButton);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    // 既にサインイン済みであればSignedMainViewへ遷移する
    if (isAuthenticated()) {
      event.forwardTo(SignedMainView.class);
      return;
    }
    // 未認証の場合は何もしない（手動サインインボタンを表示）
  }

  private boolean isAuthenticated() {
    // Spring SecurityのAuthenticationを確認し、匿名ユーザーでないことを確かめる
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication != null
      && authentication.isAuthenticated()
      && !(authentication instanceof AnonymousAuthenticationToken);
  }

  private void redirectToProvider() {
    // OAuth2クライアントの認可エンドポイントへブラウザを遷移させる
    getUI().ifPresent(ui -> ui.getPage().setLocation(authorizationPath));
  }
}
