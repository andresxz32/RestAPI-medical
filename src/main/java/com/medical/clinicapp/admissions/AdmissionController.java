package com.medical.clinicapp.admissions;

import com.medical.clinicapp.patients.Patient;
import com.medical.clinicapp.patients.PatientRepository;
import com.medical.clinicapp.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/admissions")
public class AdmissionController {
    private final AdmissionRepository admissionRepository;

    public AdmissionController(AdmissionRepository admissionRepository, PatientRepository patientRepository) {
        this.admissionRepository = admissionRepository;
    }


    @PostMapping
    private ResponseEntity<Void> createAdmission(@RequestBody Admission newAdmissionRequest, UriComponentsBuilder ucb) {
        Admission savedAdmission = admissionRepository.save(newAdmissionRequest);
        URI locationOfNewCashCard = ucb
                .path("admissions/{id}")
                .buildAndExpand(savedAdmission.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Admission> findById(@PathVariable UUID requestedId){
        Optional<Admission> admission = admissionRepository.findById(requestedId);
        return admission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
