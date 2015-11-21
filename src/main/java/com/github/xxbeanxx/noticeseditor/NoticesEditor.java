package com.github.xxbeanxx.noticeseditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Greg Baker
 */
public class NoticesEditor extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        final Parent root = FXMLLoader.load(getClass().getResource("/com/github/xxbeanxx/noticeseditor/FXMLDocument.fxml"));
        final Scene scene = new Scene(root);
        stage.setTitle("MSCA Notices Editor");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
	
}
