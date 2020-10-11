package com.jasonleetoronto.capstone.springpostgreshrservice.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salaries")
public class Salary {

    @EmbeddedId
    private SalaryIdentity salaryId;

    @Column(name = "salary")
    private Long salary;

    @Column(name = "to_date")
    private LocalDate toDate;

    protected Salary() {
    }

    public Salary(SalaryIdentity salaryId, Long salary, LocalDate toDate) {
        this.salary = salary;
        this.toDate = toDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "empNo=" + salaryId.getEmpNo() +
                ", salary=" + salary +
                ", fromDate=" + salaryId.getFromDate() +
                ", toDate=" + toDate +
                '}';
    }
}
