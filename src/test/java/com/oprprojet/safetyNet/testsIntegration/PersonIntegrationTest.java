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
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.PersonService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class PersonIntegrationTest {

	@Autowired
	Reader reader;
	@Autowired
	Writer writer;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	PersonService personService;
	@Autowired
	Person person;

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
	public void addPersonTest() throws Exception {
		String jsonContentadd = "{ \"firstName\": \"Appa\", \"lastName\": \"Paddaone\", \"address\": \"36 rue des paddas\", \"city\": \"paddaVille\", \"zip\": \"37100\", \"phone\": \"841-874-7462\", \"email\": \"appa@email.fr\"}";
		person = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462",
				"appa@email.com");
		mockMvc.perform(post("http://localhost:8080/person", person).contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContentadd)).andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Paddaone"));
	}

	@Test
	public void majPersonTest() throws Exception {
		String jsonContentmaj = "{ \"firstName\": \"John\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Paddaville\", \"zip\": \"97451\", \"phone\": \"841-874-6512\", \"email\": \"moja@email.com\"}";
		person = new Person("John", "Boyd", "1509 Culver St", "Paddaville", 97451, "841-874-6512", "moja@email.com");
		mockMvc.perform(put("http://localhost:8080/person").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(jsonContentmaj)).andExpect(status().isOk())
				.andExpect(jsonPath("$.city").value("Paddaville"))
				.andExpect(jsonPath("$.email").value("moja@email.com"));
		;
	}

	@Test
	public void deletePersonTest() throws Exception {
		String jsonContentdelete = "{ \"firstName\": \"John\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Culver\", \"zip\": \"97451\", \"phone\": \"841-874-6512\", \"email\": \"jaboyd@email.com\"}";
		String firstName = "John";
		String lastName = "Boyd";

		mockMvc.perform(delete("http://localhost:8080/person/{firstName}/{lastName}", firstName, lastName)
				.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(jsonContentdelete))
				.andExpect(status().isNoContent()).andExpect(jsonPath("$.firstName").doesNotExist());
		;
	}

}
