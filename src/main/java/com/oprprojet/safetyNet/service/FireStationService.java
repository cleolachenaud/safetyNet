package com.oprprojet.safetyNet.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;

@Service
public class FireStationService {
	@Autowired
	FireStation fireStation;

	@Autowired
	private Reader reader;

	@Autowired
	private Writer writer;

	private static final Logger logger = LogManager.getLogger(FireStationService.class);

	/**
	 * Permet d'ajouter une FireStation (stationNumber, address)
	 */

	public FireStation addFireStation(FireStation fireStation) throws Exception {
		logger.debug("methode addFireStation : lancement de la methode");
		// Lire les données
		Donnees donneesBrute = reader.jsonReader();
		logger.debug("methode addFireStation : debut du traitement");
		// Ajouter la FireStation transmise dedans en vérifiant qu'elle n'existe pas
		// déjà.
		List<FireStation> fireStationList = donneesBrute.getFireStations();
		Boolean fireStationDejaExistante = false;
		for (FireStation fireStationElement : fireStationList) {
			if (fireStationElement.equals(fireStation)) {
				fireStationDejaExistante = true;
				logger.warn(
						"methode addFireStation : la FireStation que vous essayez d'ajouter existe déjà, elle ne sera pas ajoutée");
				break;
			}
		}
		if (!fireStationDejaExistante) {
			fireStationList.add(fireStation);
			logger.debug("methode addFireStation : la FireStation a bien été ajoutée");
			// Ecrire les données.
			writer.jsonWriter(donneesBrute);
			return fireStation;
		}
		logger.debug("methode addFireStation : fin de la methode");

		return null;
	}

	/**
	 * Permet de supprimer une FireStation selon l'address ou la stationNumber
	 */

	public void deleteFireStation(FireStation fireStation) throws Exception {
		logger.debug("methode deleteFireStation : lancement de la methode");
		// Lire les données
		Donnees donneesBrute = reader.jsonReader();

		logger.debug("methode deleteFireStation : debut du traitement");
		// Supprimer la fireStation transmise.
		List<FireStation> toRemoveFireStation = new ArrayList<FireStation>();
		List<FireStation> fireStationList = donneesBrute.getFireStations();
		for (FireStation fireStationElement : fireStationList) {
			if (fireStationElement.getAddress().equals(fireStation.getAddress())
					|| fireStationElement.getStation() == fireStation.getStation()) {
				toRemoveFireStation.add(fireStation);
			}
		}
		if (!toRemoveFireStation.isEmpty()) { // si la liste à supprimer n'est PAS nulle alors je fais un traitement
			fireStationList.removeAll(toRemoveFireStation);
			logger.debug("methode deleteFireStation : la FireStation a bien été supprimée");
			// Ecrire les données.
			writer.jsonWriter(donneesBrute);
		}
		logger.debug("methode deleteFireStation : fin de la methode");
	}

	/**
	 * Permet de changer le numéro (stationNumber) d'une fireStation (stationNumber,
	 * address)
	 */
	public FireStation updateFireStation(FireStation fireStation) throws Exception {
		logger.debug("methode updateFireStation : lancement de la methode");
		// Lire les données
		Donnees donneesBrute = reader.jsonReader();
		logger.debug("methode updateFireStation : debut du traitement");
		// modifie la stationNumber transmise.
		List<FireStation> fireStationList = donneesBrute.getFireStations();
		FireStation fireStationMiseAJour = new FireStation();
		for (FireStation fireStationElement : fireStationList) {
			if (!fireStationElement.getAddress().equals(fireStation.getAddress())) {
				continue;
			}
			fireStationElement.setStation(fireStation.getStation());
			fireStationMiseAJour.setStation(fireStationElement.getStation());
			fireStationMiseAJour.setAddress(fireStationElement.getAddress());
			logger.debug("methode updateFireStation : la FireStation a bien été modifiée");
			break; // quand une fireStation est mise à jour, la méthode s'arrête.
		}

		// Ecrire les données.
		writer.jsonWriter(donneesBrute);
		logger.debug("methode updateFireStation : fin de la methode");
		return fireStationMiseAJour;
	}

}
