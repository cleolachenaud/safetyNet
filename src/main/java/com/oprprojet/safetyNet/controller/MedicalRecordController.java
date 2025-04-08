package com.oprprojet.safetyNet.controller;

import java.util.Date;
import java.util.List;

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
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
	 private static final Logger logger = LogManager.getLogger(MedicalRecord.class);
	@Autowired
	MedicalRecord medicalRecordReponse;
	
	@Autowired
	MedicalRecordService medicalRecordService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter un medicalRecord
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordReponse;
		try {
			medicalRecordReponse = medicalRecordService.addMedicalRecord(medicalRecord);
			
		} catch (Exception e) {
			logger.error("createMedicalRecord : le medicalRecord n'a pas été crée ");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		if (medicalRecordReponse == null) {
			return ResponseEntity.notFound().build();
		}
		logger.info("createMedicalRecord : réponse OK, le medicalRecord est crée");
		return ResponseEntity.ok(medicalRecordReponse);
    }
	
	@DeleteMapping("/{firstName}/{lastName}")
	/**
	 * controller qui permet de supprimer un medicalRecord avec en clé firstName et lastName
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
		
		try {
			medicalRecordService.deleteMedicalRecord(firstName, lastName);
		} catch (Exception e) {
			logger.error("deleteMedicalRecord : le medicalRecord n'a pas été supprimé ");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		logger.info("deleteMedicalRecord : réponse OK, le medicalRecord est supprimé");
		return ResponseEntity.noContent().build();
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour un medicalRecord sauf firstName et lastName
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> majMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		MedicalRecord medicalRecordReponse;
		try { 
			medicalRecordReponse = medicalRecordService.updateMedicalRecord(medicalRecord);
		} catch (Exception e) {
			logger.error("updateMedicalRecord : le medicalRecord n'a pas été mis à jour ");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		if (medicalRecordReponse == null) {
			return ResponseEntity.notFound().build();
		}
		logger.info("updateMedicalRecord : réponse OK, le medicalRecord est mis à jour");
		return ResponseEntity.ok(medicalRecordReponse);
    }
}
