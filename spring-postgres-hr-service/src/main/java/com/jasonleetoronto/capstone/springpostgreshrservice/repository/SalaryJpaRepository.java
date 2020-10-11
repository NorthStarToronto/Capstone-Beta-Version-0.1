package com.jasonleetoronto.capstone.springpostgreshrservice.repository;

import com.jasonleetoronto.capstone.springpostgreshrservice.model.Salary;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.SalaryIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryJpaRepository extends JpaRepository<Salary, SalaryIdentity> {
}
