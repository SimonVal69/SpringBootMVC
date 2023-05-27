package ru.skypro.lessons.springboot.springbootmvc.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportDTO implements Serializable {

    private String departmentName;

    private Long employeeCount;

    private int maxSalary;

    private int minSalary;

    private BigDecimal averageSalary;

    public ReportDTO() {}

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "departmentName='" + departmentName + '\'' +
                ", employeeCount=" + employeeCount +
                ", maxSalary=" + maxSalary +
                ", minSalary=" + minSalary +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
