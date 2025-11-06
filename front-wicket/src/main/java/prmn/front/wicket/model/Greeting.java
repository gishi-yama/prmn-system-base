package prmn.front.wicket.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public record Greeting(String value, LocalDateTime createAt) implements Serializable {

  public String getGreetingMessage() {
    return value + "\t(" + createAt.toString() + ")";
  }

}
