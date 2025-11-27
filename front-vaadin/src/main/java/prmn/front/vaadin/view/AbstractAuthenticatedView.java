package prmn.front.vaadin.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.Optional;
import prmn.front.vaadin.security.AuthenticatedUserService;
import prmn.front.vaadin.view.SignInView;

public abstract class AbstractAuthenticatedView extends VerticalLayout {

  private final AuthenticatedUserService authenticatedUserService;
  private boolean contentRendered = false;

  protected AbstractAuthenticatedView(AuthenticatedUserService authenticatedUserService) {
    // コンストラクタではサービスを保持するのみで、実際の画面構築はonAttachで行う
    this.authenticatedUserService = authenticatedUserService;
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    super.onAttach(attachEvent);
    if (contentRendered) {
      return;
    }
    // 画面がUIにアタッチされたタイミングでメールアドレスを確認し、取得結果に応じて遷移・描画を行う
    authenticatedUserService.findEmail()
      .ifPresentOrElse(email -> {
          contentRendered = true;
          renderAuthenticatedContent(email);
        },
        () -> handleMissingSession(attachEvent.getUI()));
  }

  private void handleMissingSession(UI ui) {
    // 認証情報が取得できない場合は通知を出し、サインイン画面へ誘導する
    Notification.show("セッションが切れています。再度サインインしてください。");
    Optional.ofNullable(ui).ifPresent(current -> current.navigate(SignInView.class));
  }

  /**
   * サブクラスで実際のUI構築を行うための抽象メソッド。引数にはサインイン済みメールアドレスが渡る。
   */
  protected abstract void renderAuthenticatedContent(String email);
}
