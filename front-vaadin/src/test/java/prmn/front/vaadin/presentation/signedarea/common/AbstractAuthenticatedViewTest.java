package prmn.front.vaadin.presentation.signedarea.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import prmn.front.vaadin.presentation.publicarea.SignInView;
import prmn.front.vaadin.service.common.AuthenticatedUserService;

class AbstractAuthenticatedViewTest {

  private AuthenticatedUserService authenticatedUserService;
  private UI mockUI;

  @BeforeEach
  void setUp() {
    authenticatedUserService = mock(AuthenticatedUserService.class);
    mockUI = mock(UI.class);
  }

  @Test
  void onAttach_withAuthenticatedUser_rendersContent() {
    // Arrange: ログインユーザーのメールアドレスが取得できる状態を準備
    String email = "test@example.com";
    when(authenticatedUserService.findEmail()).thenReturn(Optional.of(email));

    TestAuthenticatedView view = new TestAuthenticatedView(authenticatedUserService);
    AttachEvent attachEvent = mock(AttachEvent.class);
    when(attachEvent.getUI()).thenReturn(mockUI);

    // Act: onAttachを呼び出す
    view.onAttach(attachEvent);

    // Assert: renderAuthenticatedContentが正しいメールで呼ばれる
    assertEquals(1, view.getRenderCallCount());
    assertEquals(email, view.getLastRenderedEmail());
  }

  @Test
  void onAttach_withoutAuthentication_redirectsToSignIn() {
    // Arrange: 未ログイン状態を準備
    when(authenticatedUserService.findEmail()).thenReturn(Optional.empty());

    TestAuthenticatedView view = new TestAuthenticatedView(authenticatedUserService);
    AttachEvent attachEvent = mock(AttachEvent.class);
    when(attachEvent.getUI()).thenReturn(mockUI);

    try (MockedStatic<Notification> mockedNotification = mockStatic(Notification.class)) {
      // Act: onAttachを呼び出す
      view.onAttach(attachEvent);

      // Assert: 通知が表示され、SignInViewへのナビゲーションが呼ばれる
      mockedNotification.verify(() -> Notification.show("セッションが切れています。再度サインインしてください。"));
      verify(mockUI).navigate(SignInView.class);
      assertEquals(0, view.getRenderCallCount());
    }
  }

  @Test
  void onAttach_multipleAttachEvents_rendersContentOnlyOnce() {
    // Arrange: ログインユーザーのメールアドレスが取得できる状態を準備
    String email = "test@example.com";
    when(authenticatedUserService.findEmail()).thenReturn(Optional.of(email));

    TestAuthenticatedView view = new TestAuthenticatedView(authenticatedUserService);
    AttachEvent attachEvent = mock(AttachEvent.class);
    when(attachEvent.getUI()).thenReturn(mockUI);

    // Act: onAttachを複数回呼び出す
    view.onAttach(attachEvent);
    view.onAttach(attachEvent);
    view.onAttach(attachEvent);

    // Assert: contentRenderedフラグにより、renderAuthenticatedContentは1回のみ呼ばれる
    assertEquals(1, view.getRenderCallCount());
  }

  @Test
  void onAttach_withoutAuthentication_andNullUI_doesNotThrowException() {
    // Arrange: 未ログイン状態でUIがnullの場合を準備
    when(authenticatedUserService.findEmail()).thenReturn(Optional.empty());

    TestAuthenticatedView view = new TestAuthenticatedView(authenticatedUserService);
    AttachEvent attachEvent = mock(AttachEvent.class);
    when(attachEvent.getUI()).thenReturn(null);

    try (MockedStatic<Notification> mockedNotification = mockStatic(Notification.class)) {
      // Act: onAttachを呼び出す（例外がスローされないことを確認）
      view.onAttach(attachEvent);

      // Assert: 通知は表示されるが、ナビゲーションは呼ばれない（UIがnullのため）
      mockedNotification.verify(() -> Notification.show("セッションが切れています。再度サインインしてください。"));
      assertEquals(0, view.getRenderCallCount());
    }
  }

  /**
   * テスト用の具象クラス。renderAuthenticatedContentの呼び出しを追跡する。
   */
  private static class TestAuthenticatedView extends AbstractAuthenticatedView {
    private final AtomicInteger renderCallCount = new AtomicInteger(0);
    private String lastRenderedEmail;

    TestAuthenticatedView(AuthenticatedUserService authenticatedUserService) {
      super(authenticatedUserService);
    }

    @Override
    protected void renderAuthenticatedContent(String email) {
      renderCallCount.incrementAndGet();
      lastRenderedEmail = email;
    }

    int getRenderCallCount() {
      return renderCallCount.get();
    }

    String getLastRenderedEmail() {
      return lastRenderedEmail;
    }
  }
}
