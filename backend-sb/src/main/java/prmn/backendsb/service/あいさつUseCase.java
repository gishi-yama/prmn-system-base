package prmn.backendsb.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import prmn.backendsb.domain.あいさつをする;
import prmn.backendsb.domain.あいさつ内容;

@Service
public class あいさつUseCase {

  private final アカウント情報源 アカウント情報源;
  private final あいさつをする あいさつをする;

  public あいさつUseCase(アカウント情報源 アカウント情報源, あいさつをする あいさつをする) {
    this.アカウント情報源 = アカウント情報源;
    this.あいさつをする = あいさつをする;
  }

  // メールアドレスからユーザー名を特定し、指定時刻のあいさつ文を組み立てる
  public あいさつ内容 実行する(String メールアドレス) {
    String ユーザー名 = アカウント情報源.ユーザー名検索(メールアドレス);
    LocalDateTime 現在日時 = LocalDateTime.now();
    return あいさつをする.時間帯にあわせてあいさつの内容を作る(ユーザー名, 現在日時);

  }

}
