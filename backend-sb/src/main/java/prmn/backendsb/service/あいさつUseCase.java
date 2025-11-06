package prmn.backendsb.service;

import org.springframework.stereotype.Service;
import prmn.backendsb.domain.あいさつをする;
import prmn.backendsb.domain.あいさつ内容;
import prmn.backendsb.infrastructure.AccountRepository;

@Service
public class あいさつUseCase {

  private AccountRepository accountRepository;

  public あいさつUseCase(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public あいさつ内容 実行する(String メールアドレス) {
    String ユーザー名 = accountRepository.ユーザー名検索(メールアドレス);
    return new あいさつをする().時間帯にあわせてあいさつの内容を作る(ユーザー名);

  }

}
