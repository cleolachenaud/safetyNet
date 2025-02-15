package com.oprprojet.safetyNet.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class MedicalRecord {
	private String firstName;
    private String lastName;
    private Date birthdate;
    private List<String> medications;
    private List<String> allergies;
}
