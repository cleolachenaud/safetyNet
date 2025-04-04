package com.oprprojet.safetyNet.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.service.FireStationService;

@RestController
@RequestMapping("/firestation")
public class FireStationController {
	private static final Logger logger = LogManager.getLogger(FireStationController.class);
	@Autowired 
	FireStation fireStationReponse;
	@Autowired
	FireStationService fireStationService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
		logger.debug("lancement createFireStation : {}", fireStation);
		FireStation fireStationReponse;
		//ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			fireStationReponse = fireStationService.addFireStation(fireStation);
			//String reponseString = mapper.writeValueAsString(fireStationReponse);
		 
		} catch (Exception e) {
			logger.error("createFireStation : la fireStation n'a pas été créee ");
			return ResponseEntity.internalServerError().build();
		}
		if (fireStationReponse == null) {
			return ResponseEntity.status(500).body(fireStationReponse);
		}
		logger.info("createFireStation : réponse OK, la fireStation est créee");
		return ResponseEntity.ok(fireStationReponse);
      
			    
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> deleteFireStation(@RequestBody FireStation fireStation) { 
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		try {
			fireStationService.deleteFireStation(fireStation);
		} catch (Exception e) {
			logger.error("deleteFireStation : la fireStation n'a pas été supprimée ");
			return ResponseEntity.internalServerError().build();
		}
		logger.info("deleteFireStation : réponse OK, la fireStation est supprimée");
		return ResponseEntity.ok(null);
         
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour la stationNumber d'une FireStation selon son address
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> majFireStation(@RequestBody FireStation fireStation) {
		//ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		FireStation fireStationReponse;
		try {
			fireStationReponse = fireStationService.updateFireStation(fireStation);
		} catch (Exception e) {
			logger.error("updateFireStation : la fireStation n'a pas été mise à jour ");
			return ResponseEntity.notFound().build();
		}
		if (fireStationReponse == null) {
			return ResponseEntity.status(300).body(fireStationReponse);
		}
		logger.info("updateFireStation : réponse OK, la fireStation est mise à jour");
		return ResponseEntity.ok(fireStationReponse);
    }

}
