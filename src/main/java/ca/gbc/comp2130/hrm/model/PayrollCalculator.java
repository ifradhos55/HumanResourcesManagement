package ca.gbc.comp2130.hrm.model;

import java.time.LocalDate;

public class PayrollCalculator {

    private static final double TAX_RATE = 0.20; //base tax rate
    private static final double CPP_RATE = 0.0525; //cpp deduction rate
    private static final double EI_RATE = 0.0163; //ei deduction rate
    private static final double OVERTIME_MULTIPLIER = 1.5; //overtime pay multiplier

    public static PayrollRecord calculate(Employee employee,
                                          LocalDate start, LocalDate end,
                                          double hours, double overtime,
                                          double bonus) {

        double base = hours * employee.getHourlyRate(); //calculates base pay
        double ot = overtime * employee.getHourlyRate() * OVERTIME_MULTIPLIER; //calculates overtime pay
        double gross = base + ot + bonus; //calculates total gross pay

        double tax = gross * TAX_RATE; //calculates tax deduction
        double cpp = gross * CPP_RATE; //calculates cpp deduction
        double ei = gross * EI_RATE; //calculates ei deduction

        double net = gross - (tax + cpp + ei); //calculates final net pay

        return new PayrollRecord(
                employee.getId(), //employee id
                employee.getFullName(), //employee full name
                start, end, //payroll period
                hours, overtime, //hours worked and overtime hours
                gross, tax, cpp, ei, net //calculated pay details
        );
    }
}

