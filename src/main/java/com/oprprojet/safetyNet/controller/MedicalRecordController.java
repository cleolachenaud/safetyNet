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
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

	
	@Autowired
	MedicalRecordService medicalRecordService;

	@PostMapping
	/**
	 * controller qui permet d'ajouter un medicalRecord
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		try {
			medicalRecordService.addMedicalRecord(medicalRecord);
		} catch (Exception e) {
	        return new ResponseEntity<>(medicalRecord, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
    }
	
	@DeleteMapping
	/**
	 * controller qui permet de supprimer un medicalRecord avec en clé firstName et lastName
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		try {
			medicalRecordService.deleteMedicalRecord(medicalRecord);
		} catch (Exception e) {
	        return new ResponseEntity<>(medicalRecord, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(medicalRecord, HttpStatus.ACCEPTED);
    }
	@PutMapping
	/**
	 * controller qui permet de mettre à jour un medicalRecord sauf firstName et lastName
	 * @param medicalRecord
	 * @return
	 */
    public ResponseEntity<MedicalRecord> majMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		try {
			medicalRecordService.updateMedicalRecord(medicalRecord);
		} catch (Exception e) {
	        return new ResponseEntity<>(medicalRecord, HttpStatus.NOT_MODIFIED);
		}
        return new ResponseEntity<>(medicalRecord, HttpStatus.ACCEPTED);
    }
}
