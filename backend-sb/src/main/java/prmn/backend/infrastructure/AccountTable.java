package prmn.backend.infrastructure;

import org.springframework.stereotype.Repository;
import prmn.backend.service.アカウント情報源;

@Repository
public class AccountTable implements アカウント情報源 {

  /**
   * **概要**
   * メールアドレスの`@`より前をユーザー名として返却するスタブ実装です。
   *
   * **Parameters**
   * - `メールアドレス`: アカウントに紐づくメールアドレス
   *
   * **Returns**
   * - `@`より前の文字列。`@`が存在しない、または`null`/空白のみの場合は空文字
   */
  @Override
  public String ユーザー名検索(String メールアドレス) {
    // Java21以降で確定したswitch式+ガード節で分岐を1か所にまとめ、認知的複雑度を抑える
    return switch (メールアドレス) {
      case null -> "";
      case String s when s.isBlank() -> "";
      default -> {
        int atIndex = メールアドレス.indexOf('@');
        yield atIndex < 0 ? "" : メールアドレス.substring(0, atIndex);
      }
    };
  }
}
