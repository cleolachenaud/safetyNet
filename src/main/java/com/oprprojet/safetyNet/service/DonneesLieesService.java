package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonView;
import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;

import lombok.Data;

@Data
@Service
public class DonneesLieesService {
	private static final Logger logger = LogManager.getLogger(DonneesLieesService.class);
	public static final String ENFANTS = "Enfants";
	public static final String FOYER = "Foyer";
	public static final String LISTEPERSONNE = "ListePersonnes";
	public static final String LISTEPHONE = "ListePhone";
	public static final String LISTEMAIL = "ListeEmails";
	
	public static final String NOMBREENFANT = "NombreEnfants";
	public static final String NOMBREADULTE = "NombreAdultes";
	public static final String STATIONNUMBER = "fireStationNumber";
	
	public static final String FIRSTNAME = "FirstName";
	public static final String LASTNAME = "LastName";
	public static final String ADDRESS = "Address";
	public static final String PHONE = "Phone";
	public static final String EMAIL = "Email";
	public static final String AGE = "Age";
	public static final String ALLERGIES = "ListeAllergies";
	public static final String MEDICAMENTS = "ListeMedicaments";
	

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
	 * Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers
	 *correspondante. Donc, si le numéro de station = 1, elle doit renvoyer les habitants
	 * couverts par la station numéro 1. La liste doit inclure les informations spécifiques
	 * suivantes : prénom, nom, adresse, numéro de téléphone. De plus, elle doit fournir un
	 * décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
	 * moins) dans la zone desservie.
 
	 * 
	 * @param stationNumber
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getFireStationStationNumber (int stationNumber) throws Exception {
		logger.debug("methode getFireStationStationNumber : lancement de la methode");
		DonneesLiees donneesEntree = reader();
						
		List<Map<String, String>> listPerson= new ArrayList<>();
		Integer nombreAdultes = 0;
		Integer nombreEnfants = 0;
		
		logger.debug("methode getFireStationStationNumber : debut du traitement");
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation fireStationKey = entry.getKey();
			if (fireStationKey.getStation() != stationNumber) { // si la stationNumber n'est pas égale à celle passée en paramètre je continue
				continue;
			}
			for(Map.Entry<Person, List<MedicalRecord>> entryBoucleDeux : donneesEntree.getMedicalRecordByPerson().entrySet()) {
				Person personKey = entryBoucleDeux.getKey();
				if(!personKey.getAddress().equals(fireStationKey.getAddress())) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
					continue;
				}
					MedicalRecord medicalRecordValue = entryBoucleDeux.getValue().get(0);
					Map<String, String> dataPerson = new HashMap<>(); // ici j'insère les champs dont j'ai besoin dans une map clé, valeur
					dataPerson.put(FIRSTNAME, personKey.getFirstName());
					dataPerson.put(LASTNAME, personKey.getLastName()); 
					dataPerson.put(ADDRESS, personKey.getAddress());
					dataPerson.put(PHONE, personKey.getPhone());
					listPerson.add(dataPerson);
				if (medicalRecordValue.isAdulte()) {// ce if sert a déterminer qui a + de 18 ans et l'affecter dans la bonne liste. 
					nombreAdultes += 1; // si + de 18 ans, l'élément va ici
				} else { 
					nombreEnfants += 1;// sinon il va dans cette liste
				}
			}	
		}
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(LISTEPERSONNE, listPerson);
		reponse.put(NOMBREADULTE, nombreAdultes);
		reponse.put(NOMBREENFANT, nombreEnfants);
		logger.debug("methode getFireStationStationNumber : fin de la methode");
		return reponse;
		
		
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
	public Map<String, Object> getChildAlertAddress(String address) throws Exception {
		logger.debug("methode getChildAlertAddress : lancement de la methode");
		DonneesLiees donneesEntree = reader();
		logger.debug("methode getChildAlertAddress : debut du traitement");
		List<Map<String, String>> listAdulte= new ArrayList<>();
		List<Map<String, String>> listEnfant= new ArrayList<>();
		// pour chaque key (person) de la Map si l'adresse est égale à l'adresse passée en paramètre, alors la Person est ajoutée dans la liste Adulte
		// si l'age est supérieur à 18ans ou enfant si il est inférieur. 
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person person = entry.getKey();
			if(!person.getAddress().equals(address)) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
				continue;
			}
			MedicalRecord medicalRecord = entry.getValue().get(0);
			if (medicalRecord.isAdulte()) {// si la personne est adulte, on l'enregistre dans une liste dédiée
				Map<String, String> dataAdulte = new HashMap<>();
				dataAdulte.put(FIRSTNAME, person.getFirstName());
				dataAdulte.put(LASTNAME, person.getLastName()); // si la personne est adulte on ne garde pas son age
				listAdulte.add(dataAdulte);
			} else { // sinon c'est un enfant, on l'enregistre dans une autre liste
				Map<String, String> dataEnfant = new HashMap<>();
				dataEnfant.put(FIRSTNAME, person.getFirstName());
				dataEnfant.put(LASTNAME, person.getLastName());
				dataEnfant.put(AGE, medicalRecord.getAge().toString()); // si la personne est un enfant, on garde son age.
				listEnfant.add(dataEnfant);
			}
		}
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(ENFANTS, listEnfant);
		reponse.put(FOYER, listAdulte);
		reponse.put(NOMBREADULTE, listAdulte.size());
		reponse.put(NOMBREENFANT, listEnfant.size());
		logger.debug("methode getChildAlertAddress : fin de la methode");
		return reponse;
	}

	/**
	 * PhoneAlertFireStation
	 * Cette url doit retourner une liste des numéros de téléphone des résidents desservis
	 * par la caserne de pompiers. Nous l'utiliserons pour envoyer des messages texte
	 * d'urgence à des foyers spécifiques.
	 * @param stationNumber
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getPhoneAlerteFireStation (int stationNumber) throws Exception {
		logger.debug("methode getPhoneAlerteFireStation : lancement de la methode");
		DonneesLiees donneesEntree = reader();
		List<Map<String, String>> listPerson= new ArrayList<>();
	
		logger.debug("methode getPhoneAlerteFireStation : debut du traitement");
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation fireStationKey = entry.getKey();
			if (fireStationKey.getStation() != stationNumber) { // si la stationNumber n'est pas égale à celle passée en paramètre je continue
				continue;
			}
			for(Map.Entry<Person, List<MedicalRecord>> entryBoucleDeux : donneesEntree.getMedicalRecordByPerson().entrySet()) {
				Person personKey = entryBoucleDeux.getKey();
				if(!personKey.getAddress().equals(fireStationKey.getAddress())) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
					continue;
				}
				Map<String, String> dataPerson = new HashMap<>(); // ici j'insère les champs dont j'ai besoin dans une map clé, valeur
				dataPerson.put(PHONE, personKey.getPhone());
				listPerson.add(dataPerson);
			}	
		}
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(LISTEPHONE, listPerson);
		logger.debug("methode getPhoneAlerteFireStation : fin de la methode");
		return reponse;
	}

	
	/**
	 * FireAddress
	 * Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le
	 * numéro de la caserne de pompiers la desservant. La liste doit inclure le nom, le
	 * numéro de téléphone, l'âge et les antécédents médicaux (médicaments, posologie et
	 * allergies) de chaque personne.
	 * @param address
	 * @return
	 * @throws Exception 
	 */
	//TODO comment faire pour medicaments et allergies qui sont des listes de string ....
	public Map<String, Object> getFireAddress(String address) throws Exception{
		logger.debug("methode getFireAddress : lancement de la methode");
		DonneesLiees donneesEntree = reader();
		logger.debug("methode getFireAddress : debut du traitement");
		return getMapPersonnesParAdresse(address, donneesEntree);
	}
	
	/**
	 * FloodStation
	 * Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette
	 * liste doit regrouper les personnes par adresse. Elle doit aussi inclure le nom, le
	 * numéro de téléphone et l'âge des habitants, et faire figurer leurs antécédents
	 * médicaux (médicaments, posologie et allergies) à côté de chaque nom.

	 *@param stationNumber
	 *@return 
	 *@throws Exception
	 */
	public Map<String, Object> getFloodStations(List<Integer> stationNumberList) throws Exception{
		logger.debug("methode getFloodStations : lancement de la methode");
		DonneesLiees donneesEntree = reader();
		int stationNumber = 0;
		logger.debug("methode getFloodStations : debut du traitement");
		Map<String, Object> reponse = new HashMap<>();
		Map<String, Object> mapPersonnesParAddresse = new HashMap<>();
		Set<String> setAddressFireStation = new HashSet();
		for (int elementDeMaListe : stationNumberList) {
			stationNumber = stationNumberList.get(stationNumberList.indexOf(elementDeMaListe));
			for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
				FireStation fireStationKey = entry.getKey();
				if (fireStationKey.getStation() !=(stationNumber)) { // si la stationNumber n'est pas égale à celle passée en paramètre je continue
					continue;
				}
				setAddressFireStation.add(fireStationKey.getAddress()); // Stocker la liste des adresses différentes
			}
		}
		List<Object> listeDePersonnesParAddresse = new ArrayList<>();
		for (String addressDeMaListe : setAddressFireStation) {
			mapPersonnesParAddresse = getMapPersonnesParAdresse(addressDeMaListe, donneesEntree);
			mapPersonnesParAddresse.remove(STATIONNUMBER);
			listeDePersonnesParAddresse.add(mapPersonnesParAddresse);
		}
		
		reponse.put(FOYER, listeDePersonnesParAddresse);
		logger.debug("methode getFloodStations : fin de la methode");
		return reponse;
	}


	
	
	/**
	 * personInfoLastName
	 * Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents
	 * médicaux (médicaments, posologie et allergies) de chaque habitant. Si plusieurs
	 * personnes portent le même nom, elles doivent toutes apparaître.
	 * @throws Exception 
	 * 
	 */
	public Map<String, Object> getPersonInfoLastName (String lastName) throws Exception {
		logger.debug("methode getPersonInfoLastName : lancement de la methode");
		DonneesLiees donneesEntree = reader();
		logger.debug("methode getPersonInfoLastName : debut du traitement");
		List<Map<String, Object>> listPerson= new ArrayList<>(); 
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person person = entry.getKey();
			if(!person.getLastName().equals(lastName)) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
				continue;
			}
			MedicalRecord medicalRecord = entry.getValue().get(0);
			Map<String, Object> dataPerson = new HashMap<>();
			dataPerson.put(LASTNAME, person.getLastName()); 
			dataPerson.put(FIRSTNAME, person.getFirstName());
			dataPerson.put(ADDRESS, person.getAddress()); 
			dataPerson.put(EMAIL, person.getEmail()); 
			dataPerson.put(AGE, medicalRecord.getAge().toString());
			dataPerson.put(MEDICAMENTS, medicalRecord.getMedications());
			dataPerson.put(ALLERGIES, medicalRecord.getAllergies());
			listPerson.add(dataPerson);
		}
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(LISTEPERSONNE, listPerson);
		logger.debug("methode getPersonInfoLastName : fin de la methode");
		return reponse;
	}
	
	
	/**
	 * CommunityEmail
	 * retourne la liste des adresses mail des personnes habitants dans la ville passée en paramètre
	 * @param city
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> getCommunityEmail(String city) throws Exception{
		logger.debug("methode getCommunityEmail : lancement de la methode");
		DonneesLiees donneesEntree = reader();				
		List<Map<String, String>> listPerson= new ArrayList<>();
	
		logger.debug("methode getCommunityEmail : debut du traitement");

		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person personKey = entry.getKey();
			if(!personKey.getCity().equals(city)) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
				continue;
			}
			Map<String, String> dataPerson = new HashMap<>(); // ici j'insère les champs dont j'ai besoin dans une map clé, valeur
			dataPerson.put(EMAIL, personKey.getEmail());
			listPerson.add(dataPerson);
		}	
		
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(LISTEMAIL, listPerson);
		logger.debug("methode getCommunityEmail : fin de la methode");
		return reponse;
	}


	/*--------------------méthodes utiles à la classe-------------------------------------------------------------*/
	/**
	 * méthode qui gère la lecture et la transformation des données brutes en données liées. 
	 * @return
	 * @throws Exception
	 */
	private DonneesLiees reader() throws Exception {
		DonneesLiees donneesEntree = new DonneesLiees();
		Donnees donneesBrutes = reader.jsonReader();
		donneesEntree.conversionDonneesBrutDonneesLiees(donneesBrutes);
		return donneesEntree;
	}

	/**
	 * Retourne une Map<String, Object> triant les personnes par addresse 
	 * et indiquant à quelle stationNumber elles sont rattachées
	 * @param address
	 * @param donneesEntree
	 * @return
	 */
	private Map<String, Object> getMapPersonnesParAdresse(String address, DonneesLiees donneesEntree) {
		List<Map<String, Object>> listPerson= new ArrayList<>(); // je fais une map String Object, car les médicaments et allergies sont des listes de String. Je ne pourrai donc pas stocker dans la même map les médicaments et l'age par exemple
		Integer stationNumber = 0;
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation fireStationKey = entry.getKey();
			if (!fireStationKey.getAddress().equals(address)) { // si la stationNumber n'est pas égale à celle passée en paramètre je continue
				continue;
			}
			stationNumber = fireStationKey.getStation();
			for(Map.Entry<Person, List<MedicalRecord>> entryBoucleDeux : donneesEntree.getMedicalRecordByPerson().entrySet()) {
				Person person = entryBoucleDeux.getKey();
				if(!person.getAddress().equals(address)) {// si l'adresse de la personne n'est pas identique à celle passée en paramètres on continue de parcourir les éléments
					continue;
				}
				MedicalRecord medicalRecord = entryBoucleDeux.getValue().get(0);
				Map<String, Object> dataPerson = new HashMap<>();
				dataPerson.put(LASTNAME, person.getLastName()); 
				dataPerson.put(FIRSTNAME, person.getFirstName()); 
				dataPerson.put(PHONE, person.getPhone()); 
				dataPerson.put(AGE, medicalRecord.getAge().toString());
				dataPerson.put(MEDICAMENTS, medicalRecord.getMedications());
				dataPerson.put(ALLERGIES, medicalRecord.getAllergies());
				listPerson.add(dataPerson);
			}
		}
		Map<String, Object> reponse = new HashMap<>();
		reponse.put(STATIONNUMBER, stationNumber);
		reponse.put(LISTEPERSONNE, listPerson);
		logger.debug("methode getFireAddress : fin de la methode");
		return reponse;
	}

}
