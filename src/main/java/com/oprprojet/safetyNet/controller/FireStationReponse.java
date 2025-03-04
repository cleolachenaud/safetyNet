package com.oprprojet.safetyNet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.service.DonneesLieesService;

import lombok.Data;
@Data
@Component
public class FireStationReponse {
	private List<Person> fireStationAlert ;
	private int nombreAdultes;
	private int nombreEnfants;
	
	@Autowired 
	private DonneesLieesService donneesLieesService;
	
	/**
	 * retourne un objet de type FireStationReponse comportant la liste de personne couverte par la stationNumber passée en paramètres de la méthode
	 * ainsi que le nombre d'enfant et d'adultes présent dans cette liste. 
	 * @param stationNumber
	 * @return
	 */
	public FireStationReponse getReponse (int stationNumber) {
		FireStationReponse fireStationReponse = new FireStationReponse();
		DonneesLiees donneesEntree = DonneesLiees.conversionDonneesBrutDonneesLiees(Reader.jsonReader("data.json"));
		List<Person> fireStationAlert = DonneesLieesService.listePersonneCouverteParFireStation(stationNumber, donneesEntree);
		List<Person> listeAdulte = new ArrayList<>();
		List<Person> listeEnfant = new ArrayList<>();
		this.listePersonnesMajeurMineur(donneesEntree, listeAdulte, listeEnfant);
		
		fireStationReponse.setFireStationAlert(fireStationAlert);
		fireStationReponse.setNombreAdultes(listeAdulte.size());
		fireStationReponse.setNombreEnfants(listeEnfant.size());
		return fireStationReponse;
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
