package ca.gbc.comp2130.hrm.controller;

import ca.gbc.comp2130.hrm.model.Employee;
import ca.gbc.comp2130.hrm.model.HRMRepository;
import ca.gbc.comp2130.hrm.model.PayrollCalculator;
import ca.gbc.comp2130.hrm.model.PayrollRecord;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PayrollController {

    @FXML private ComboBox<Employee> employeeCombo;
    @FXML private TextField periodStartField;
    @FXML private TextField periodEndField;
    @FXML private TextField hoursWorkedField;
    @FXML private TextField overtimeHoursField;
    @FXML private TextField bonusField;

    @FXML private TableView<PayrollRecord> payrollTable;
    @FXML private TableColumn<PayrollRecord, String> empNameColumn;
    @FXML private TableColumn<PayrollRecord, String> periodColumn;
    @FXML private TableColumn<PayrollRecord, Number> grossColumn;
    @FXML private TableColumn<PayrollRecord, Number> netColumn;

    @FXML private Label summaryLabel;

    private final HRMRepository repo = HRMRepository.getInstance();

    @FXML
    private void initialize() {
        employeeCombo.setItems(repo.getEmployees());
        payrollTable.setItems(repo.getPayrollRecords());

        empNameColumn.setCellValueFactory(c -> Bindings.createStringBinding(c.getValue()::getEmployeeName));
        periodColumn.setCellValueFactory(c -> Bindings.createStringBinding(() ->
                c.getValue().getPeriodStart() + " to " + c.getValue().getPeriodEnd()));
        grossColumn.setCellValueFactory(c -> Bindings.createDoubleBinding(c.getValue()::getGrossPay));
        netColumn.setCellValueFactory(c -> Bindings.createDoubleBinding(c.getValue()::getNetPay));
    }

    @FXML
    private void handleCalculatePayroll() {
        try {
            Employee e = employeeCombo.getValue();
            LocalDate start = LocalDate.parse(periodStartField.getText());
            LocalDate end = LocalDate.parse(periodEndField.getText());
            double hours = Double.parseDouble(hoursWorkedField.getText());
            double overtime = Double.parseDouble(overtimeHoursField.getText());
            double bonus = Double.parseDouble(bonusField.getText());

            PayrollRecord r = PayrollCalculator.calculate(e, start, end, hours, overtime, bonus);
            repo.addPayrollRecord(r);

            summaryLabel.setText("Latest net pay: $" + r.getNetPay());
            clear();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
        }
    }

    private void clear() {
        employeeCombo.getSelectionModel().clearSelection();
        periodStartField.clear();
        periodEndField.clear();
        hoursWorkedField.clear();
        overtimeHoursField.clear();
        bonusField.setText("0.0");
    }

    public void handleClear(ActionEvent actionEvent) {
    }
}
