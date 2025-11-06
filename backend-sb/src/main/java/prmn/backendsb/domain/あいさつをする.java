package prmn.backendsb.domain;

import java.time.LocalDateTime;
import prmn.backendsb.domain.specification.時間帯の判定ルール;

public class あいさつをする {

  private final 時間帯の判定ルール 判定ルール;

  // 指定された時間帯判定ルールを保持して挨拶サービスを初期化する
  public あいさつをする(時間帯の判定ルール 判定ルール) {
    this.判定ルール = 判定ルール;
  }

  // 指定されたユーザー名と生成時刻に応じて挨拶メッセージを生成する
  public あいさつ内容 時間帯にあわせてあいさつの内容を作る(String ユーザー名, LocalDateTime 実行日時) {
    時間帯 時間帯 = 判定ルール.時間帯を判定する(実行日時);

    String メッセージ = switch (時間帯) {
      case 朝 -> "おはようございます！%sさん！今日もよい日でありますように！".formatted(ユーザー名);
      case 昼 -> "こんにちは！%sさん！午後も素敵な時間をお過ごしください！".formatted(ユーザー名);
      case 夜 -> "こんばんは！%sさん！一日お疲れさまでした！".formatted(ユーザー名);
    };
    return new あいさつ内容(メッセージ, 実行日時);
  }
}
