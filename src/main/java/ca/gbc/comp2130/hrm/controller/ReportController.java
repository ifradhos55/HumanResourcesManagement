package ca.gbc.comp2130.hrm.controller;

import ca.gbc.comp2130.hrm.model.*;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class ReportController {

    @FXML private TableView<PayrollRecord> reportTable;
    @FXML private TableColumn<PayrollRecord, String> employeeColumn;
    @FXML private TableColumn<PayrollRecord, String> periodColumn;
    @FXML private TableColumn<PayrollRecord, Number> grossColumn;
    @FXML private TableColumn<PayrollRecord, Number> netColumn;

    @FXML private ProgressBar exportProgress;
    @FXML private Label exportStatusLabel;

    @FXML private LineChart<String, Number> incomeChart;
    @FXML private BarChart<String, Number> departmentChart;

    private final HRMRepository repo = HRMRepository.getInstance();

    @FXML
    private void initialize() {
        reportTable.setItems(repo.getPayrollRecords());

        employeeColumn.setCellValueFactory(c ->
                Bindings.createStringBinding(c.getValue()::getEmployeeName));

        periodColumn.setCellValueFactory(c ->
                Bindings.createStringBinding(() ->
                        c.getValue().getPeriodStart() + " to " + c.getValue().getPeriodEnd()));

        grossColumn.setCellValueFactory(c ->
                Bindings.createDoubleBinding(c.getValue()::getGrossPay));

        netColumn.setCellValueFactory(c ->
                Bindings.createDoubleBinding(c.getValue()::getNetPay));

        exportProgress.setProgress(0);
        exportStatusLabel.setText("");

        // Initial graph load
        loadIncomeChart();
        loadDepartmentChart();

        // Real-time updates
        repo.getPayrollRecords().addListener((javafx.collections.ListChangeListener<PayrollRecord>) c -> {
            loadIncomeChart();
            loadDepartmentChart();
        });

        repo.getEmployees().addListener((javafx.collections.ListChangeListener<Employee>) c -> {
            loadDepartmentChart();
        });
    }

    // Export report to csv
    @FXML
    private void handleExportReport() {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                updateMessage("Exporting...");
                Path dir = Paths.get("reports");
                Files.createDirectories(dir);

                Path csvFile = dir.resolve("payroll-report.csv");

                try (BufferedWriter w = Files.newBufferedWriter(csvFile)) {

                    // CSV HEADER
                    w.write("Name,Period,Gross Income,Net Income\n");

                    int total = repo.getPayrollRecords().size();
                    int i = 0;

                    for (PayrollRecord r : repo.getPayrollRecords()) {

                        String name = r.getEmployeeName();
                        String period = r.getPeriodStart() + " to " + r.getPeriodEnd();
                        double gross = r.getGrossPay();
                        double net = r.getNetPay();

                        // Write CSV row
                        w.write(name + "," + period + "," + gross + "," + net + "\n");

                        i++;
                        updateProgress(i, total == 0 ? 1 : total);
                    }
                }

                updateMessage("Exported → payroll-report.csv");
                return null;
            }
        };

        exportProgress.progressProperty().bind(task.progressProperty());
        exportStatusLabel.textProperty().bind(task.messageProperty());

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    // Open the csv file
    @FXML
    private void handleOpenReport() {
        File file = new File("reports/payroll-report.csv");

        if (!file.exists()) {
            new Alert(Alert.AlertType.ERROR, "Report not found. Export first.").show();
            return;
        }

        try {
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unable to open CSV file.").show();
        }
    }

    // Income line chart
    private void loadIncomeChart() {
        incomeChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Net Income");

        for (PayrollRecord r : repo.getPayrollRecords()) {
            String label = r.getPeriodStart().toString();
            series.getData().add(new XYChart.Data<>(label, r.getNetPay()));
        }

        incomeChart.getData().add(series);
    }

    // Department income bar chart
    private void loadDepartmentChart() {
        departmentChart.getData().clear();

        Map<String, Double> totals = new HashMap<>();

        for (PayrollRecord r : repo.getPayrollRecords()) {
            Employee employee = repo.getEmployees()
                    .stream()
                    .filter(e -> e.getId() == r.getEmployeeId())
                    .findFirst()
                    .orElse(null);

            if (employee == null) continue;

            String dept = employee.getDepartment();
            totals.put(dept, totals.getOrDefault(dept, 0.0) + r.getNetPay());
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Department Totals");

        for (var entry : totals.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        departmentChart.getData().add(series);
    }
}
