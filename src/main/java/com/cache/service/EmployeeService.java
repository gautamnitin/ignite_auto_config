package com.cache.service;

import com.cache.entity.Employee;
import com.cache.entity.EmployeeKey;
import com.cache.exception.ResourceNotFoundException;
import com.cache.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Cacheable(cacheNames = "employee", key = "#id")
    public Employee getEmployeeByID(String id) {
        return employeeRepository.findById(new EmployeeKey(id)).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @CachePut(cacheNames = "employee", key = "#id", unless = "#result==null")
    public Employee updateEmployee(String id, String name) {
        Optional<Employee> emp = employeeRepository.findById(new EmployeeKey(name));
        emp.map(existingEmp -> {
            existingEmp.setName(name);
            return employeeRepository.save(existingEmp.getKey(), existingEmp);
        }).orElseGet(() -> {
            Employee newEmployee = new Employee(id, name);
            return employeeRepository.save(newEmployee.getKey(), newEmployee);
        });

        return null;
    }

    @CacheEvict(value = "employee", key = "#id")
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(new EmployeeKey(id));
    }
}
