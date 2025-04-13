package com.oprprojet.safetyNet.testsIntegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.FireStationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class FireStationIntegrationTest {

	@Autowired
	Reader reader;
	@Autowired
	Writer writer;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	FireStationService fireStationService;
	@Autowired
	FireStation fireStation;

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
	public void addFireStationTest() throws Exception {
		String jsonContent = "{ \"address\": \"newValue\", \"stationNumber\": \"7\"}";
		fireStation = new FireStation("newValue", 7);

		mockMvc.perform(post("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("newValue"));
		;
	}

	@Test
	public void majFireStationTest() throws Exception {
		String jsonContent = "{ \"address\":\"908 73rd St\", \"station\":\"8\" }";
		fireStation = new FireStation("908 73rd St", 8);
		mockMvc.perform(put("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isOk())
				.andExpect(jsonPath("$.station").value("8"));
		;
	}

	@Test
	public void deleteFireStationTest() throws Exception {
		String jsonContent = "{ \"address\":\"908 73rd St\", \"station\":\"1\" }";
		fireStation = new FireStation("908 73rd St", 1);

		mockMvc.perform(delete("http://localhost:8080/firestation", fireStation).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContent)).andExpect(status().isNoContent())
				.andExpect(jsonPath("$.address").doesNotExist());
		;
	}

}
