package com.oprprojet.safetyNet.testsIntegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.MedicalRecordService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class MedicalRecordIntegration {

	@Autowired
	Reader reader;
	@Autowired
	Writer writer;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	MedicalRecordService medicalRecordService;
	@Autowired
	MedicalRecord medicalRecord;

	Donnees donnees;

	@BeforeEach
	public void setUp() throws Exception {
		donnees = reader.jsonReader();
	}

	@AfterEach
	public void remiseAZeroDuFichier() throws Exception {
		writer.jsonWriter(donnees);
	}

	@Test
	public void addMedicalRecordTest() throws Exception {

		Date birthdate = createBirthdate("2021-04-14");
		String jsonContent = "{ \"firstName\": \"Appa\", \"lastName\": \"Paddaone\", \"birthdate\": \"2021/04/14\", \"medications\": [\"baytril\", \"celestene\"], \"allergies\": [\"avocat\", \"chocolat\"] }";
		medicalRecord = new MedicalRecord("Appa", "Paddaone", birthdate, List.of("baytril"), List.of("avocat"));

		mockMvc.perform(post("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("$.lastName").value("Paddaone"));
	}

	@Test
	public void majMedicalRecordTest() throws Exception {
		String jsonContent = "{ \"firstName\": \"John\", \"lastName\": \"Boyd\", \"birthdate\": \"03/06/1984\", \"medications\": [\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"avocat\"] }";
		Date birthdate = createBirthdate("03-06-1984");
		medicalRecord = new MedicalRecord("John", "Boyd", birthdate, List.of("aznol:350mg", "hydrapermazol:100mg"),
				List.of("avocat"));

		mockMvc.perform(put("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("$.allergies").value("avocat"));
	}

	@Test
	public void deleteMedicalRecordTest() throws Exception {
		String firstName = "John";
		String lastName = "Boyd";
		mockMvc.perform(delete("http://localhost:8080/medicalRecord/{firstName}/{lastName}", firstName, lastName)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").doesNotExist());
	}

	private Date createBirthdate(String birthdate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdateDate = simpleDateFormat.parse(birthdate);
		return birthdateDate;
	}

}
