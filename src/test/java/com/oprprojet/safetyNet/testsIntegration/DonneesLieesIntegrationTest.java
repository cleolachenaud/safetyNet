package com.oprprojet.safetyNet.testsIntegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.oprprojet.safetyNet.model.DonneesLiees;
import com.oprprojet.safetyNet.service.DonneesLieesService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class DonneesLieesIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	DonneesLieesService donneesLieesService;
	@Autowired
	DonneesLiees donneesLiees;

	@Test
	public void fireStationStationNumberTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=1")).andExpect(status().isOk()) // Vérifiez
																												// le
																												// statut
																												// de la
																												// réponse
				.andExpect(jsonPath("$.NombreAdultes").value(5)).andExpect(jsonPath("$.NombreEnfants").value(1))
				.andExpect(jsonPath("$.ListePersonnes[0].FirstName").value("Jamie")); // Vérifiez une valeur dans la
																						// réponse JSON

	}

	@Test
	public void childAlertTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/childAlert?address=1509 Culver St")).andExpect(status().isOk())

				.andExpect(jsonPath("$.NombreEnfants").value(2))
				.andExpect(jsonPath("$.Foyer[0].FirstName").value("Jacob"));

	}

	@Test
	public void phoneAlertTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=2")).andExpect(status().isOk())
				.andExpect(jsonPath("$.ListePhone[2].Phone").value("841-874-7512"));

	}

	@Test
	public void fireAddressTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/fire?address=1509 Culver St")).andExpect(status().isOk())

				.andExpect(jsonPath("$.fireStationNumber").value("3"))
				.andExpect(jsonPath("$.ListePersonnes[0].LastName").value("Boyd"));
	}

	@Test
	public void floodStationTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/flood/stations?stations=1,3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.Foyer[0].ListePersonnes[0].LastName").value("Shepard"));
	}

	@Test
	public void personInfoLastNameTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/personInfo?lastName=Boyd")).andExpect(status().isOk())

				.andExpect(jsonPath("$.ListePersonnes[0].Age").value("59"));
	}

	@Test
	public void communityEmailTest() throws Exception {
		mockMvc.perform(get("http://localhost:8080/communityEmail?city=Culver")).andExpect(status().isOk())

				.andExpect(jsonPath("$.ListeEmails[0].Email").value("lily@email.com"));
	}

}
