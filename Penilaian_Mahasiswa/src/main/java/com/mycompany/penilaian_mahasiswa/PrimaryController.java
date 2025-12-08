package com.mycompany.penilaian_mahasiswa;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PrimaryController {

    @FXML
    private Button manageMahasiswaButton;
    @FXML
    private Button manageMataKuliahButton;
    @FXML
    private Button manageNilaiButton;
    @FXML
    public void manageMahasiswa() throws Exception {
        Stage stage = (Stage) manageMahasiswaButton.getScene().getWindow();
        Parent root = loadFXML("mahasiswa");
        stage.setScene(new Scene(root, 800, 600));
    }
    @FXML
    public void manageMataKuliah() throws Exception {
        Stage stage = (Stage) manageMataKuliahButton.getScene().getWindow();
        Parent root = loadFXML("matakuliah");
        stage.setScene(new Scene(root, 800, 600));
    }
    @FXML
    public void manageNilai() throws Exception {
        Stage stage = (Stage) manageNilaiButton.getScene().getWindow();
        Parent root = loadFXML("nilai");
        stage.setScene(new Scene(root, 800, 600));
    }
    private Parent loadFXML(String fxml) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/penilaian_mahasiswa/" + fxml + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxml);
            throw new Exception("FXML file not found: " + fxml);
        }
    }
}
