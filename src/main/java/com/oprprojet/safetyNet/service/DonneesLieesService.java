package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

import lombok.Data;
@Data
@Service
public class DonneesLieesService {
	private static final Logger logger = LogManager.getLogger(DonneesLieesService.class);
	private List<Person> listPerson ;
	private Map<Person, Integer> enfantMap;
	private Map<Person, List<MedicalRecord>> personMedicalRecordMap;
	private int nombreAdultes;
	private int nombreEnfants;
	private List <Integer> listStationNumber = new ArrayList<>();
	private Map<String, Map<Person, List<MedicalRecord>>> floodStationsMap;
	
	@Autowired
	Reader reader;
	
	/**
	 * FireStationStationNumber
	 * Retourne une liste des personnes couvertes par la fireStation passée en paramètres.
	 * La liste inclus la Person et son MedicalRecord associé.
	 * Retourne également le nombre d'enfants et d'adultes de la liste principale 
	 * 
	 * @param stationNumber
	 * @return
	 * @throws Exception 
	 */
	public DonneesLieesService getFireStationStationNumber (int stationNumber) throws Exception {
		logger.debug("methode getFireStationStationNumber : lancement de la methode");
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donnees = new DonneesLiees();
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		// Récupération de la liste des personnes
		logger.debug("methode getFireStationStationNumber : debut du traitement");
		donneesLieesReponse.setListPerson(DonneesLieesService.listePersonneCouverteParFireStation(stationNumber, donneesEntree));
		// supprimer de donneesEntree les personnes/MedicalRecord (car Hashmap) n'étant pas dans la liste retournée (donneesLieesReponse.getListePerson())		
		// Récupération du nombre d'adulte et d'enfant
		List<Person> listeAdulte = new ArrayList<>();
		List<Person> listeEnfant = new ArrayList<>();
		this.listePersonnesMajeurMineur(donneesEntree, listeAdulte, listeEnfant);
		listeEnfant.retainAll(donneesLieesReponse.getListPerson());
		listeAdulte.retainAll(donneesLieesReponse.getListPerson());
		donneesLieesReponse.setNombreAdultes(listeAdulte.size());
		donneesLieesReponse.setNombreEnfants(listeEnfant.size());
		logger.debug("methode getFireStationStationNumber : fin de la methode");
		return donneesLieesReponse;
	}
	/**
	 * ChildAlertAddress
	 * Retourne une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à l'adresse passée en paramètres. 
	 * La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres membres du foyer. 
	 * S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	public DonneesLieesService getChildAlertAddress (String address) throws Exception {
		logger.debug("methode getChildAlertAddress : lancement de la methode");
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		logger.debug("methode getChildAlertAddress : debut du traitement");
		List<Person> listPerson = new ArrayList<Person>();
		Map<Person, Integer> enfantMap = new HashMap<Person, Integer>();
		// pour chaque key (person) de la Map si l'adresse est égale à l'adresse passée en paramètre, alors la Person est ajoutée dans la liste Adulte
		// si l'age est supérieur à 18ans ou enfant si il est inférieur. 
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(!key.getAddress().equals(address)) {
				continue;
			}

			if (value.get(0).isAdulte()) {
				listPerson.add(key); // si la personne est adulte, on ne sauvegarde pas son age
			} else {
				enfantMap.put(key, value.get(0).getAge()); // si la personne est un enfant, on garde son age. 
						
			}
		}
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		donneesLieesReponse.setListPerson(listPerson);
		donneesLieesReponse.setEnfantMap(enfantMap);
		logger.debug("methode getChildAlertAddress : fin de la methode");
		return donneesLieesReponse;
	}
	
	/**
	 * PhoneAlertFireStation
	 * Retourne une liste des numéros de téléphone des résidents desservis
	 * par la fireStation passée en paramètres. 
	 * @param stationNumber
	 * @return
	 * @throws Exception 
	 */
	public DonneesLieesService getPhoneAlerteFireStation (int stationNumber) throws Exception {
		logger.debug("methode getPhoneALerteFireStation : lancement de la methode");
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		logger.debug("methode getPhoneALerteFireStation : debut du traitement");
		List<Person> listPerson = DonneesLieesService.listePersonneCouverteParFireStation(stationNumber, donneesEntree);
		donneesLieesReponse.setListPerson(listPerson);
		logger.debug("methode getPhoneALerteFireStation : fin de la methode");
		return donneesLieesReponse;
	}
	
	/**
	 * FireAddress
	 * Retourne la liste des habitants vivant à l’adresse passée en paramètre ainsi que la fireStation la desservant. 
	 * La liste comprend Person et ListMedicalRecord de chaque personne.
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	public DonneesLieesService getFireAddress(String address) throws Exception{
		logger.debug("methode getFireStation : lancement de la methode");
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		logger.debug("methode getFireStation : debut du traitement");
		Map<Person, List<MedicalRecord>> personMedicalRecordMap = new HashMap<Person, List<MedicalRecord>>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(!key.getAddress().equals(address)) {
				continue;
			}
			personMedicalRecordMap.put(key, value);				
		}
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation key = entry.getKey();
			if(!key.getAddress().equals(address)) {
				continue;
			}
			donneesLieesReponse.getListStationNumber().add(key.getStation());
			break;
		}
		
		//TODO cette méthode ne fonctionne pas car idem getChildAlert on récupère donneesEntree et non les personndes déjà filtérées
		
		donneesLieesReponse.setPersonMedicalRecordMap(personMedicalRecordMap);
		logger.debug("methode getFireStation : fin de la methode");
		return donneesLieesReponse;
		
		
	}
	
	/**
	 * FloodStation
	 * retourne une liste de tous les foyers desservis par la stationNumber. 
	 * La liste regroupe les personnes par adresse
	 * Cette liste comprend Person et MedicalRecord de chaque personne
	 *@param stationNumber
	 *@return 
	 *@throws Exception
	 */
	public DonneesLieesService getFloodStations(int stationNumber) throws Exception{
	logger.debug("methode getFloodStations : lancement de la methode");
	DonneesLiees donneesEntree = new DonneesLiees();
	Donnees donneesBrutes = reader.jsonReader();
	donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
	logger.debug("methode getFloodStations : debut du traitement");
	DonneesLieesService donneesLieesReponse = new DonneesLieesService();
	//créer une map avec un String "address", et une map de Person ListMedicalRecord pour pouvoir trier par addresse les foyers
	Map<String, Map<Person, List<MedicalRecord>>> floodStationsMap = new HashMap<>();
	// trier les personnes par rapport à la station number. 
	for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
		FireStation key = entry.getKey();
		if (key.getStation() != stationNumber) {
			continue;
		}
		floodStationsMap.put(key.getAddress(), new HashMap<>());
	}
	for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
		Person key = entry.getKey();
		List<MedicalRecord> value = entry.getValue();
		
		if(floodStationsMap.containsKey(key.getAddress())) {
			floodStationsMap.get(key.getAddress()).put(key, value);
		}
	}
	donneesLieesReponse.setFloodStationsMap(floodStationsMap);
	logger.debug("methode getFloodStation : fin de la methode");
	return donneesLieesReponse;
	}
	
	/**
	 * personInfoLastName
	 * Retourne une map de personnes (person ListMedicalRecord) ayant le même nom de famille
	 * @throws Exception 
	 * 
	 */
	public DonneesLieesService getPersonInfoLastName (String lastName) throws Exception {
		logger.debug("methode getPersonInfoLastName : lancement de la methode");
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		logger.debug("methode getPersonInfoLastName : debut du traitement");
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		Map<Person, List<MedicalRecord>> personMedicalRecordMap = new HashMap<Person, List<MedicalRecord>>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(!key.getLastName().equals(lastName)) {
				continue;
			}
			personMedicalRecordMap.put(key, value);				
		}
		donneesLieesReponse.setPersonMedicalRecordMap(personMedicalRecordMap);
		logger.debug("methode getPersonInfoLastName : fin de la methode");
		return donneesLieesReponse;
	}
	
	
	/**
	 * CommunityEmail
	 * retourne la liste des adresses mail des personnes habitants dans la ville passée en paramètre
	 * @param city
	 * @return
	 * @throws Exception 
	 */
	public DonneesLieesService getCommunityEmail(String city) throws Exception{
		logger.debug("methode getCommunityEmail : lancement de la methode");
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		logger.debug("methode getCommunityEmail : debut du traitement");
		List<Person> listPerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (!key.getCity().equals(city)) {
				continue;
			}
			listPerson.add(key);
		}
		donneesLieesReponse.setListPerson(listPerson);
		logger.debug("methode getCommunityEmail : fin de la methode");
		return donneesLieesReponse;
	}

	/*--------------------méthodes utiles à la classe-------------------------------------------------------------*/
	
	/**
	 * Renvoie la liste de toutes les personnes couvertes par une FireStation donnée en paramètres
	 * @param numeroFireStation
	 * @param donneesEntree
	 * @return
	 */
	
	public static List<Person> listePersonneCouverteParFireStation(int stationNumber, DonneesLiees donneesEntree){
		logger.debug("methode listePersonneCouverteParFireStation : lancement de la methode");
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation key = entry.getKey();
			List<Person> value = entry.getValue();
			if (key.getStation() != stationNumber) {
				continue;
			}
			listePerson.addAll(value);
			
		}
		logger.debug("methode listePersonneCouverteParFireStation : fin de la methode");
		return listePerson;
	}
	
	

	
	/**
	 * Retourne les habitants habitant à la même address passé en paramètre
	 */
	/*
	public List<Person> listeDesHabitantsMemeAddress (DonneesLiees donneesEntree, String address){
		logger.debug("methode listeDesHabitantsMemeAddress : lancement de la methode");
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (!key.getAddress().equals(address)) {
				continue;
			}
			listePerson.add(key);
		}
		logger.debug("methode listeDesHabitantsMemeAddress : fin de la methode");
		return listePerson;
	}

	*/
	/**
	 * Retourne les habitants portant le nom passé en paramètre
	 */
	/*
	public List<Person> listeDesHabitantsMemeNom (DonneesLiees donneesEntree, String lastName){
		logger.debug("methode listeDesHabitantsMemeNom : lancement de la methode");
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (key.getLastName().equals(lastName)) {
				continue;
			}
			listePerson.add(key);
		}
		logger.debug("methode listeDesHabitantsMemeNom : fin de la methode");
		return listePerson;
	}
		
	*/
	
	/**
	 * Trie les personnes de plus de 18 ans (listeAdulte) et de moins de 18 ans (listeEnfant) dans deux listes distinctes
	 * @param donneesEntree
	 * @param listeAdulte
	 * @param listeEnfant
	 */
	public void listePersonnesMajeurMineur(DonneesLiees donneesEntree, List<Person> listeAdulte, List<Person> listeEnfant){
		logger.debug("methode listePersonnesMajeurMineur : lancement de la methode");
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(value.get(0).isAdulte()) {
				listeAdulte.add(key);
			}else {
				listeEnfant.add(key);
			}
			logger.debug("methode listePersonnesMajeuMineur : fin de la methode");
		}
		
	}
}
