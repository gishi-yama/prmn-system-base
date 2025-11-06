package prmn.backendsb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prmn.backendsb.domain.あいさつ;
import prmn.backendsb.domain.specification.時間帯の判定ルール;

@Configuration
public class DomainConfig {

  // ドメインサービスをアプリケーション層で組み立ててBean登録する
  @Bean
  public あいさつ あいさつをする() {
    return new あいさつ(new 時間帯の判定ルール());
  }
}
