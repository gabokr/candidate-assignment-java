package ch.aaap.assignment.model;

import java.util.Set;

public interface ModelValidator {

  void validateCantonCode(String cantonCode, Set<Canton> cantons);

  void validateDistrictNumber(String districtNumber, Set<District> districts);

}
