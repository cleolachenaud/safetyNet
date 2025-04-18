package com.oprprojet.safetyNet;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.FireStationService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class FireStationServiceTest {
	@Mock
	private Reader jsonReaderMock;

	@Mock
	private Writer jsonWriterMock;

	@InjectMocks
	private FireStationService fireStationService;

	@Captor
	ArgumentCaptor<Donnees> argumentCaptorDonnees;

	@BeforeEach
	public void setUp() throws Exception {

		// Créez un objet FireStation à ajouter
		Donnees donneesBrutes = new Donnees();
		donneesBrutes.setPersons(new ArrayList<Person>());
		donneesBrutes.setMedicalRecords(new ArrayList<MedicalRecord>());
		List<FireStation> fireStationList = new ArrayList<FireStation>();
		FireStation station5 = new FireStation("address5", 5);
		FireStation station6 = new FireStation("address6", 6);
		fireStationList.add(station5);
		fireStationList.add(station6);
		donneesBrutes.setFireStations(fireStationList);

		// Simuler le comportement du reader et writer
		Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);

	}

	@Test
	public void addFireStationTest() throws Exception {
		String erreurLoggee = "";

		// Appelez la méthode d'ajout
		FireStation station7 = new FireStation("address7", 7);

		try {
			fireStationService.addFireStation(new FireStation("address7", 7));
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace();
		}

		// Vérifiez que l'ajout s'est bien passé

		Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);

		assertEquals("la liste de données doit être égale à 3", 3,
				argumentCaptorDonnees.getValue().getFireStations().size());
		assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock",
				station7, argumentCaptorDonnees.getValue().getFireStations().get(2));
	}

	@Test
	public void updateFireStationTest() throws Exception {
		String erreurLoggee = "";

		// Appelez la méthode d'ajout
		FireStation station6 = new FireStation("address6", 6);

		try {
			fireStationService.updateFireStation(new FireStation("address6", 6));
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace();
		}

		// Vérifiez que l'ajout s'est bien passé
		Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("la liste de données doit être égale à 2", 2,
				argumentCaptorDonnees.getValue().getFireStations().size());
		assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock",
				station6, argumentCaptorDonnees.getValue().getFireStations().get(1));
	}

	@Test
	public void deleteFireStationTest() throws Exception {
		String erreurLoggee = "";

		// Appelez la méthode d'ajout
		FireStation station6 = new FireStation("address6", 6);

		try {
			fireStationService.deleteFireStation(new FireStation("address6", 6));
		} catch (Exception e) {
			erreurLoggee = e.toString();
			e.printStackTrace();
		}

		// Vérifiez que l'ajout s'est bien passé
		Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
		assertEquals("la liste de données doit être égale à 1", 1,
				argumentCaptorDonnees.getValue().getFireStations().size());
	}
}
