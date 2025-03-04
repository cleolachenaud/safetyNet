package com.oprprojet.safetyNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@Autowired
	PersonService personService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter une Person
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		try {
			personService.addPerson(person);
		} catch (Exception e) {
	        return new ResponseEntity<>(person, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer une Person via son firstName + lastName
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> deletePerson(@RequestBody Person person) {
		try {
			personService.deletePerson(person);
		} catch (Exception e) {
	        return new ResponseEntity<>(person, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(person, HttpStatus.ACCEPTED);
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour une Person. firstName et lastName sont les points d'entrée et ne peuvent être modifiés
	 * Seuls les champs address, city, zip, phone, email le peuvent
	 * @param person
	 * @return
	 */
    public ResponseEntity<Person> majPerson(@RequestBody Person person) {
		try {
			personService.updatePerson(person);
		} catch (Exception e) {
	        return new ResponseEntity<>(person, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(person, HttpStatus.ACCEPTED);
    }

}
