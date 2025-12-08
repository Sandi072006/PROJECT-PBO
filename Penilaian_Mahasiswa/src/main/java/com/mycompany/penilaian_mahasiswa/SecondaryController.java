package com.mycompany.penilaian_mahasiswa;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SecondaryController implements Initializable {
    @FXML
    private TableView<Mahasiswa> mahasiswaTable;
    @FXML
    private TableColumn<Mahasiswa, String> npmColumn;
    @FXML
    private TableColumn<Mahasiswa, String> namaColumn;
    @FXML
    private TableColumn<Mahasiswa, String> jurusanColumn;
    @FXML
    private TableColumn<Mahasiswa, Integer> angkatanColumn;
    @FXML
    private TableColumn<Mahasiswa, String> prodiColumn;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        npmColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNpm()));
        namaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNama()));
        jurusanColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getJurusan()));
        angkatanColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAngkatan()).asObject());
        prodiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProdi()));
        loadDataMahasiswa();
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
            mahasiswaTable.getItems().add(mahasiswa);
            showAlert("Success", "Mahasiswa " + mahasiswa.getNama() + " berhasil ditambahkan.", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Gagal menyimpan data mahasiswa ke database.", AlertType.ERROR);
        }
    }

    private boolean saveMahasiswaToDatabase(Mahasiswa mahasiswa) {
        try (Connection connection = KoneksiDB.getConnection()) {
            String query = "INSERT INTO mahasiswa (npm, nama, jurusan, angkatan, program_studi) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, mahasiswa.getNpm());
                pst.setString(2, mahasiswa.getNama());
                pst.setString(3, mahasiswa.getJurusan());
                pst.setInt(4, mahasiswa.getAngkatan());
                pst.setString(5, mahasiswa.getProdi());
                
                int result = pst.executeUpdate();
                System.out.println("Hasil executeUpdate: " + result);  // Debugging

                return result > 0; 
            }
        } catch (SQLException e) {
            System.err.println("Error saving mahasiswa to database: " + e.getMessage());
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

    private void loadDataMahasiswa() {
        try (Connection connection = KoneksiDB.getConnection()) {
            String query = "SELECT * FROM mahasiswa";
            try (PreparedStatement pst = connection.prepareStatement(query);
                 java.sql.ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    String npm = rs.getString("npm");
                    String nama = rs.getString("nama");
                    String jurusan = rs.getString("jurusan");
                    int angkatan = rs.getInt("angkatan");
                    String prodi = rs.getString("program_studi");
                    Mahasiswa mahasiswa = new Mahasiswa(nama, npm, jurusan, angkatan, prodi);
                    mahasiswaTable.getItems().add(mahasiswa);
                }
            }
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data mahasiswa: " + e.getMessage(), AlertType.ERROR);
        }
    }
}
