package com.medical.clinicapp.services;

import com.medical.clinicapp.patients.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ServiceRepository extends CrudRepository<Service, UUID>, PagingAndSortingRepository<Service, UUID>  {
}




