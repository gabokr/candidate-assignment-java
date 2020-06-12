package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.PoliticalCommunity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PoliticalCommunityImpl implements PoliticalCommunity {

  private final String number;
  private final String name;
  private final String shortName;
  private final LocalDate lastUpdate;
  private final District district;

}
