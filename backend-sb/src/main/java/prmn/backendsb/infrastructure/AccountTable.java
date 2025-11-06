package prmn.backendsb.infrastructure;

import org.springframework.stereotype.Repository;
import prmn.backendsb.service.アカウント情報源;

@Repository
public class AccountTable implements アカウント情報源 {

  public String ユーザー名検索(String メールアドレス) {
    // データベースからSelectする体でのスタブです。
    return "Duke";
  }
}
