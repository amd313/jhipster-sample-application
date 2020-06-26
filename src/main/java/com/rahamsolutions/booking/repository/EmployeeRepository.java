package com.rahamsolutions.booking.repository;

import com.rahamsolutions.booking.domain.Employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Employee entity.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select distinct employee from Employee employee left join fetch employee.services",
        countQuery = "select count(distinct employee) from Employee employee")
    Page<Employee> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct employee from Employee employee left join fetch employee.services")
    List<Employee> findAllWithEagerRelationships();

    @Query("select employee from Employee employee left join fetch employee.services where employee.id =:id")
    Optional<Employee> findOneWithEagerRelationships(@Param("id") Long id);
}
