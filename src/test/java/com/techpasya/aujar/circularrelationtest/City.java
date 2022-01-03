package com.techpasya.aujar.circularrelationtest;

import java.util.Arrays;
import java.util.List;

public class City {

  public static List<City> mostPolluttedCities = Arrays.asList(new City("Kanpur"),
      new City("Bamenda"), new City("Peshawar"), new City("Kampala"));
  private final String name;

  public City(final String name) {
    this.name = name;
  }
}
