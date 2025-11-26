package prmn.front.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import prmn.front.vaadin.model.Greeting;
import prmn.front.vaadin.model.HelloAPIRepository;
import jakarta.annotation.security.PermitAll;

@Route("signed")
@PermitAll
public class SignedMainView extends VerticalLayout {

  public SignedMainView(HelloAPIRepository backend) {
    // サインイン済み利用者向けの画面を構築し、APIレスポンスをボタンで表示する

    NativeLabel nativeLabel = new NativeLabel();
    Button clickMe = new Button("Click me", e
      -> {
      Greeting greeting = backend.greet();
      nativeLabel.setText(greeting.getGreetingMessage());
    });

    add(nativeLabel);
    add(clickMe);
  }


}
