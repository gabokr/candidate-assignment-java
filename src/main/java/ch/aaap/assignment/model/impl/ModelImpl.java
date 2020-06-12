package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.*;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ModelImpl implements Model {

  private final Set<PoliticalCommunity> politicalCommunities;
  private final Set<PostalCommunity> postalCommunities;
  private final Set<Canton> cantons;
  private final Set<District> districts;

  public ModelImpl(Collection<PoliticalCommunity> politicalCommunities,
      Collection<PostalCommunity> postalCommunities, Collection<Canton> cantons,
      Collection<District> districts) {
    this.politicalCommunities = new HashSet<>();
    this.politicalCommunities.addAll(politicalCommunities);

    this.postalCommunities = new HashSet<>();
    this.postalCommunities.addAll(postalCommunities);

    this.cantons = new HashSet<>();
    this.cantons.addAll(cantons);

    this.districts = new HashSet<>();
    this.districts.addAll(districts);
  }

}
