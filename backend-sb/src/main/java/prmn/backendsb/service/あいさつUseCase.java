package prmn.backendsb.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import prmn.backendsb.domain.あいさつ;
import prmn.backendsb.domain.あいさつ内容;

@Service
public class あいさつUseCase {

  private final アカウント情報源 アカウント情報源;
  private final あいさつ あいさつ;

  public あいさつUseCase(アカウント情報源 アカウント情報源, あいさつ あいさつ) {
    this.アカウント情報源 = アカウント情報源;
    this.あいさつ = あいさつ;
  }

  // メールアドレスからユーザー名を特定し、指定時刻のあいさつ文を組み立てる
  public あいさつ内容 実行する(String メールアドレス) {
    String ユーザー名 = アカウント情報源.ユーザー名検索(メールアドレス);
    LocalDateTime 現在日時 = LocalDateTime.now();
    return あいさつ.時間帯にあわせてあいさつをする(ユーザー名, 現在日時);

  }

}
