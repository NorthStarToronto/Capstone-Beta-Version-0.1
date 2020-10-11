package com.jasonleetoronto.capstone.springpostgreshrservice.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class ManagerIdentity implements Serializable {

    @NotNull
    @Column(name = "dept_no")
    private String deptNo;

    @NotNull
    @Column(name = "emp_no")
    private Long empNo;

    protected ManagerIdentity() {
    }

    public ManagerIdentity(String deptNo, Long empNo) {
        super();
        this.deptNo = deptNo;
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Long getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Long empNo) {
        this.empNo = empNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ManagerIdentity)) return false;

        ManagerIdentity managerId = (ManagerIdentity) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(deptNo, managerId.deptNo)
                .append(empNo, managerId.empNo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(deptNo)
                .append(empNo)
                .toHashCode();
    }
}
