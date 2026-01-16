package com.mastercard.foundry.template.service;

/**
 * Pet model class representing a pet in the store.
 */
public class Pet {
  private Long id;
  private String name;
  private String species;
  private int age;
  private String status;

  /**
   * Default constructor.
   */
  public Pet() {
  }

  /**
   * Constructor with all fields.
   *
   * @param id      The pet's unique identifier.
   * @param name    The pet's name.
   * @param species The pet's species (e.g., dog, cat, bird).
   * @param age     The pet's age in years.
   * @param status  The pet's status (e.g., available, adopted, pending).
   */
  public Pet(Long id, String name, String species, int age, String status) {
    this.id = id;
    this.name = name;
    this.species = species;
    this.age = age;
    this.status = status;
  }

  /**
   * Gets the pet's ID.
   *
   * @return The pet's ID.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the pet's ID.
   *
   * @param id The pet's ID.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the pet's name.
   *
   * @return The pet's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the pet's name.
   *
   * @param name The pet's name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the pet's species.
   *
   * @return The pet's species.
   */
  public String getSpecies() {
    return species;
  }

  /**
   * Sets the pet's species.
   *
   * @param species The pet's species.
   */
  public void setSpecies(String species) {
    this.species = species;
  }

  /**
   * Gets the pet's age.
   *
   * @return The pet's age.
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the pet's age.
   *
   * @param age The pet's age.
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Gets the pet's status.
   *
   * @return The pet's status.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the pet's status.
   *
   * @param status The pet's status.
   */
  public void setStatus(String status) {
    this.status = status;
  }
}
