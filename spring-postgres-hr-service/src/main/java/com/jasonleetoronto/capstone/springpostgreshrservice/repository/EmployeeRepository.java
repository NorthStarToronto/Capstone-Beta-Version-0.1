package com.jasonleetoronto.capstone.springpostgreshrservice.repository;

import com.jasonleetoronto.capstone.springpostgreshrservice.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class EmployeeRepository {

    /* Create a logger */
    private static Logger LOG = LoggerFactory.getLogger(EmployeeRepository.class);

    /* Autowire in the entity manager to utilize persistence context api */
    @Autowired
    EntityManager em;

    public Employee findById(Long id) {
        return em.find(Employee.class, id);
    }

    public Employee save(Employee employee) {
        if (em.find(Employee.class, employee.getEmpNo()) == null) {
            em.persist(employee);
        } else {
            em.merge(employee);
        }
        return employee;
    }

    public void deleteById(Long id) {
        Employee employee = em.find(Employee.class, id);
        em.remove(employee);
    }
}
