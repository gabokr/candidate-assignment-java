package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class CantonImpl implements Canton {

  private final String code;
  private final String name;

}
