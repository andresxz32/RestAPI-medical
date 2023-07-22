package com.medical.clinicapp.admissions;

import com.medical.clinicapp.ClinicappApplicationTests;
import com.medical.clinicapp.patients.Patient;
import com.medical.clinicapp.services.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PostAdmissionTests extends ClinicappApplicationTests {


    @Autowired
    TestRestTemplate restTemplate;
    @Test
    @DirtiesContext
    void shouldCreateANewAdmission() {
        //Find patient
        ResponseEntity<Patient> response = restTemplate.getForEntity("/patients/550e8400-e29b-41d4-a716-446655440000",Patient.class);
        Patient patient = response.getBody();

        //Create service
        Service service = new Service(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),"Servicio 1", new Date(),new Date());
        ResponseEntity<Void> createResponseService = restTemplate.postForEntity("/services", service, Void.class);

        //Create admission
        UUID id = UUID.fromString("550e8400-e29b-41d4-a716-446655440001");
        List<Service> services = Arrays.asList(service);
        Admission newAdmission = new Admission(id,new Date(),new Date(), patient,services);
        ResponseEntity<Void> createResponseAdmission = restTemplate.postForEntity("/admissions", newAdmission, Void.class);
        assertThat(createResponseAdmission.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Validate if really exist
        ResponseEntity<Admission> admissionResponse = restTemplate.getForEntity("/admissions/" + id , Admission.class);
        assertThat(admissionResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
