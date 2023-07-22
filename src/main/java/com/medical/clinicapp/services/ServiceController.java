package com.medical.clinicapp.services;

import com.medical.clinicapp.patients.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createAdmission(@RequestBody Service newServiceRequest, UriComponentsBuilder ucb) {
        Service savedService = serviceRepository.save(newServiceRequest);
        URI locationOfNewCashCard = ucb
                .path("admissions/{id}")
                .buildAndExpand(savedService.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Service> findById(@PathVariable UUID requestedId) {
        Optional<Service> service = serviceRepository.findById(requestedId);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Service>> findAll(Pageable pageable) {
        Page<Service> page = serviceRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))));
        return ResponseEntity.ok(page.getContent());
    }

}
