package com.hrmgr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hrmgr.models.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

}
