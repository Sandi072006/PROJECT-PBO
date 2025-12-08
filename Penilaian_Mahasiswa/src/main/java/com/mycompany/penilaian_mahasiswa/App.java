package com.mycompany.penilaian_mahasiswa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/penilaian_mahasiswa/primary.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Manajemen Nilai Akademik");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
