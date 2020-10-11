package com.jasonleetoronto.capstone.springpostgreshrservice.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TitleId implements Serializable {

    private Long empNo;
    private LocalDate fromDate;
    private LocalDate toDate;

    public TitleId() {
    }

    public TitleId(Long empNo, String title, LocalDate fromDate, LocalDate toDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TitleId)) return false;

        TitleId titleId = (TitleId) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(empNo, titleId.empNo)
                .append(fromDate, titleId.fromDate)
                .append(toDate, titleId.toDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(empNo)
                .append(fromDate)
                .append(toDate)
                .toHashCode();
    }
}
