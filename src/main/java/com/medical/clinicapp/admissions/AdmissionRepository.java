package com.medical.clinicapp.admissions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface AdmissionRepository extends CrudRepository<Admission, UUID>, PagingAndSortingRepository<Admission, UUID> {


}
