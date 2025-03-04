package com.oprprojet.safetyNet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class PersonService {
	/**
	 * Permet d'ajouter une person (stationNumber, address)
	 */
    public void addPerson(Person person) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Ajouter la Person transmise dedans en vérifiant qu'elle n'existe pas déjà.
    	List<Person> personList = donneesBrute.getPersonList();
    	Boolean personDejaExistante = false;
    	for(Person personElement : personList) {
    		if(personElement == person) {
    			personDejaExistante = true;
    			break;
				//TODO mettre un logger ici
    		}	
    	}
		if(!personDejaExistante) {
			personList.add(person);
			// Ecrire les données.
	    	Writer.jsonWriter(donneesBrute);
		}  	
    }
    /**
     * Permet de supprimer une person avec firstName et lastName
     */
    public void deletePerson(Person person) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// Supprimer la person transmise.
    	List<Person> personList = donneesBrute.getPersonList();
    	for(Person personElement : personList) {
    		if(personElement.getFirstName() == person.getFirstName() && personElement.getLastName() == person.getLastName()) {
    			personList.remove(personElement);
    		}
    	}
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
    
    
    /**
     * Permet de modifier les informations d'une personne (sauf firstName et lastName)
     */
    public void updatePerson (Person person) throws Exception {
    	// Lire les données
    	Donnees donneesBrute = Reader.jsonReader();
    	// modifie la person transmise.
    	List<Person> personList = donneesBrute.getPersonList();
    	for(Person personElement : personList) {
    		if(personElement.getFirstName() != person.getFirstName() && personElement.getLastName() != person.getLastName()){
    			continue;
    		}
    		personElement = person;
    	} 
    	
    	// Ecrire les données.
    	Writer.jsonWriter(donneesBrute);
    }
}
