package com.vtw.dna.employee.repository;

import com.vtw.dna.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByNameContains(Pageable pageable, String searchName);

}
