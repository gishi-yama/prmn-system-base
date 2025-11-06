package prmn.front.vaadin.model;

import java.time.LocalDateTime;

public record Greeting(String value, LocalDateTime createAt) {

  public String getGreetingMessage() {
    return value + "\t(" + createAt.toString() + ")";
  }

}
