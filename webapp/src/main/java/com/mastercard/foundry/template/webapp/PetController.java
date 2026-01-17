package com.mastercard.foundry.template.webapp;

import com.mastercard.foundry.template.service.Pet;
import com.mastercard.foundry.template.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for managing pets in the PetStore.
 * Provides endpoints for CRUD operations on pets.
 */
@RestController
@RequestMapping("/api/pets")
public class PetController {

  private final PetService petService;

  /**
   * Constructor that initializes the controller with a PetService.
   *
   * @param petService The pet service to use.
   */
  public PetController(PetService petService) {
    this.petService = petService;
  }

  /**
   * Retrieves all pets.
   *
   * @return A Flux of all pets in the store.
   */
  @GetMapping
  public Flux<Pet> getAllPets() {
    return Flux.fromIterable(petService.getAllPets());
  }

  /**
   * Retrieves a pet by its ID.
   *
   * @param id The ID of the pet to retrieve.
   * @return A Mono containing the pet and HTTP 200 if found, or HTTP 404 if not found.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Pet>> getPetById(@PathVariable Long id) {
    return Mono.justOrEmpty(petService.getPetById(id))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Creates a new pet.
   *
   * @param pet The pet to create.
   * @return A Mono containing the created pet with HTTP 201 status.
   */
  @PostMapping
  public Mono<ResponseEntity<Pet>> createPet(@RequestBody Pet pet) {
    Pet createdPet = petService.createPet(pet);
    return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(createdPet));
  }

  /**
   * Updates an existing pet.
   *
   * @param id  The ID of the pet to update.
   * @param pet The updated pet data.
   * @return A Mono containing the updated pet and HTTP 200 if found,
   *         or HTTP 404 if not found.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Pet>> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
    return Mono.justOrEmpty(petService.updatePet(id, pet))
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Deletes a pet by its ID.
   *
   * @param id The ID of the pet to delete.
   * @return A Mono with HTTP 204 if deleted, or HTTP 404 if not found.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deletePet(@PathVariable Long id) {
    boolean deleted = petService.deletePet(id);
    return Mono.just(deleted
        ? ResponseEntity.noContent().<Void>build()
        : ResponseEntity.notFound().<Void>build());
  }
}
