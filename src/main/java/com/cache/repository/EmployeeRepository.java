package com.cache.repository;

import com.cache.entity.Employee;
import com.cache.entity.EmployeeKey;
import org.apache.ignite.springdata22.repository.IgniteRepository;
import org.apache.ignite.springdata22.repository.config.RepositoryConfig;

@RepositoryConfig(cacheName = "EmployeeCache")
public interface EmployeeRepository extends IgniteRepository<Employee, EmployeeKey> {
}
