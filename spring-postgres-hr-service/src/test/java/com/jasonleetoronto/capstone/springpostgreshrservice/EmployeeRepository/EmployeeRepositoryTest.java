package com.jasonleetoronto.capstone.springpostgreshrservice.EmployeeRepository;

import com.jasonleetoronto.capstone.springpostgreshrservice.SpringPostgresHrServiceApplication;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.Employee;
import com.jasonleetoronto.capstone.springpostgreshrservice.model.Gender;
import com.jasonleetoronto.capstone.springpostgreshrservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import static org.junit.Assert.*;

/* Unit Test */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringPostgresHrServiceApplication.class) // spring context
class EmployeeRepositoryTest {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void findById() {
		Employee employee = employeeRepository.findById(61850L);
		assertEquals("Magy", employee.getFirstName());
	}

	@Test
	@DirtiesContext
	public void deleteById() {
		employeeRepository.deleteById(61850L);
		assertEquals(null, employeeRepository.findById(61850L));
	}

	@Test
	@DirtiesContext
	public void insertEmployee() {
		LocalDate birthDate = LocalDate.of(2020,1,1);
		LocalDate hireDate = LocalDate.of(2020,2,1);

		Employee employee = new Employee(20200217L, birthDate, "Thank", "You", Gender.M, hireDate);
		employeeRepository.save(employee);
		assertEquals("Thank", employeeRepository.findById(20200217L).getFirstName());
	}

}
