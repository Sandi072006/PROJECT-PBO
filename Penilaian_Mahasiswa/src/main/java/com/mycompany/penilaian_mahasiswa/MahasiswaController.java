package com.mycompany.penilaian_mahasiswa;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MahasiswaController implements Initializable {

    @FXML
    private TextField npmField;
    @FXML
    private TextField namaField;
    @FXML
    private TextField jurusanField;
    @FXML
    private TextField angkatanField;
    @FXML
    private TextField prodiField;
    @FXML
    private TableView<Mahasiswa> mahasiswaTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Controller Mahasiswa siap!");
    }

    @FXML
    public void saveMahasiswa() {
        String npm = npmField.getText();
        String nama = namaField.getText();
        String jurusan = jurusanField.getText();
        int angkatan = 0;
        String prodi = prodiField.getText();

        if (npm.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || prodi.isEmpty() || angkatanField.getText().isEmpty()) {
            showAlert("Error", "Harap lengkapi semua data mahasiswa.", AlertType.ERROR);
            return;
        }

        try {
            angkatan = Integer.parseInt(angkatanField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Angkatan harus berupa angka.", AlertType.ERROR);
            return;
        }
        Mahasiswa mahasiswa = new Mahasiswa(nama, npm, jurusan, angkatan, prodi);

        if (saveMahasiswaToDatabase(mahasiswa)) {
            mahasiswaTable.getItems().add(mahasiswa); // Menambahkan mahasiswa ke TableView
            showAlert("Success", "Mahasiswa " + mahasiswa.getNama() + " berhasil ditambahkan.", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Gagal menyimpan data mahasiswa ke database.", AlertType.ERROR);
        }
    }
    private boolean saveMahasiswaToDatabase(Mahasiswa mahasiswa) {
    try (Connection connection = KoneksiDB.getConnection()) {
        connection.setAutoCommit(false);
        
        String query = "INSERT INTO mahasiswa (npm, nama, jurusan, angkatan, program_studi) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, mahasiswa.getNpm());
            pst.setString(2, mahasiswa.getNama());
            pst.setString(3, mahasiswa.getJurusan());
            pst.setInt(4, mahasiswa.getAngkatan());
            pst.setString(5, mahasiswa.getProdi());
            
            int result = pst.executeUpdate();
            System.out.println("Hasil executeUpdate: " + result); 

            if (result > 0) {
                connection.commit(); 
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            connection.rollback(); 
            System.err.println("Error saving mahasiswa to database: " + e.getMessage());
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Error connecting to the database: " + e.getMessage());
        return false;
    }
}
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
