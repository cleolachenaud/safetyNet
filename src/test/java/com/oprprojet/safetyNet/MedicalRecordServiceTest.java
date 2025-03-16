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

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedicalRecordServiceTest {
    @Mock
    private Reader jsonReaderMock;

    @Mock
    private Writer jsonWriterMock;

    @InjectMocks
    private MedicalRecordService medicalRecordService;    
    
    @Captor
    ArgumentCaptor<Donnees> argumentCaptorDonnees;
    
    
    
    @BeforeEach
    public void setUp() throws Exception {
    	
        // Créez un objet MedicalRecord à ajouter
    	Donnees donneesBrutes = new Donnees();
    	donneesBrutes.setFireStations(new ArrayList<FireStation>());
    	donneesBrutes.setPersons(new ArrayList<Person>());
    	List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();

    	MedicalRecord medicalRecord1 = createMedicalRecord(
    			"Appa",
    			"Paddaone",
    			"2021-04-14",
    			new ArrayList<String>(List.of("baytril","celestene")),
    			new ArrayList<String>(List.of("avocat","chocolat"))
    			);
        
    	MedicalRecord medicalRecord2 = createMedicalRecord(
    			"Moja",
    			"Paddatwo",
    			"2021-05-19",
    			new ArrayList<String>(List.of("","")),
    			new ArrayList<String>(List.of("avocat","chocolat"))
    			); 
    	medicalRecordList.add(medicalRecord1);
    	medicalRecordList.add(medicalRecord2);
    	donneesBrutes.setMedicalRecords(medicalRecordList);
    	

        // Simuler le comportement du reader et writer
        Mockito.when(jsonReaderMock.jsonReader()).thenReturn(donneesBrutes);

    }

    @Test
    public void addMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	MedicalRecord medicalRecord3 = createMedicalRecord(
    			"Marley",
    			"Paddathree",
    			"2021-06-21",
    			new ArrayList<String>(List.of("","")),
    			new ArrayList<String>(List.of("avocat"))
    			);
        
     	try {
     		medicalRecordService.addMedicalRecord(medicalRecord3);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 3", 3, argumentCaptorDonnees.getValue().getMedicalRecords().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", medicalRecord3, argumentCaptorDonnees.getValue().getMedicalRecords().get(2));
    }
   
    @Test
    public void updateMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
    	MedicalRecord medicalRecord1 = createMedicalRecord(
    			"Appa",
    			"Paddaone",
    			"2021-04-14",
    			new ArrayList<String>(List.of("baytril")),
    			new ArrayList<String>(List.of("avocat"))
    			);
     	try {
     		medicalRecordService.updateMedicalRecord(medicalRecord1);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 2", 2, argumentCaptorDonnees.getValue().getMedicalRecords().size());
        assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", medicalRecord1, argumentCaptorDonnees.getValue().getMedicalRecords().get(0));
    }
  
    @Test
    public void deleteMedicalRecordTest() throws Exception {
    	String erreurLoggee = "";

     	// Appelez la méthode d'ajout
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Moja");
        medicalRecord1.setLastName("Paddatwo");
        
     	try {
     		medicalRecordService.deleteMedicalRecord(medicalRecord1);
     	} catch (Exception e) {
     		erreurLoggee = e.toString();
     	}

        // Vérifiez que l'ajout s'est bien passé
        Mockito.verify(jsonWriterMock).jsonWriter(argumentCaptorDonnees.capture());
		assertEquals("IL n'y a pas eu d'erreur", "", erreurLoggee);
        assertEquals("la liste de données doit être égale à 1", 1, argumentCaptorDonnees.getValue().getMedicalRecords().size());
        //assertEquals("les éléments de la variable station doivent être identiques à ceux capturées par le mock", null, argumentCaptorDonnees.getValue().getFireStations().get(1));
    }
    
    
    private MedicalRecord createMedicalRecord(String firstName, String LastName, String birthdate, List<String> medications, List<String> allergies) throws ParseException {
    	 MedicalRecord medicalRecord = new MedicalRecord();
    	 // pour la simplicité du test, nous transformons le string du paramètre en date
         SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
         Date birthdateDate = simpleDateFormat.parse(birthdate);
    	 medicalRecord.setFirstName(firstName);
    	 medicalRecord.setLastName(LastName);
    	 medicalRecord.setBirthdate(birthdateDate);
    	 medicalRecord.setMedications(medications);
    	 medicalRecord.setAllergies(allergies);
    	 return medicalRecord;
    }
}
