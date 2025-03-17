package com.oprprojet.safetyNet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oprprojet.safetyNet.model.Donnees;
import com.oprprojet.safetyNet.model.FireStation;
import com.oprprojet.safetyNet.model.MedicalRecord;
import com.oprprojet.safetyNet.model.Person;
import com.oprprojet.safetyNet.repository.Reader;
import com.oprprojet.safetyNet.repository.Writer;
import com.oprprojet.safetyNet.service.MedicalRecordService;
import com.oprprojet.safetyNet.service.PersonService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private Reader jsonReaderMock;

    @Mock
    private Writer jsonWriterMock;

    @InjectMocks
    private PersonService personService;    
    
    @Captor
    ArgumentCaptor<Donnees> argumentCaptorDonnees;
    
    
    
    @BeforeEach
    public void setUp() throws Exception {
    	
        // Créez un objet MedicalRecord à ajouter
    	Donnees donneesBrutes = new Donnees();
    	donneesBrutes.setFireStations(new ArrayList<FireStation>());
    	donneesBrutes.setMedicalRecords(new ArrayList<MedicalRecord>());
    	List<Person> personList = new ArrayList<Person>();
    	Person person1 = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462", "appa@email.com");
    	Person person2 = new Person("Moja", "Paddatwo", "36 rue des paddas", "paddaVille", 37100, "843-894-7462", "moja@email.com");
    	
    	personList.add(person1);
    	personList.add(person2);
    	donneesBrutes.setPersons(personList);
    	

        // Simuler le comportement du reader et writer
        Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);

    }

    @Test
    public void addPersonTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	Person person3 = new Person("Marley", "Paddathree", "12 rue des gloutons", "paddaVille", 37100, "561-094-3928", "marley@email.com");
     	try {
     		personService.addPerson(person3);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 3", 3, argumentCaptorDonnees.getValue().getPersons().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", person3, argumentCaptorDonnees.getValue().getPersons().get(2));
    }
   
    @Test
    public void updatePersonTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	Person person1 = new Person("Appa", "Paddaone", "36 rue des paddas", "paddaVille", 37100, "841-874-7462", "appa@email.com");
     	try {
     		personService.updatePerson(person1);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 2", 2, argumentCaptorDonnees.getValue().getPersons().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", person1, argumentCaptorDonnees.getValue().getPersons().get(0));
    }
  
    @Test
    public void deletePersonTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	Person person1 = new Person("Appa", "Paddaone", null, null, 0, null, null);

     	try {
     		personService.deletePerson(person1);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 1", 1, argumentCaptorDonnees.getValue().getPersons().size());
    }
}
