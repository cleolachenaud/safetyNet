package com.oprprojet.safetyNet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.service.MedicalRecordService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc

public class medicalRecordControllerTest {
	@Autowired
	private MockMvc mockMvc; // Pour simuler les requêtes HTTP

	@MockitoBean
	private MedicalRecordService medicalRecordService;

	@Autowired
	MedicalRecord medicalRecord;

	String jsonContent = "{ \"firstName\": \"Appa\", \"lastName\": \"Paddaone\", \"birthdate\": \"2021/04/14\", \"medications\": [\"baytril\", \"celestene\"], \"allergies\": [\"avocat\", \"chocolat\"] }";

	@Test
	public void addMedicalRecordTest() throws Exception {

		Date birthdate = createBirthdate("2021-04-14");
		medicalRecord = new MedicalRecord("Appa", "Paddaone", birthdate, List.of("baytril"), List.of("avocat"));
		doReturn(medicalRecord).when(medicalRecordService).addMedicalRecord(any());
		mockMvc.perform(post("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("$.lastName").value("Paddaone"));
	}

	@Test
	public void addMedicalRecordTestKo() throws Exception {
		doReturn(null).when(medicalRecordService).addMedicalRecord(any());
		mockMvc.perform(post("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isNotFound());
	}

	@Test
	public void deleteMedicalRecordTest() throws Exception {
		String firstName = "Appa";
		String lastName = "Paddaone";

		doNothing().when(medicalRecordService).deleteMedicalRecord(firstName, lastName);

		mockMvc.perform(delete("http://localhost:8080/medicalRecord/{firstName}/{lastName}", firstName, lastName)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isNoContent())
				.andExpect(jsonPath("$.lastName").doesNotExist());
	}

	@Test
	public void deleteMedicalRecordTestNull() throws Exception {
		String firstName = null;
		String lastName = null;

		doNothing().when(medicalRecordService).deleteMedicalRecord(firstName, lastName);
		mockMvc.perform(delete("http://localhost:8080/medicalRecord/{firstName}/{lastName}", firstName, lastName)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isNotFound());
	}

	@Test
	public void majMedicalRecordTest() throws Exception {
		Date birthdate = createBirthdate("2021-04-14");
		medicalRecord = new MedicalRecord("Moja", "Paddaone", birthdate, List.of("baytril"), List.of("avocat"));
		doReturn(medicalRecord).when(medicalRecordService).updateMedicalRecord(any());
		mockMvc.perform(put("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isOk()).andExpect(jsonPath("$.firstName").value("Moja"));
	}

	@Test
	public void majMedicalRecordTestKo() throws Exception {
		doReturn(null).when(medicalRecordService).updateMedicalRecord(any());
		mockMvc.perform(put("http://localhost:8080/medicalRecord", medicalRecord)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContent))
				.andExpect(status().isNotFound());
	}

	private Date createBirthdate(String birthdate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdateDate = simpleDateFormat.parse(birthdate);
		return birthdateDate;
	}
}
