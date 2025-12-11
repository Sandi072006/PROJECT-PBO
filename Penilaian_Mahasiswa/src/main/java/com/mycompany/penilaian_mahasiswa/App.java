package com.mycompany.penilaian_mahasiswa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/com/mycompany/penilaian_mahasiswa/login.fxml")
        );
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 600, 700);
        stage.setScene(scene);
        stage.setTitle("Login - Sistem Penilaian Mahasiswa");
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
