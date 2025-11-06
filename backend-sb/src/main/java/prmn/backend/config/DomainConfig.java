package prmn.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prmn.backend.domain.あいさつ;
import prmn.backend.domain.specification.時間帯の判定ルール;

@Configuration
public class DomainConfig {

  // ドメインサービスをアプリケーション層で組み立ててBean登録する
  @Bean
  public あいさつ あいさつをする() {
    return new あいさつ(new 時間帯の判定ルール());
  }
}
