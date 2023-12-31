package org.example.repositories;

import org.example.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment,Long> {
    List<Payment> findAllByDoctorId(String doctorId);
    List<Payment> findAllByPatientId(String patientId);
}
