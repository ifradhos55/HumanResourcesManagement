package ca.gbc.comp2130.hrm.model;

import java.io.Serializable;
import java.time.LocalDate;

public class PayrollRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private int employeeId;
    private String employeeName;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private double hoursWorked;
    private double overtimeHours;
    private double grossPay;
    private double tax;
    private double cpp;
    private double ei;
    private double netPay;

    public PayrollRecord(int employeeId, String employeeName,
                         LocalDate periodStart, LocalDate periodEnd,
                         double hoursWorked, double overtimeHours,
                         double grossPay, double tax, double cpp,
                         double ei, double netPay) {

        this.employeeId = employeeId; // sets employee id
        this.employeeName = employeeName; // sets employee name
        this.periodStart = periodStart; // sets start date
        this.periodEnd = periodEnd; // sets end date
        this.hoursWorked = hoursWorked; // sets hours worked
        this.overtimeHours = overtimeHours; // sets overtime hours
        this.grossPay = grossPay; // sets gross pay
        this.tax = tax; // sets tax
        this.cpp = cpp; // sets cpp
        this.ei = ei; // sets ei
        this.netPay = netPay; // sets net pay
    }

    // Getters only
    public int getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public LocalDate getPeriodStart() { return periodStart; }
    public LocalDate getPeriodEnd() { return periodEnd; }
    public double getHoursWorked() { return hoursWorked; }
    public double getOvertimeHours() { return overtimeHours; }
    public double getGrossPay() { return grossPay; }
    public double getTax() { return tax; }
    public double getCpp() { return cpp; }
    public double getEi() { return ei; }
    public double getNetPay() { return netPay; }
}
