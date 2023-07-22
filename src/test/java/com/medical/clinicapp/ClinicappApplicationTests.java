package com.medical.clinicapp;

import com.medical.clinicapp.admissions.Admission;
import com.medical.clinicapp.patients.Patient;
import com.medical.clinicapp.services.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClinicappApplicationTests {

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

	@Test
	@DirtiesContext
	void shouldCreateANewService() {
		Date nowDate = new Date();
		//Create service
		Service service = new Service(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"),"Servicio 1", nowDate,nowDate);
		ResponseEntity<Void> createResponseService = restTemplate.postForEntity("/services", service, Void.class);

		//Validate if exist
		ResponseEntity<Service> serviceResponse = restTemplate.getForEntity("/services/" + service.getId(), Service.class);
		assertThat(serviceResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		Service serviceModel = serviceResponse.getBody();
		serviceModel.setUpdatedAt(nowDate);
		serviceModel.setCreatedAt(nowDate);

		//Validate if service is same
		assertEquals(serviceModel,service);
	}




}
