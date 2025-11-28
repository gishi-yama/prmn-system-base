package prmn.front.vaadin.presentation.signedarea;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import prmn.front.vaadin.application.common.AuthenticatedUserService;
import prmn.front.vaadin.presentation.signedarea.common.AbstractAuthenticatedView;
import prmn.front.vaadin.application.domain.Greeting;
import prmn.front.vaadin.infrastructure.HelloAPIRepository;

@Route("signed")
@PermitAll
public class SignedMainView extends AbstractAuthenticatedView {

  private final HelloAPIRepository backend;

  public SignedMainView(AuthenticatedUserService authenticatedUserService, HelloAPIRepository backend) {
    super(authenticatedUserService);
    this.backend = backend;
  }

  @Override
  protected void renderAuthenticatedContent(String email) {
    // 認証済みメールアドレスを画面に表示するのみで、認証確認や遷移は親クラスに委譲する
    add(new NativeLabel("Signed in as: " + email));

    NativeLabel nativeLabel = new NativeLabel();
    Button clickMe = new Button("Click me", event -> {
      Greeting greeting = backend.greet(email);
      nativeLabel.setText(greeting.getGreetingMessage());
    });
    add(nativeLabel);
    add(clickMe);
  }
}
