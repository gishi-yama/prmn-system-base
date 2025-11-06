package prmn.backend.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import prmn.backend.domain.specification.時間帯の判定ルール;

class 時間帯の判定ルールTest {

  private final 時間帯の判定ルール ルール = new 時間帯の判定ルール();

  @Test
  void 朝の時間帯を返す() {
    var result = ルール.時間帯を判定する(LocalDateTime.of(2024, 1, 1, 7, 30));
    assertEquals(時間帯.朝, result);
  }

  @Test
  void 昼の時間帯を返す() {
    var result = ルール.時間帯を判定する(LocalDateTime.of(2024, 1, 1, 13, 0));
    assertEquals(時間帯.昼, result);
  }

  @Test
  void 夜の時間帯を返す() {
    var result = ルール.時間帯を判定する(LocalDateTime.of(2024, 1, 1, 21, 15));
    assertEquals(時間帯.夜, result);
  }

  @Test
  void 深夜は夜の時間帯を返す() {
    var result = ルール.時間帯を判定する(LocalDateTime.of(2024, 1, 2, 2, 45));
    assertEquals(時間帯.夜, result);
  }
}
