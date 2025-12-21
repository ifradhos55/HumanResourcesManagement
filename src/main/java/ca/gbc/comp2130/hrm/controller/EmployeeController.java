package ca.gbc.comp2130.hrm.controller;

import ca.gbc.comp2130.hrm.model.Employee;
import ca.gbc.comp2130.hrm.model.HRMRepository;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;

public class EmployeeController {

    @FXML private TextField idField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> departmentCombo;
    @FXML private TextField hourlyRateField;
    @FXML private TextField hoursPerWeekField;
    @FXML private TextField hireDateField;
    @FXML private CheckBox activeCheckBox;

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Number> idColumn;
    @FXML private TableColumn<Employee, String> nameColumn;
    @FXML private TableColumn<Employee, String> departmentColumn;
    @FXML private TableColumn<Employee, Number> rateColumn;

    private final HRMRepository repo = HRMRepository.getInstance();

    @FXML
    private void initialize() {
        ObservableList<Employee> data = repo.getEmployees();
        employeeTable.setItems(data);

        idColumn.setCellValueFactory(c -> Bindings.createIntegerBinding(c.getValue()::getId));
        nameColumn.setCellValueFactory(c -> Bindings.createStringBinding(c.getValue()::getFullName));
        departmentColumn.setCellValueFactory(c -> Bindings.createStringBinding(c.getValue()::getDepartment));
        rateColumn.setCellValueFactory(c -> Bindings.createDoubleBinding(c.getValue()::getHourlyRate));

        departmentCombo.getItems().addAll("HR", "IT", "Finance", "Sales", "Operations");

        employeeTable.setOnMouseClicked(this::handleTableClick);
    }

    @FXML
    private void handleAddEmployee() {
        try {
            Employee e = new Employee(
                    Integer.parseInt(idField.getText()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    departmentCombo.getValue(),
                    Double.parseDouble(hourlyRateField.getText()),
                    Double.parseDouble(hoursPerWeekField.getText()),
                    LocalDate.parse(hireDateField.getText()),
                    activeCheckBox.isSelected()
            );
            repo.addEmployee(e);
            clear();
        } catch (Exception ex) {
            showError();
        }
    }

    @FXML
    private void handleUpdateEmployee() {
        Employee e = employeeTable.getSelectionModel().getSelectedItem();
        if (e == null) return;

        try {
            e.setId(Integer.parseInt(idField.getText()));
            e.setFirstName(firstNameField.getText());
            e.setLastName(lastNameField.getText());
            e.setDepartment(departmentCombo.getValue());
            e.setHourlyRate(Double.parseDouble(hourlyRateField.getText()));
            e.setStandardHoursPerWeek(Double.parseDouble(hoursPerWeekField.getText()));
            e.setHireDate(LocalDate.parse(hireDateField.getText()));
            e.setActive(activeCheckBox.isSelected());

            employeeTable.refresh();
        } catch (Exception ex) {
            showError();
        }
    }

    @FXML
    private void handleDeleteEmployee() {
        Employee e = employeeTable.getSelectionModel().getSelectedItem();
        if (e != null) {
            repo.removeEmployee(e);
            clear();
        }
    }

    private void handleTableClick(MouseEvent e) {

    }

    private void clear() {
        idField.clear();
        firstNameField.clear();
        lastNameField.clear();
        departmentCombo.getSelectionModel().clearSelection();
        hourlyRateField.clear();
        hoursPerWeekField.clear();
        hireDateField.clear();
        activeCheckBox.setSelected(true);
    }

    private void showError() {
        new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
    }

    public void handleClear(ActionEvent actionEvent) {
    }
}
