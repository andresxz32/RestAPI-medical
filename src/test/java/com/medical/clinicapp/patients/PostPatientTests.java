package com.medical.clinicapp.patients;

import com.medical.clinicapp.ClinicappApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PostPatientTests extends ClinicappApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    @Test
    @DirtiesContext
    void shouldCreateANewPatient() {
        //Build patient
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Patient newPatient = new Patient(id,"Sarah","Smith",new Date(),new Date());

        //Create patient
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/patients", newPatient, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Validate if really exist
        ResponseEntity<Patient> patientResponse = restTemplate.getForEntity("/patients/" + id, Patient.class);
        assertThat(patientResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
