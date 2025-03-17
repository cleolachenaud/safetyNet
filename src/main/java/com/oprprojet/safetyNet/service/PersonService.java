package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class PersonService {
	@Autowired
	private Reader reader;
	
	@Autowired
	private Writer writer;
	
	private static final Logger logger = LogManager.getLogger(PersonService.class);
	/**
	 * Permet d'ajouter une person (stationNumber, address)
	 */
	//TODO faire en sorte de faire un retour sur person/fireStation/medicalRecord pour les méthodes add et update. Pas de void sur les méthodes. IL doit renvoyer la réponse. 
    //TODO utiliser des .stream
	//TODO Boolean personDejaExistante = personList.stream().anyMatch(personElement -> personElement.equals(person)); // équivaut à une boucle for de la ligne 40 a 47
	public void addPerson(Person person) throws Exception {
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
	    	logger.debug("methode addPerson : fin de la methode");
		}  	
    }
    /**
     * Permet de supprimer une person avec firstName et lastName
     */
    public void deletePerson(Person person) throws Exception {
    	logger.debug("methode deletePerson : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode deletePerson : debut du traitement");
    	// Supprimer la person transmise.
    	List<Person> toRemovePerson = new ArrayList<Person>();
    	List<Person> personList = donneesBrute.getPersons();
    	for(Person personElement : personList) {
    		if(personElement.getFirstName().equals(person.getFirstName()) && personElement.getLastName().equals(person.getLastName())) {
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
    public void updatePerson (Person person) throws Exception {
    	logger.debug("methode updatePerson : lancement de la methode");
    	// Lire les données
    	Donnees donneesBrute = reader.jsonReader();
    	logger.debug("methode updatePerson : debut du traitement");
    	// modifie la person transmise.
    	List<Person> personList = donneesBrute.getPersons();
    	for(Person personElement : personList) {
    		if(personElement.getFirstName() != person.getFirstName() && personElement.getLastName() != person.getLastName()){
    			continue;
    		} 
    		if(!person.getAddress().isEmpty()) {
    			personElement.setAddress(person.getAddress());
    		}
    		if(!person.getCity().isEmpty()) {
    			personElement.setCity(person.getCity());
    		}
    		if(!person.getEmail().isEmpty()) {
    			personElement.setEmail(person.getEmail());
    		}
    		if(!person.getPhone().isEmpty()) {
    			personElement.setPhone(person.getPhone());
    		}
    		if(person.getZip() > 0 ) {
    		personElement.setZip(person.getZip());
    		}
    		logger.debug("la person a bien été modifiée");
    	} 
    	
    	// Ecrire les données.
    	writer.jsonWriter(donneesBrute);
    	logger.debug("methode updatePerson : fin de la methode");
    }
}
