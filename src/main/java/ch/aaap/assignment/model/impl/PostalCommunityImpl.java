package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.PostalCommunity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class PostalCommunityImpl implements PostalCommunity {

  private final String zipCode;
  private final String zipCodeAddition;
  private final String name;
  private final Set<PoliticalCommunity> politicalCommunities;

}
