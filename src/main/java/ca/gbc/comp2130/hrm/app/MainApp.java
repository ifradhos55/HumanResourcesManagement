/*
*Group 40:
*Luis Tello 101580076
*John Sebastian Laquis 101591588
*Basira Zaki 101565577
*Ifrad Hossain 101587843
*/


package ca.gbc.comp2130.hrm.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    //Connecting the main application to the view
    @Override
    public void start(Stage stage) throws Exception {

        var fxmlUrl = ClassLoader.getSystemResource(
                "ca/gbc/comp2130/hrm/view/main-view.fxml"
        );

        if (fxmlUrl == null) {
            System.out.println("FXML NOT FOUND!");
            System.out.println("Expected path: ca/gbc/comp2130/hrm/view/main-view.fxml");
            return;
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);

        Scene scene = new Scene(loader.load(), 1000, 650);

        // stylesheet connection
        var cssUrl = ClassLoader.getSystemResource(
                "ca/gbc/comp2130/hrm/view/css/styles.css"
        );

        // If the css file isn't readable, invalid, or null
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("CSS NOT FOUND!");
        }

        //Program Title
        stage.setTitle("HRM & Payroll System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
