package com.jasonleetoronto.capstone.springpostgreshrservice.ManagerRepository;

import com.jasonleetoronto.capstone.springpostgreshrservice.SpringPostgresHrServiceApplication;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.Employee;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.Gender;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.Manager;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.ManagerIdentity;
import com.jasonleetoronto.capstone.springpostgreshrservice.repository.EmployeeRepository;
import com.jasonleetoronto.capstone.springpostgreshrservice.repository.ManagerJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/* Unit Test */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringPostgresHrServiceApplication.class) // spring context
class ManagerJpaRepositoryTest {

	private Logger LOG = LoggerFactory.getLogger(ManagerJpaRepositoryTest.class);

	@Autowired
	ManagerJpaRepository managerJpaRepository;

	@Test
	public void findById() {
		Optional<Manager> manager = managerJpaRepository.findById(new ManagerIdentity("d004",110420L));
		LOG.info(manager.get().toString());
		assertEquals("Manager{department=d004, employee=110420, fromDate=1996-08-30, toDate=9999-01-01}", manager.get().toString());
	}
}
