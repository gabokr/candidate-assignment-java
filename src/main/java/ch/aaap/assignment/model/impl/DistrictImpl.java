package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class DistrictImpl implements District {

  private final String number;
  private final String name;
  private final Canton canton;

}
