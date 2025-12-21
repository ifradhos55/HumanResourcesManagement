/*
 *Group 40:
 *Luis Tello 101580076
 *John Sebastian Laquis 101591588
 *Basira Zaki 101565577
 *Ifrad Hossain 101587843
 */

package ca.gbc.comp2130.hrm.controller;

import ca.gbc.comp2130.hrm.model.HRMRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    //These models handle the state of program based on the user's activity/actions
    @FXML private Label statusLabel;
    private final HRMRepository repo = HRMRepository.getInstance();

    //1. This one shows "Ready", displaying that the application is ready to be used
    @FXML
    private void initialize() { statusLabel.setText("Ready"); }

    //2. This one shows "Saved" when any changes or actions made within the application are saved by the user
    @FXML
    private void handleSaveAll() {
        repo.save();
        statusLabel.setText("Saved.");
    }

    //3. This model terminates the current iteration of the program and exits the application, usually after being saved
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
