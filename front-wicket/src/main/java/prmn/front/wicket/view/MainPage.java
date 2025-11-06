package prmn.front.wicket.view;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.util.ObjectUtils;
import org.wicketstuff.annotation.mount.MountPath;
import prmn.front.wicket.model.Greeting;
import prmn.front.wicket.model.IHelloAPIRepository;

@WicketHomePage
@MountPath("MainPage")
public class MainPage extends WebPage {

  @SpringBean
  private IHelloAPIRepository repository;

  private Greeting greeting;

  public MainPage() {
    greeting = null;

    var greetingModel = LambdaModel.of(() -> {
      // 本来は下のようにLoadableDetachableModelを使った方が良いが、わかりやすさ重視で
      if (ObjectUtils.isEmpty(greeting)) {
        return "";
      }
      return greeting.getGreetingMessage();
    });

//    var greetingModel
//      = LoadableDetachableModel.of(() -> repository.greet().getGreetingMessage());

    var greetingLabel = new Label("greetingLabel", greetingModel) {
      @Override
      protected void onInitialize() {
        super.onInitialize();
        greeting = repository.greet();
        setOutputMarkupId(true);
      }
    };

    var clickMeLink = new AjaxLink<>("clickMeLink") {

      @Override
      public void onClick(AjaxRequestTarget target) {
        greeting = repository.greet();
        target.add(getPage().get("greetingLabel"));
      }
    };

    add(greetingLabel);
    add(clickMeLink);

  }
}
