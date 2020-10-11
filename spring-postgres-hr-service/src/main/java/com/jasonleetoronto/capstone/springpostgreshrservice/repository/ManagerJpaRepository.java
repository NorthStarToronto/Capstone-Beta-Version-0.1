package com.jasonleetoronto.capstone.springpostgreshrservice.repository;

import com.jasonleetoronto.capstone.springpostgreshrservice.model.Manager;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.ManagerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerJpaRepository extends JpaRepository<Manager, ManagerIdentity> {

    //List<Manager> findByManagerIdentityDepartmentNumber(String deptNo);
}
