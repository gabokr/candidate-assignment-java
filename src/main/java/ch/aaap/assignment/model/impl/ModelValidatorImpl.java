package ch.aaap.assignment.model.impl;

import ch.aaap.assignment.model.Canton;
import ch.aaap.assignment.model.District;
import ch.aaap.assignment.model.ModelValidator;

import java.util.Set;

public class ModelValidatorImpl implements ModelValidator {

  @Override
  public void validateCantonCode(String cantonCode, Set<Canton> cantons) {
    if (cantons.stream().noneMatch(canton -> canton.getCode().equals(cantonCode))) {
      throw new IllegalArgumentException("Provided canton code " + cantonCode + " does not exist");
    }
  }

  @Override
  public void validateDistrictNumber(String districtNumber, Set<District> districts) {
    if (districts.stream()
        .noneMatch(district -> district.getNumber().equals(districtNumber))) {
      throw new IllegalArgumentException(
          "Provided district code " + districtNumber + " does not exist");
    }
  }

}
