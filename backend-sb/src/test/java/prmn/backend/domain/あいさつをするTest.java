package prmn.backend.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import prmn.backend.domain.specification.時間帯の判定ルール;

class あいさつをするTest {

  private final あいさつ ドメインサービス = new あいさつ(new 時間帯の判定ルール());

  @Test
  void 朝は朝のメッセージを返す() {
    var result = ドメインサービス.時間帯にあわせてあいさつをする(
        "test@example.com", LocalDateTime.of(2024, 1, 1, 7, 30));
    assertEquals("おはようございます！test@example.comさん！今日もよい日でありますように！", result.value());
  }

  @Test
  void 昼は昼のメッセージを返す() {
    var result = ドメインサービス.時間帯にあわせてあいさつをする(
        "test@example.com", LocalDateTime.of(2024, 1, 1, 13, 0));
    assertEquals("こんにちは！test@example.comさん！午後も素敵な時間をお過ごしください！", result.value());
  }

  @Test
  void 夜は夜のメッセージを返す() {
    var result = ドメインサービス.時間帯にあわせてあいさつをする(
        "test@example.com", LocalDateTime.of(2024, 1, 1, 21, 15));
    assertEquals("こんばんは！test@example.comさん！一日お疲れさまでした！", result.value());
  }

  @Test
  void 作成日時は引数の日時を利用する() {
    var now = LocalDateTime.of(2024, 1, 1, 7, 30);
    var result = ドメインサービス.時間帯にあわせてあいさつをする("test@example.com", now);
    assertEquals(now, result.createAt());
  }
}
