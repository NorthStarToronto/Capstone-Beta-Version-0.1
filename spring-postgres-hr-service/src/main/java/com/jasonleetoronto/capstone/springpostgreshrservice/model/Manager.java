package com.jasonleetoronto.capstone.springpostgreshrservice.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
public class Manager {

    @EmbeddedId
    private ManagerIdentity managerId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    protected Manager() {

    }

    public Manager(ManagerIdentity managerId, LocalDate fromDate, LocalDate toDate) {
        super();
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "department=" + managerId.getDeptNo()+
                ", employee=" + managerId.getEmpNo() +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
