package com.mastercard.foundry.template.webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mastercard.foundry.template.service.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the PetController.
 */
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PetControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  /**
   * Tests retrieving all pets.
   */
  @Test
  public void testGetAllPets() {
    webTestClient.get()
        .uri("/api/pets")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Pet.class)
        .hasSize(3);
  }

  /**
   * Tests retrieving a pet by ID.
   */
  @Test
  public void testGetPetById() {
    webTestClient.get()
        .uri("/api/pets/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody(Pet.class)
        .value(pet -> {
          assertEquals(1L, pet.getId());
          assertEquals("Buddy", pet.getName());
        });
  }

  /**
   * Tests retrieving a non-existent pet.
   */
  @Test
  public void testGetPetByIdNotFound() {
    webTestClient.get()
        .uri("/api/pets/999")
        .exchange()
        .expectStatus().isNotFound();
  }

  /**
   * Tests creating a new pet.
   */
  @Test
  public void testCreatePet() {
    Pet newPet = new Pet();
    newPet.setName("Max");
    newPet.setSpecies("Dog");
    newPet.setAge(5);
    newPet.setStatus("available");

    webTestClient.post()
        .uri("/api/pets")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(newPet)
        .exchange()
        .expectStatus().isCreated()
        .expectBody(Pet.class)
        .value(pet -> {
          assertNotNull(pet.getId());
          assertEquals("Max", pet.getName());
          assertEquals("Dog", pet.getSpecies());
        });
  }

  /**
   * Tests updating an existing pet.
   */
  @Test
  public void testUpdatePet() {
    Pet updatedPet = new Pet();
    updatedPet.setName("Buddy Updated");
    updatedPet.setSpecies("Dog");
    updatedPet.setAge(4);
    updatedPet.setStatus("adopted");

    webTestClient.put()
        .uri("/api/pets/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updatedPet)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Pet.class)
        .value(pet -> {
          assertEquals(1L, pet.getId());
          assertEquals("Buddy Updated", pet.getName());
        });
  }

  /**
   * Tests updating a non-existent pet.
   */
  @Test
  public void testUpdatePetNotFound() {
    Pet updatedPet = new Pet();
    updatedPet.setName("Ghost");
    updatedPet.setSpecies("Dog");
    updatedPet.setAge(1);
    updatedPet.setStatus("available");

    webTestClient.put()
        .uri("/api/pets/999")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(updatedPet)
        .exchange()
        .expectStatus().isNotFound();
  }

  /**
   * Tests deleting a pet.
   */
  @Test
  public void testDeletePet() {
    webTestClient.delete()
        .uri("/api/pets/2")
        .exchange()
        .expectStatus().isNoContent();

    // Verify the pet is actually deleted
    webTestClient.get()
        .uri("/api/pets/2")
        .exchange()
        .expectStatus().isNotFound();
  }

  /**
   * Tests deleting a non-existent pet.
   */
  @Test
  public void testDeletePetNotFound() {
    webTestClient.delete()
        .uri("/api/pets/999")
        .exchange()
        .expectStatus().isNotFound();
  }
}
