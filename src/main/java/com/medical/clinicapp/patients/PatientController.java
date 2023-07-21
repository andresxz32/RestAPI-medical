package com.medical.clinicapp.patients;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientRepository patientRepository;
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }


    @PostMapping
    private ResponseEntity<Void> createAdmission(@RequestBody Patient newPatientRequest, UriComponentsBuilder ucb) {
        Patient savedPatient = patientRepository.save(newPatientRequest);
        URI locationOfNewCashCard = ucb
                .path("admissions/{id}")
                .buildAndExpand(savedPatient.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Patient> findById(@PathVariable UUID requestedId, Principal principal) {
        Optional<Patient> patient = patientRepository.findById(requestedId);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
