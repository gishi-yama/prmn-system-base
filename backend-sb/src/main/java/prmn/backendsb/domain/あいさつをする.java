package prmn.backendsb.domain;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import prmn.backendsb.domain.specification.時間帯の判定ルール;

@Component
public class あいさつをする {

  private final 時間帯の判定ルール 判定ルール;

  public あいさつをする() {
    this(new 時間帯の判定ルール());
  }

  public あいさつをする(時間帯の判定ルール 判定ルール) {
    this.判定ルール = 判定ルール;
  }

  // 指定されたユーザー名と生成時刻に応じて挨拶メッセージを生成する
  public あいさつ内容 時間帯にあわせてあいさつの内容を作る(String userName, LocalDateTime 生成日時) {
    時間帯 時間帯 = 判定ルール.時間帯を判定する(生成日時);

    String メッセージ = switch (時間帯) {
      case 朝 -> "おはようございます！%sさん！今日もよい日でありますように！".formatted(userName);
      case 昼 -> "こんにちは！%sさん！午後も素敵な時間をお過ごしください！".formatted(userName);
      case 夜 -> "こんばんは！%sさん！一日お疲れさまでした！".formatted(userName);
    };
    return new あいさつ内容(メッセージ, 生成日時);
  }
}
