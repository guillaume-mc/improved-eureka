package com.mastercard.foundry.template.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

/**
 * Service class for managing pets in the store.
 * Provides CRUD operations for Pet entities.
 */
@Service
public class PetService {
  private final List<Pet> pets;
  private final AtomicLong idCounter;

  /**
   * Default constructor that initializes the service with sample data.
   */
  public PetService() {
    this.pets = new ArrayList<>();
    this.idCounter = new AtomicLong(1);
    // Initialize with sample data
    pets.add(new Pet(idCounter.getAndIncrement(), "Buddy", "Dog", 3, "available"));
    pets.add(new Pet(idCounter.getAndIncrement(), "Whiskers", "Cat", 2, "available"));
    pets.add(new Pet(idCounter.getAndIncrement(), "Tweety", "Bird", 1, "adopted"));
  }

  /**
   * Retrieves all pets from the store.
   *
   * @return A list of all pets.
   */
  public List<Pet> getAllPets() {
    return new ArrayList<>(pets);
  }

  /**
   * Retrieves a pet by its ID.
   *
   * @param id The ID of the pet to retrieve.
   * @return An Optional containing the pet if found, or empty if not found.
   */
  public Optional<Pet> getPetById(Long id) {
    return pets.stream()
        .filter(pet -> pet.getId().equals(id))
        .findFirst();
  }

  /**
   * Creates a new pet in the store.
   *
   * @param pet The pet to create.
   * @return The created pet with an assigned ID.
   */
  public Pet createPet(Pet pet) {
    pet.setId(idCounter.getAndIncrement());
    pets.add(pet);
    return pet;
  }

  /**
   * Updates an existing pet.
   *
   * @param id         The ID of the pet to update.
   * @param updatedPet The updated pet data.
   * @return An Optional containing the updated pet if found, or empty if not found.
   */
  public Optional<Pet> updatePet(Long id, Pet updatedPet) {
    for (int i = 0; i < pets.size(); i++) {
      if (pets.get(i).getId().equals(id)) {
        updatedPet.setId(id);
        pets.set(i, updatedPet);
        return Optional.of(updatedPet);
      }
    }
    return Optional.empty();
  }

  /**
   * Deletes a pet from the store.
   *
   * @param id The ID of the pet to delete.
   * @return true if the pet was deleted, false if not found.
   */
  public boolean deletePet(Long id) {
    return pets.removeIf(pet -> pet.getId().equals(id));
  }
}
