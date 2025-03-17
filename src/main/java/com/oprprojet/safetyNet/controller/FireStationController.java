package com.oprprojet.safetyNet.controller;

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

import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.service.FireStationService;

@RestController
@RequestMapping("/firestation")
public class FireStationController {
	 private static final Logger logger = LogManager.getLogger(FireStationController.class);
	@Autowired
	FireStationService fireStationService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> createFireStation(@PathVariable int stationNumber, String address, @RequestBody FireStation fireStation) {
		try {
			fireStationService.addFireStation(fireStation);
		} catch (Exception e) {
			logger.error("createFireStation : la fireStation n'a pas été créee ");
			return ResponseEntity.notFound().build();
		}
		logger.info("createFireStation : réponse OK, la fireStation est créee");
		return ResponseEntity.ok(fireStation);
      
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> deleteFireStation(@PathVariable int stationNumber, String address, @RequestBody FireStation fireStation ) { 
		
		try {
			fireStationService.deleteFireStation(fireStation);
		} catch (Exception e) {
			logger.error("deleteFireStation : la fireStation n'a pas été supprimée ");
			return ResponseEntity.notFound().build(); 
		}
		logger.info("deleteFireStation : réponse OK, la fireStation est supprimée");
		return ResponseEntity.ok(fireStation);
         
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour la stationNumber d'une FireStation selon son address
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> majFireStation(@PathVariable int stationNumber, String address, @RequestBody FireStation fireStation) {
		try {
			fireStationService.updateFireStation(fireStation);
		} catch (Exception e) {
			logger.error("updateFireStation : la fireStation n'a pas été mise à jour ");
			return ResponseEntity.notFound().build();
		}
		logger.info("updateFireStation : réponse OK, la fireStation est mise à jour");
		return ResponseEntity.ok(fireStation);
    }
	
}
