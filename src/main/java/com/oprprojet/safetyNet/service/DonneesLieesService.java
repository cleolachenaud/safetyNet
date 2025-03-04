package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.controller.ChildAlertReponse;
import com.oprprojet.safetyNet.controller.FireStationReponse;
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
	private List<Person> listPerson ;
	private Map<Person, Integer> enfantMap;
	Map<Person, List<MedicalRecord>> personMedicalRecordMap;
	private int nombreAdultes;
	private int nombreEnfants;
	private int stationNumber;

	
	/**
	 * service de l'URL http://localhost8080/fire?address=<address>
	 * retourne un objet de type DonneesLieesService comportant une Map person ListMedicalRecord habitant à l'address passée en paramètre
	 * et le stationNumber correspondant. 
	 * @param address
	 * @return
	 */
	public DonneesLieesService getFireAddress(String address){
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		Map<Person, List<MedicalRecord>> personMedicalRecordMap = new HashMap<Person, List<MedicalRecord>>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(key.getAddress() != address) {
				continue;
			}
			personMedicalRecordMap.put(key, value);				
		}
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation key = entry.getKey();
			if(key.getAddress() != address) {
				continue;
			}
			donneesLieesReponse.setStationNumber(key.getStation());
			break;
		}
		donneesLieesReponse.setPersonMedicalRecordMap(personMedicalRecordMap);
		return donneesLieesReponse;
	}
	
	/**
	 * service de l'URL http://localhost8080/station?stations=<a list of station_number>
	 * retourne une liste de tous les foyers desservis par la stationNumber. La liste doit regrouper les personnes par adresse et inclure les medicalRecord
	 */
	//TODO a implémenter
	
	/**
	 * 
	 * retourne un objet de type DonneesLIeesService comportant les adresses mail des habitants habitants dans la ville passée en paramètre
	 * @param city
	 * @return
	 */
	public DonneesLieesService getCommunityEmail(String city){
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		List<Person> listPerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (key.getCity() != city) {
				continue;
			}
			listPerson.add(key);
		}
		donneesLieesReponse.setListPerson(listPerson);
		return donneesLieesReponse;
	}
	
	/**
	 * retourne un objet de type DonneesLieesService comportant la liste de personne couverte par la stationNumber passée en paramètres de la méthode
	 * ainsi que le nombre d'enfant et d'adultes présent dans cette liste. 
	 * 
	 * @param stationNumber
	 * @return
	 */
	public DonneesLieesService getFireStationStationNumber (int stationNumber) {
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		// Récupération de la liste des personnes
		donneesLieesReponse.setListPerson(DonneesLieesService.listePersonneCouverteParFireStation(stationNumber, donneesEntree)); 
		// Récupération du nombre d'adulte et d'enfant
		List<Person> listeAdulte = new ArrayList<>();
		List<Person> listeEnfant = new ArrayList<>();
		this.listePersonnesMajeurMineur(donneesEntree, listeAdulte, listeEnfant);
		donneesLieesReponse.setNombreAdultes(listeAdulte.size());
		donneesLieesReponse.setNombreEnfants(listeEnfant.size());
		
		return donneesLieesReponse;
	}
	/**
	 * retourne un objet de type DonneesLieesService comportant la liste des enfants qui habitent à cette adresse, leur age et une autre liste 
	 * comportant les autres membres du foyer 
	 * @param address
	 * @return
	 */
	public DonneesLieesService getChildAlertAddress (String address) {
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		List<Person> listPerson = new ArrayList<Person>();
		Map<Person, Integer> enfantMap = new HashMap<Person, Integer>();
		// pour chaque key (person) de la Map si l'adresse est égale à l'adresse passée en paramètre, alors la Person est ajoutée dans la liste Adulte
		// si l'age est supérieur à 18ans ou enfant si il est inférieur. 
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(key.getAddress() != address) {
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
		return donneesLieesReponse;
	}
	
	/**
	 * retourne un objet de type DonneesLieesService comportant les numéros de téléphones des résidents couverts par la stationNumber passée en paramètres
	 * 
	 * @param stationNumber
	 * @return
	 */
	public DonneesLieesService getphoneAlerteFireStation (int stationNumber) {
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		List<Person> listPerson = DonneesLieesService.listePersonneCouverteParFireStation(stationNumber, donneesEntree);
		donneesLieesReponse.setListPerson(listPerson);
		return donneesLieesReponse;
	}
	
	
	/**
	 * Retourne un objet de type DonneesLieesService comportant une map de personnes (person ListMedicalRecord) ayant le même nom de famille
	 * 
	 */
	public static DonneesLieesService getPersonInfoLastName (String lastName) {
		DonneesLiees donneesEntree = DonneesLiees.getDonneesLiees();
		DonneesLieesService donneesLieesReponse = new DonneesLieesService();
		Map<Person, List<MedicalRecord>> personMedicalRecordMap = new HashMap<Person, List<MedicalRecord>>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(key.getLastName() != lastName) {
				continue;
			}
			personMedicalRecordMap.put(key, value);				
		}
		donneesLieesReponse.setPersonMedicalRecordMap(personMedicalRecordMap);
		return donneesLieesReponse;
	}
	


	/**
	 * Renvoie la liste de toutes les personnes couvertes par une FireStation donnée en paramètres
	 * @param numeroFireStation
	 * @param donneesEntree
	 * @return
	 */
	
	public static List<Person> listePersonneCouverteParFireStation(int numeroFireStation, DonneesLiees donneesEntree){
		
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<FireStation, List<Person>> entry : donneesEntree.getPersonByFirestation().entrySet()) {
			FireStation key = entry.getKey();
			List<Person> value = entry.getValue();
			if (key.getStation() != numeroFireStation) {
				continue;
			}
			listePerson.addAll(value);
			
		}
		return listePerson;
	}
	
	

	
	/**
	 * Retourne les habitants habitant à la même address passé en paramètre
	 */
	public List<Person> listeDesHabitantsMemeAddress (DonneesLiees donneesEntree, String address){
		
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (key.getAddress() != address) {
				continue;
			}
			listePerson.add(key);
		}
		return listePerson;
	}

	
	/**
	 * Retourne les habitants portant le nom passé en paramètre
	 */
	public List<Person> listeDesHabitantsMemeNom (DonneesLiees donneesEntree, String lastName){
		
		List<Person> listePerson = new ArrayList<Person>();
		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			if (key.getLastName() != lastName) {
				continue;
			}
			listePerson.add(key);
		}
		return listePerson;
	}
		
	
	
	/**
	 * Trie les personnes de plus de 18 ans (listeAdulte) et de moins de 18 ans (listeEnfant) dans deux listes distinctes
	 * @param donneesEntree
	 * @param listeAdulte
	 * @param listeEnfant
	 */
	public void listePersonnesMajeurMineur(DonneesLiees donneesEntree, List<Person> listeAdulte, List<Person> listeEnfant){

		for(Map.Entry<Person, List<MedicalRecord>> entry : donneesEntree.getMedicalRecordByPerson().entrySet()) {
			Person key = entry.getKey();
			List<MedicalRecord> value = entry.getValue();
			if(value.get(0).isAdulte()) {
				listeAdulte.add(key);
			}else {
				listeEnfant.add(key);
			}
			
		}
		
	}
}
