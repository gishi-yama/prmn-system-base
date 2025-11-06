package prmn.backendsb.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import prmn.backendsb.domain.あいさつ内容;
import prmn.backendsb.service.あいさつUseCase;

@RestController
public class GreetingAPI {

  private あいさつUseCase usecase;

  public GreetingAPI(あいさつUseCase usecase) {
    this.usecase = usecase;
  }

  @PostMapping("/greet")
  public あいさつ内容 post(String mailaddress) {
    あいさつ内容 returning = usecase.実行する(mailaddress);
    return returning;
  }


}
