package prmn.front.vaadin.publicarea.main.presentation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import prmn.front.vaadin.signedarea.greeting.infrastructure.HelloAPIRepository;
import prmn.front.vaadin.signedarea.greeting.domain.Greeting;

@Route("")
@AnonymousAllowed
public class MainView extends VerticalLayout {

  public MainView(HelloAPIRepository backend) {
    // 未認証ユーザー向けのトップ画面を構築し、ボタンでAPIを呼び出す

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
