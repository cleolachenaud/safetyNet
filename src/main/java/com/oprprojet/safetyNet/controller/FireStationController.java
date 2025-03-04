package com.oprprojet.safetyNet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@Autowired
	FireStationService fireStationService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStation fireStation) {
		try {
			fireStationService.addFireStation(fireStation);
		} catch (Exception e) {
	        return new ResponseEntity<>(fireStation, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(fireStation, HttpStatus.CREATED);
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer une FireStation
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> deleteFireStation(@RequestBody FireStation fireStation) {
		try {
			fireStationService.deleteFireStation(fireStation);
		} catch (Exception e) {
	        return new ResponseEntity<>(fireStation, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(fireStation, HttpStatus.ACCEPTED);
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour la stationNumber d'une FireStation selon son address
	 * @param fireStation
	 * @return
	 */
    public ResponseEntity<FireStation> majFireStation(@RequestBody FireStation fireStation) {
		try {
			fireStationService.updateFireStation(fireStation);
		} catch (Exception e) {
	        return new ResponseEntity<>(fireStation, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(fireStation, HttpStatus.ACCEPTED);
    }
	
}
