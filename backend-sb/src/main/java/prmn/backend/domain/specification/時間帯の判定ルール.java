package prmn.backend.domain.specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;
import prmn.backend.domain.時間帯;

public record 時間帯の判定ルール() {

  private static final LocalTime 朝開始 = LocalTime.of(5, 0);
  private static final LocalTime 昼開始 = LocalTime.NOON;
  private static final LocalTime 晩開始 = LocalTime.of(18, 0);

  private static final Map<時間帯, Predicate<LocalTime>> 判定ルール = new EnumMap<>(時間帯.class);

  static {
    判定ルール.put(時間帯.朝,
        time -> !time.isBefore(朝開始) && time.isBefore(昼開始));
    判定ルール.put(時間帯.昼,
        time -> !time.isBefore(昼開始) && time.isBefore(晩開始));
    判定ルール.put(時間帯.夜,
        time -> time.isBefore(朝開始) || !time.isBefore(晩開始));
  }

  public 時間帯 時間帯を判定する(LocalDateTime 日時) {
    LocalTime 時刻 = 日時.toLocalTime();
    return 判定ルール.entrySet()
        .stream()
        .filter(entry -> entry.getValue().test(時刻))
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("時間帯を判定できません"));
  }
}
