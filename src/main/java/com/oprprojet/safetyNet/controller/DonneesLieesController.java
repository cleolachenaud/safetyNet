package com.oprprojet.safetyNet.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oprprojet.safetyNet.service.DonneesLieesService;

@RestController
public class DonneesLieesController {
	private static final Logger logger = LogManager.getLogger(DonneesLieesController.class);
	@Autowired
	private DonneesLieesService donneesLieesService = new DonneesLieesService();

	/**
	 * Controller de l'url fireStationNumber
	 * 
	 * @param stationNumber
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/firestation")
	public ResponseEntity<String> fireStationStationNumber(
			@RequestParam int stationNumber) throws Exception {
		logger.debug("lancement fireStationStationNumber URL avec la stationNumber : {}", stationNumber);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getFireStationStationNumber(stationNumber);
			String reponseString = mapper.writeValueAsString(reponse); // Conversion avec indentation
			logger.info("Réponse OK FireStationStationNumber ");
			return ResponseEntity.ok(reponseString); // Retourne le JSON indenté
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getFireStationStationNumber : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de l'url childAlert
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<String> childAlertAddress(
			@RequestParam String address)
			throws Exception {
		logger.debug("lancement chilAlertAddress URL avec l'adresse : {}", address);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getChildAlertAddress(address);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK childAlertAdress ");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getChilAlertAddress : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de l'url phoneAlert
	 * 
	 * @param station
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/phoneAlert")
	public ResponseEntity<String> phoneAlerteFireStation(
			@RequestParam int firestation) throws Exception {
		logger.debug("lancement phoneAlerte avec la stationNumber : {}", firestation);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getPhoneAlerteFireStation(firestation);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK phoneAlert ");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getPhoneAlert : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de fireAddress
	 * 
	 * @param address
	 * @return
	 * @throws Exception
	 * 
	 */
	@GetMapping("/fire") 
	public ResponseEntity<String> fireAddress(
			@RequestParam String address)
			throws Exception {
		logger.debug("Lancement fireAddress avec l'adresse : {}", address);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getFireAddress(address);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK fireAddress");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getFireAddress : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de l'url floodstation
	 * 
	 * @param stationNumber
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/flood/stations")
	public ResponseEntity<String> floodStation(
			@RequestParam List<Integer> stations)
			throws Exception {
		logger.debug("Lancement floodStation avec la liste des stationNumber : {}", stations);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getFloodStations(stations);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK floodStation ");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getFloodStation : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de personInfoLastName
	 * 
	 * @param lastName
	 * @return
	 * @throws Exception
	 */

	@GetMapping("/personInfo")
	public ResponseEntity<String> personInfoLastName(
			@RequestParam String lastName)
			throws Exception {
		logger.debug("lancement personInfoLastName URL avec le nom de famille : {}", lastName);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getPersonInfoLastName(lastName);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK perInfoLastName ");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getPersonInfoLastName : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}

	/**
	 * Controller de l'url communityEmail
	 * 
	 * @param city
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<String> communityEmail(
			@RequestParam String city)
			throws Exception {
		logger.debug("lancement communityEmail URL avec la ville : {}", city);
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			Map<String, Object> reponse = donneesLieesService.getCommunityEmail(city);
			String reponseString = mapper.writeValueAsString(reponse); 
			logger.info("Réponse OK communityEmail ");
			return ResponseEntity.ok(reponseString); 
		} catch (Exception e) {
			logger.error("Erreur lors de l'appel à getCommunityEmail : {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"error\": \"Une erreur est survenue.\"}");
		}
	}
}
