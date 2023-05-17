package com.vtw.dna.employee.controller;

import com.vtw.dna.employee.Employee;
import com.vtw.dna.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeRepository repository;

    @QueryMapping
    public Page<Employee> employees(@Argument int page,
                                    @Argument int size,
                                    @Argument String sortBy,
                                    @Argument String sortDir,
                                    @Argument String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));
        Page<Employee> employees = repository.findAllByNameContains(pageable, filter);
        return employees;
    }

    @QueryMapping
    public Employee employee(@Argument Long id) {
        Employee employee = repository.findById(id).orElseThrow();
        return employee;
    }

    @MutationMapping
    public Employee createEmployee(@Argument Employee employee) {
        repository.save(employee);
        return employee;
    }

    @MutationMapping
    public Employee updateEmployee(@Argument Employee employee) {
        Employee oldOne = repository.findById(employee.getId()).orElseThrow();
        oldOne.update(employee);
        repository.save(oldOne);
        return oldOne;
    }

    @MutationMapping
    public Employee deleteEmployee(@Argument Long id) {
        Employee oldOne = repository.findById(id).orElseThrow();
        repository.delete(oldOne);
        return oldOne;
    }

}
