package com.jasonleetoronto.capstone.springpostgreshrservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class SalaryIdentity implements Serializable {

    @NotNull
    @Column(name = "emp_no")
    private Long empNo;

    @NotNull
    @Column(name = "title")
    private LocalDate fromDate;

    protected SalaryIdentity() {
    }

    public SalaryIdentity(Long empNo, LocalDate fromDate) {
        super();
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public String toString() {
        return "SalaryIdentity{" +
                "empNo=" + empNo +
                ", fromDate=" + fromDate +
                '}';
    }
}
