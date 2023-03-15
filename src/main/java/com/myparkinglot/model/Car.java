package com.myparkinglot.model;

/**
 * Model object to represent a car.
 */
public class Car {
  private String registrationNumber;

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public Car(final String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }
}
