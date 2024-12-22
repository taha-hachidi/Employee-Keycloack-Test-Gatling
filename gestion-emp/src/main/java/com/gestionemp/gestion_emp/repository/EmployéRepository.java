package com.gestionemp.gestion_emp.repository;

import com.gestionemp.gestion_emp.model.Employé;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployéRepository extends JpaRepository<Employé,Long> {
}
