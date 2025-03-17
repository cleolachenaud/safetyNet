package com.oprprojet.safetyNet.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {
	 private static final Logger logger = LogManager.getLogger(Person.class);
	@Autowired
	PersonService personService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter une Person
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> createPerson(@PathVariable String firstName, String lastName,  @RequestBody Person person) {
		try {
			personService.addPerson(person);
		} catch (Exception e) {
			logger.error("createPerson : la person n'a pas été créee ");
			return ResponseEntity.notFound().build();
		}
		logger.info("createPerson : réponse OK, la person est créee");
		return ResponseEntity.ok(person);
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer une Person via son firstName + lastName
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> deletePerson(@PathVariable String firstName, String lastName, @RequestBody Person person) {
		try {
			personService.deletePerson(person);
		} catch (Exception e) {
			logger.error("deletePerson : la person n'a pas été supprimée ");
			return ResponseEntity.notFound().build(); 
		}
		logger.info("deletePerson : réponse OK, la person est supprimée");
		return ResponseEntity.ok(person);
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour une Person. firstName et lastName sont les points d'entrée et ne peuvent être modifiés
	 * Seuls les champs address, city, zip, phone, email le peuvent
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> majPerson(@PathVariable String firstName, String lastName, @RequestBody Person person) {
		try {
			personService.updatePerson(person);
		} catch (Exception e) {
			logger.error("updatePerson : la person n'a pas été mise à jour ");
			return ResponseEntity.notFound().build();
		}
		logger.info("updatePerson : réponse OK, la person est mise à jour");
		return ResponseEntity.ok(person);
    }

}
