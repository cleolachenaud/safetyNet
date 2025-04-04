package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class PersonService {
	@Autowired
	Person person;
	@Autowired
	private Reader reader;
	
	@Autowired
	private Writer writer;
	
	private static final Logger logger = LogManager.getLogger(PersonService.class);
	/**
	 * Permet d'ajouter une person (stationNumber, address)
	 */
	
	public Person addPerson(Person person) throws Exception {
    	logger.debug("methode addPerson : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode addPerson : debut du traitement");
    	// Ajouter la Person transmise dedans en vérifiant qu'elle n'existe pas déjà.
    	List<Person> personList = donneesBrute.getPersons();
    	Boolean personDejaExistante = false;
    	for(Person personElement : personList) {
    		if(personElement.equals(person)) {
    			personDejaExistante = true;
    			logger.warn("methode addPerson : la Person que vous essayez d'ajouter existe déjà, elle ne sera pas ajoutée");
    			break;
    		}	
    	}
		if(!personDejaExistante) {
			personList.add(person);
			logger.debug("methode addPerson : la Person a bien été ajoutée");
			// Ecrire les données.
	    	writer.jsonWriter(donneesBrute);
	    	
		}  
		logger.debug("methode addPerson : fin de la methode");
		return person;
    }
    /**
     * Permet de supprimer une person avec firstName et lastName
     */
    public void deletePerson(String firstName, String lastName) throws Exception {
    	logger.debug("methode deletePerson : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode deletePerson : debut du traitement");
    	// Supprimer la person transmise.
    	List<Person> toRemovePerson = new ArrayList<Person>();
    	List<Person> personList = donneesBrute.getPersons();
    	for(Person personElement : personList) {
    		if(personElement.getFirstName().equals(firstName) && personElement.getLastName().equals(lastName)) {
    			toRemovePerson.add(personElement);
    		}
    	}
    	if(!toRemovePerson.isEmpty()) { //si la liste à supprimer n'est PAS nulle alors je fais un traitement
    		personList.removeAll(toRemovePerson);
    		logger.debug("methode deletePerson : la person a bien été supprimée");
    		// Ecrire les données.
	    	writer.jsonWriter(donneesBrute);
    	}
    	logger.debug("methode deletePerson : fin de la methode");
    }
    
    
    /**
     * Permet de modifier les informations d'une personne (sauf firstName et lastName)
     */
    public Person updatePerson (Person person) throws Exception {
    	logger.debug("methode updatePerson : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode updatePerson : debut du traitement");
    	// modifie la person transmise.
    	List<Person> personList = donneesBrute.getPersons();
    	Person personMisAJour = new Person();
    	for(Person personElement : personList) {
    		if(!personElement.getFirstName().equals(person.getFirstName()) && !personElement.getLastName().equals(person.getLastName())){
    			continue;
    		} 
    		if(person.getAddress() !=null) {
    			personElement.setAddress(person.getAddress());
    			personMisAJour.setAddress(personElement.getAddress());
    		}
    		if(person.getCity() !=null) {
    			personElement.setCity(person.getCity());
    			personMisAJour.setCity(personElement.getCity());
    		}
    		if(person.getEmail() !=null) {
    			personElement.setEmail(person.getEmail());
    		}
    		if(person.getPhone() !=null) {
    			personElement.setPhone(person.getPhone());
    			personMisAJour.setPhone(personElement.getPhone());
    			personMisAJour.setEmail(personElement.getEmail());
    		}
    		if(person.getZip() > 0 ) {
    		personElement.setZip(person.getZip());
    		personMisAJour.setZip(personElement.getZip());
    		}

    		personMisAJour.setFirstName(personElement.getFirstName());
    		personMisAJour.setLastName(personElement.getLastName());
    		logger.debug("la person a bien été modifiée");
    	} 
    	
    	// Ecrire les données.
    	writer.jsonWriter(donneesBrute);
    	logger.debug("methode updatePerson : fin de la methode");
    	return personMisAJour;
    	
    }
}
