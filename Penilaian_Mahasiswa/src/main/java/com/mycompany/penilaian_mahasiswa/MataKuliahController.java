package com.mycompany.penilaian_mahasiswa;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class MataKuliahController implements Initializable {
    @FXML
    private TextField idMatkulField;
    @FXML
    private TextField namaMatkulField;
    @FXML
    private TextField sksField;
    @FXML
    private TextField persentaseAbsensiField;
    @FXML
    private TextField persentaseTugasField;
    @FXML
    private TextField persentaseQuizField;
    @FXML
    private TextField persentaseUtsField;
    @FXML
    private TextField persentaseUasField;
    @FXML
    private TableView<MataKuliah> mataKuliahTable;
    @FXML
    private TextField persentaseResponsiField;  

    private ObservableList<MataKuliah> mataKuliahList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Controller MataKuliah siap!");
        
        TableColumn<MataKuliah, String> idMatkulColumn = new TableColumn<>("ID Mata Kuliah");
        idMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("idMatkul"));

        TableColumn<MataKuliah, String> namaMatkulColumn = new TableColumn<>("Nama Mata Kuliah");
        namaMatkulColumn.setCellValueFactory(new PropertyValueFactory<>("namaMatkul"));

        TableColumn<MataKuliah, Integer> sksColumn = new TableColumn<>("SKS");
        sksColumn.setCellValueFactory(new PropertyValueFactory<>("sks"));

        mataKuliahTable.getColumns().addAll(idMatkulColumn, namaMatkulColumn, sksColumn);
        mataKuliahTable.setItems(mataKuliahList);
    }

    @FXML
public void saveMataKuliah() {
    String idMatkul = idMatkulField.getText();
    String namaMatkul = namaMatkulField.getText();
    int sks = 0;
    float persentaseAbsensi = 0, persentaseTugas = 0, persentaseQuiz = 0, persentaseUts = 0, persentaseUas = 0;
    Float persentaseResponsi = null;

    if (idMatkul.isEmpty() || namaMatkul.isEmpty() || sksField.getText().isEmpty() || persentaseAbsensiField.getText().isEmpty() || 
        persentaseTugasField.getText().isEmpty() || persentaseQuizField.getText().isEmpty() || persentaseUtsField.getText().isEmpty() || 
        persentaseUasField.getText().isEmpty()) {
        showAlert("Error", "Harap lengkapi semua data mata kuliah.", AlertType.ERROR);
        return;
    }

    try {
        sks = Integer.parseInt(sksField.getText());
        persentaseAbsensi = Float.parseFloat(persentaseAbsensiField.getText());
        persentaseTugas = Float.parseFloat(persentaseTugasField.getText());
        persentaseQuiz = Float.parseFloat(persentaseQuizField.getText());
        persentaseUts = Float.parseFloat(persentaseUtsField.getText());
        persentaseUas = Float.parseFloat(persentaseUasField.getText());
        
        if (!persentaseResponsiField.getText().isEmpty()) {
            persentaseResponsi = Float.parseFloat(persentaseResponsiField.getText());
        } else {
            persentaseResponsi = null;
        }
    } catch (NumberFormatException e) {
        showAlert("Error", "Pastikan semua input angka sudah benar.", AlertType.ERROR);
        return;
    }
    MataKuliah mataKuliah = new MataKuliah(idMatkul, namaMatkul, sks);
    if (saveMataKuliahToDatabase(mataKuliah, persentaseAbsensi, persentaseTugas, persentaseQuiz, persentaseUts, persentaseUas, persentaseResponsi)) {
        mataKuliahList.add(mataKuliah); 
        showAlert("Success", "Mata kuliah " + mataKuliah.getNamaMatkul() + " berhasil ditambahkan.", AlertType.INFORMATION);
    } else {
        showAlert("Error", "Gagal menyimpan mata kuliah ke database.", AlertType.ERROR);
    }
}

private boolean saveMataKuliahToDatabase(MataKuliah mataKuliah, float persentaseAbsensi, float persentaseTugas, float persentaseQuiz, 
                                         float persentaseUts, float persentaseUas, Float persentaseResponsi) {
    try (Connection connection = KoneksiDB.getConnection()) {
        connection.setAutoCommit(false); 
        
        String queryMatkul = "INSERT INTO mata_kuliah (id_matkul, nama_matkul, sks) VALUES (?, ?, ?)";
        try (PreparedStatement pstMatkul = connection.prepareStatement(queryMatkul)) {
            pstMatkul.setString(1, mataKuliah.getIdMatkul());
            pstMatkul.setString(2, mataKuliah.getNamaMatkul());
            pstMatkul.setInt(3, mataKuliah.getSks());

            int resultMatkul = pstMatkul.executeUpdate();

            if (resultMatkul > 0) {
                String queryPersentase = "INSERT INTO persentase_matkul (id_matkul, persentase_absensi, persentase_tugas, persentase_quiz, persentase_uts, persentase_uas, persentase_responsi) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstPersentase = connection.prepareStatement(queryPersentase)) {
                    pstPersentase.setString(1, mataKuliah.getIdMatkul());
                    pstPersentase.setFloat(2, persentaseAbsensi);
                    pstPersentase.setFloat(3, persentaseTugas);
                    pstPersentase.setFloat(4, persentaseQuiz);
                    pstPersentase.setFloat(5, persentaseUts);
                    pstPersentase.setFloat(6, persentaseUas);
                    if (persentaseResponsi != null) {
                        pstPersentase.setFloat(7, persentaseResponsi); 
                    } else {
                        pstPersentase.setNull(7, java.sql.Types.FLOAT); 
                    }

                    int resultPersentase = pstPersentase.executeUpdate();
                    if (resultPersentase > 0) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    System.err.println("Error saving persentase mata kuliah: " + e.getMessage());
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            connection.rollback();
            System.err.println("Error saving mata kuliah to database: " + e.getMessage());
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
