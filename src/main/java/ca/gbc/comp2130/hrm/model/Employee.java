package ca.gbc.comp2130.hrm.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String department;
    private double hourlyRate;
    private double standardHoursPerWeek;
    private LocalDate hireDate;
    private boolean active;

    public Employee(int id, String firstName, String lastName, String department,
                    double hourlyRate, double standardHoursPerWeek,
                    LocalDate hireDate, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.hourlyRate = hourlyRate;
        this.standardHoursPerWeek = standardHoursPerWeek;
        this.hireDate = hireDate;
        this.active = active;
    }

    // Getters + setters

    public int getId() {
        return id;
    } // returns employee id

    public void setId(int id) {
        this.id = id;
    } // sets employee id

    public String getFirstName() {
        return firstName;
    } // returns first name

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    } // sets first name

    public String getLastName() {
        return lastName;
    } // returns last name

    public void setLastName(String lastName) {
        this.lastName = lastName;
    } // sets last name

    public String getDepartment() {
        return department;
    } // returns department

    public void setDepartment(String department) {
        this.department = department;
    } // sets department

    public double getHourlyRate() {
        return hourlyRate;
    } // returns hourly rate

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    } // sets hourly rate

    public double getStandardHoursPerWeek() {
        return standardHoursPerWeek;
    } // returns weekly hours

    public void setStandardHoursPerWeek(double hours) {
        this.standardHoursPerWeek = hours;
    } // sets weekly hours

    public LocalDate getHireDate() {
        return hireDate;
    } // returns hire date

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    } // sets hire date

    public boolean isActive() {
        return active;
    } // returns active status

    public void setActive(boolean active) {
        this.active = active;
    } // sets active status

    public String getFullName() {
        return firstName + " " + lastName;
    } // returns full name

    @Override
    public String toString() {
        return id + " - " + getFullName(); // returns formatted identifier
    }
}