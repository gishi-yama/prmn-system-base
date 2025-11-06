package prmn.front.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import prmn.front.vaadin.model.HelloAPIRepository;
import prmn.front.vaadin.model.Greeting;

@Route
public class MainView extends VerticalLayout {

  public MainView(HelloAPIRepository backend) {

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
