package com.medical.clinicapp.admissions;

import com.medical.clinicapp.patients.Patient;
import com.medical.clinicapp.services.Service;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADMISSIONS")
public class Admission {

    @Id
    private UUID id;

    @Column(name = "created_at", nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne()
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @Column(name = "services")
    @OneToMany
    @JoinColumn(name = "id_admission")
    private List<Service> services;

    @PrePersist
    public void prePersist() {
        Date nowDate = new Date();
        createdAt = nowDate;
        updatedAt = nowDate;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void removeService(Service service) {
        services.remove(service);
    }

}
