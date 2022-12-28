package com.cache.service;

import com.cache.entity.Employee;
import com.cache.exception.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeService {

    private List<Employee> employeeList = new ArrayList<>();

    @Cacheable(cacheNames="employee",key="#id")
    public Employee getEmployeeByID(String id)
    {
        Optional<Employee> emp = employeeList.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst();
        return emp.orElseThrow(()-> new ResourceNotFoundException("Employee not found with id "+id));
    }

    @CachePut(cacheNames="employee", key="#id" , unless="#result==null")
    public Employee updateEmployee(String id,String name) {
        Optional<Employee> emp = employeeList.stream().filter(e -> e.getId().equalsIgnoreCase(id)).findFirst();
        emp.map(existingEmp ->{
            existingEmp.setName(name);
            return employeeList.add(existingEmp);
        }).orElseGet(()->{
            return employeeList.add(new Employee(id, name));
        });

        return null;
    }

    @CacheEvict(value = "employee", key="#id")
    public void deleteEmployee(String id){
        employeeList.removeIf(e -> e.getId().equalsIgnoreCase(id));
    }
}
