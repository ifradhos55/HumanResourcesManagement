package ca.gbc.comp2130.hrm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class HRMRepository {
    // Serialized data file path
    private static final String FILE = "data/hrm-data.ser";
    private static final HRMRepository INSTANCE = new HRMRepository();
    // List of all employees
    private final ObservableList<Employee> employees =
            FXCollections.observableArrayList();
    // List of all payroll records
    private final ObservableList<PayrollRecord> payrolls =
            FXCollections.observableArrayList();
    // Private constructor loads saved data
    private HRMRepository() { load(); }

    public static HRMRepository getInstance() { return INSTANCE; }

    public ObservableList<Employee> getEmployees() { return employees; }
    public ObservableList<PayrollRecord> getPayrollRecords() { return payrolls; }
    // Adds an employee
    public void addEmployee(Employee e) { employees.add(e); }
    // Removes an employee and related payroll entries
    public void removeEmployee(Employee e) {
        employees.remove(e);
        payrolls.removeIf(r -> r.getEmployeeId() == e.getId());
    }

    public void addPayrollRecord(PayrollRecord r) { payrolls.add(r); }

    public void save() {
        try {
            Path path = Paths.get(FILE);
            Files.createDirectories(path.getParent());

            try (ObjectOutputStream out =
                         new ObjectOutputStream(
                                 new BufferedOutputStream(
                                         Files.newOutputStream(path)))) {
                out.writeObject(new ArrayList<>(employees));
                out.writeObject(new ArrayList<>(payrolls));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    private void load() {
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return;

        try (ObjectInputStream in =
                     new ObjectInputStream(
                             new BufferedInputStream(
                                     Files.newInputStream(path)))) {

            employees.setAll((List<Employee>) in.readObject());
            payrolls.setAll((List<PayrollRecord>) in.readObject());

        } catch (Exception e) { e.printStackTrace(); }
    }
}
