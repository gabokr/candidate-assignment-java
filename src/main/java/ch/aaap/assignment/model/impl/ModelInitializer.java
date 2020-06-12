package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.*;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ModelInitializer {

  private static Map<String, Canton> initCantons(
      Set<CSVPoliticalCommunity> csvPoliticalCommunities) {
    return csvPoliticalCommunities.stream()
        .map(csvPoliticalCommunity -> new CantonImpl(csvPoliticalCommunity.getCantonCode(),
            csvPoliticalCommunity.getCantonName()))
        .distinct()
        .collect(Collectors.toMap(CantonImpl::getCode, Function.identity()));
  }

  private static Map<String, District> initDistricts(
      Set<CSVPoliticalCommunity> csvPoliticalCommunities, Map<String, Canton> cantonMap) {
    return csvPoliticalCommunities.stream()
        .map(csvPoliticalCommunity -> new DistrictImpl(
            csvPoliticalCommunity.getDistrictNumber(),
            csvPoliticalCommunity.getDistrictName(),
            cantonMap.get(csvPoliticalCommunity.getCantonCode())))
        .distinct()
        .collect(Collectors.toMap(DistrictImpl::getNumber, Function.identity()));
  }

  private static Map<String, PoliticalCommunity> initPoliticalCommunities(
      Set<CSVPoliticalCommunity> csvPoliticalCommunities, Map<String, District> districtMap) {
    return csvPoliticalCommunities.stream()
        .map(politicalCommunity -> new PoliticalCommunityImpl(
            politicalCommunity.getNumber(),
            politicalCommunity.getName(),
            politicalCommunity.getShortName(),
            politicalCommunity.getLastUpdate(),
            districtMap.get(politicalCommunity.getDistrictNumber())))
        .distinct()
        .collect(Collectors.toMap(PoliticalCommunity::getNumber, Function.identity()));
  }

  private static Set<PostalCommunity> initPostalCommunities(
      Set<CSVPostalCommunity> csvPostalCommunities,
      Map<String, PoliticalCommunity> politicalCommunities) {

    Set<PostalCommunity> postalCommunities = new HashSet<>();
    Map<String, List<CSVPostalCommunity>> postalRecordsGroupedByUniqueKey =
        csvPostalCommunities.stream()
            .collect(Collectors.groupingBy(
                csvPostalCommunity -> csvPostalCommunity.getZipCode() + csvPostalCommunity
                    .getZipCodeAddition() + csvPostalCommunity.getName()));

    postalRecordsGroupedByUniqueKey.values().forEach(csvPostalCommunities1 -> {
      postalCommunities.add(new PostalCommunityImpl(
          csvPostalCommunities1.get(0).getZipCode(),
          csvPostalCommunities1.get(0).getZipCodeAddition(),
          csvPostalCommunities1.get(0).getName(),
          csvPostalCommunities1.stream().map(postalCommunity -> politicalCommunities
              .get(postalCommunity.getPoliticalCommunityNumber())).collect(
              Collectors.toSet())
      ));
    });
    return postalCommunities;
  }

  public static Model initModel(Set<CSVPoliticalCommunity> csvPoliticalCommunities,
      Set<CSVPostalCommunity> csvPostalCommunities) {
    Map<String, Canton> cantonsMap = initCantons(csvPoliticalCommunities);
    Map<String, District> districtMap = initDistricts(csvPoliticalCommunities, cantonsMap);
    Map<String, PoliticalCommunity> politicalCommunitiesMap = initPoliticalCommunities(
        csvPoliticalCommunities, districtMap);
    Set<PostalCommunity> postalCommunitiesMap = initPostalCommunities(csvPostalCommunities,
        politicalCommunitiesMap);

    return new ModelImpl(politicalCommunitiesMap.values(), postalCommunitiesMap,
        cantonsMap.values(), districtMap.values());
  }

}
