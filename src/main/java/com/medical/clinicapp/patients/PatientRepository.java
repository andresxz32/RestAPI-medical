package com.medical.clinicapp.patients;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface PatientRepository extends CrudRepository<Patient, UUID>, PagingAndSortingRepository<Patient, UUID> {


}

