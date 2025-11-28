package prmn.front.vaadin.application.domain;

import java.time.LocalDateTime;

public record Greeting(String value, LocalDateTime createAt) {

  public String getGreetingMessage() {
    return value + "\t(" + createAt.toString() + ")";
  }

}
