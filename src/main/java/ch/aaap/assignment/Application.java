package ch.aaap.assignment;

import ch.aaap.assignment.model.Model;
import ch.aaap.assignment.model.impl.ModelInitializer;
import ch.aaap.assignment.model.ModelValidator;
import ch.aaap.assignment.model.PoliticalCommunity;
import ch.aaap.assignment.model.impl.ModelValidatorImpl;
import ch.aaap.assignment.raw.CSVPoliticalCommunity;
import ch.aaap.assignment.raw.CSVPostalCommunity;
import ch.aaap.assignment.raw.CSVUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Application {

  private Model model = null;

  private final ModelValidator modelValidator = new ModelValidatorImpl();

  public Application() {
    initModel();
  }

  public static void main(String[] args) {
    new Application();
  }

  /**
   * Reads the CSVs and initializes a in memory model
   */
  private void initModel() {
    Set<CSVPoliticalCommunity> politicalCommunities = CSVUtil.getPoliticalCommunities();
    Set<CSVPostalCommunity> postalCommunities = CSVUtil.getPostalCommunities();
    model = ModelInitializer.initModel(politicalCommunities, postalCommunities);
  }

  /**
   * @return model
   */
  public Model getModel() {
    return model;
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of political communities in given canton
   */
  public long getAmountOfPoliticalCommunitiesInCanton(String cantonCode) {
    modelValidator.validateCantonCode(cantonCode, model.getCantons());
    return model.getPoliticalCommunities().stream()
        .filter(politicalCommunity -> politicalCommunity.getDistrict().getCanton().getCode()
            .equals(cantonCode))
        .count();
  }

  /**
   * @param cantonCode of a canton (e.g. ZH)
   * @return amount of districts in given canton
   */
  public long getAmountOfDistrictsInCanton(String cantonCode) {
    modelValidator.validateCantonCode(cantonCode, model.getCantons());
    return model.getDistricts().stream()
        .filter(district -> district.getCanton().getCode().equals(cantonCode))
        .count();
  }

  /**
   * @param districtNumber of a district (e.g. 101)
   * @return amount of districts in given canton
   */
  public long getAmountOfPoliticalCommunitiesInDistict(String districtNumber) {
    modelValidator.validateDistrictNumber(districtNumber, model.getDistricts());
    return model.getPoliticalCommunities().stream()
        .filter(politicalCommunity -> politicalCommunity.getDistrict().getNumber()
            .equals(districtNumber))
        .count();
  }

  /**
   * @param zipCode 4 digit zip code
   * @return district that belongs to specified zip code
   */
  public Set<String> getDistrictsForZipCode(String zipCode) {
    return model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getZipCode().equals(zipCode))
        .flatMap(postalCommunity -> postalCommunity.getPoliticalCommunities().stream()
            .map(politicalCommunity -> politicalCommunity.getDistrict().getName()))
        .collect(Collectors.toSet());
  }

  /**
   * @param postalCommunityName name
   * @return lastUpdate of the political community by a given postal community name or
   * NoSuchElementException("No value present") if no last update is available.
   */
  public LocalDate getLastUpdateOfPoliticalCommunityByPostalCommunityName(
      String postalCommunityName) {
    return model.getPostalCommunities().stream()
        .filter(postalCommunity -> postalCommunity.getName().equals(postalCommunityName))
        .flatMap(postalCommunity -> postalCommunity.getPoliticalCommunities().stream()
            .map(PoliticalCommunity::getLastUpdate))
        .findFirst()
        .orElseThrow();
  }

  /**
   * https://de.wikipedia.org/wiki/Kanton_(Schweiz)
   *
   * @return amount of canton
   */
  public long getAmountOfCantons() {
    return model.getCantons().size();
  }

  /**
   * https://de.wikipedia.org/wiki/Kommunanz
   *
   * @return amount of political communities without postal communities
   */
  public long getAmountOfPoliticalCommunityWithoutPostalCommunities() {
    Set<PoliticalCommunity> politicalCommunitiesHavingPostalCommunity = new HashSet<>();
    model.getPoliticalCommunities().forEach(politicalCommunity -> {
      boolean politicalHasPostal = model.getPostalCommunities().stream()
          .flatMap(postalCommunity -> postalCommunity.getPoliticalCommunities().stream())
          .anyMatch(postalPoliticalCommunity -> postalPoliticalCommunity.getNumber()
              .equals(politicalCommunity.getNumber()));
      if (politicalHasPostal) {
        politicalCommunitiesHavingPostalCommunity.add(politicalCommunity);
      }
    });
    return model.getPoliticalCommunities().size() - politicalCommunitiesHavingPostalCommunity
        .size();
  }
}
