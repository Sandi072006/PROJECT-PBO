package com.mycompany.penilaian_mahasiswa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageNilaiController implements Initializable {
    
    @FXML
    private ComboBox<String> mahasiswaComboBox;
    
    @FXML
    private ComboBox<String> mataKuliahComboBox;
    
    @FXML
    private Spinner<Integer> jumlahQuizSpinner;
    
    @FXML
    private Spinner<Integer> jumlahTugasSpinner;
    
    @FXML
    private Button simpanButton;
    
    @FXML
    private Button batalButton;
    
    private ObservableList<Mahasiswa> mahasiswaList = FXCollections.observableArrayList();
    private ObservableList<MataKuliah> mataKuliahList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("ManageNilaiController initialized");
        
        // Setup Spinners dengan nilai default
        try {
            SpinnerValueFactory<Integer> quizValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);
            jumlahQuizSpinner.setValueFactory(quizValueFactory);
            jumlahQuizSpinner.setEditable(true);
            
            SpinnerValueFactory<Integer> tugasValueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);
            jumlahTugasSpinner.setValueFactory(tugasValueFactory);
            jumlahTugasSpinner.setEditable(true);
            
            System.out.println("Spinners initialized successfully");
        } catch (Exception e) {
            System.err.println("Error initializing spinners: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Load data
        loadMahasiswaData();
        loadMataKuliahData();
    }
    
    private void loadMahasiswaData() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT npm, nama FROM mahasiswa ORDER BY nama";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            ObservableList<String> mahasiswaItems = FXCollections.observableArrayList();
            mahasiswaList.clear();
            
            while (rs.next()) {
                String npm = rs.getString("npm");
                String nama = rs.getString("nama");
                String item = npm + " - " + nama;
                mahasiswaItems.add(item);
                
                // Simpan ke list untuk referensi
                Mahasiswa mhs = new Mahasiswa(nama, npm, "", 0, "");
                mahasiswaList.add(mhs);
            }
            
            mahasiswaComboBox.setItems(mahasiswaItems);
            System.out.println("Loaded " + mahasiswaItems.size() + " mahasiswa");
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data mahasiswa: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void loadMataKuliahData() {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT id_matkul, nama_matkul, sks FROM mata_kuliah ORDER BY nama_matkul";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            ObservableList<String> matkulItems = FXCollections.observableArrayList();
            mataKuliahList.clear();
            
            while (rs.next()) {
                String idMatkul = rs.getString("id_matkul");
                String namaMatkul = rs.getString("nama_matkul");
                int sks = rs.getInt("sks");
                String item = idMatkul + " - " + namaMatkul + " (" + sks + " SKS)";
                matkulItems.add(item);
                
                // Simpan ke list untuk referensi
                MataKuliah mk = new MataKuliah(idMatkul, namaMatkul, sks);
                mataKuliahList.add(mk);
            }
            
            mataKuliahComboBox.setItems(matkulItems);
            System.out.println("Loaded " + matkulItems.size() + " mata kuliah");
            
        } catch (SQLException e) {
            showAlert("Error", "Gagal memuat data mata kuliah: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleSimpan() {
        System.out.println("=== handleSimpan called ===");
        
        // Validasi input
        if (mahasiswaComboBox.getValue() == null) {
            showAlert("Error", "Silakan pilih mahasiswa terlebih dahulu!", Alert.AlertType.ERROR);
            return;
        }
        
        if (mataKuliahComboBox.getValue() == null) {
            showAlert("Error", "Silakan pilih mata kuliah terlebih dahulu!", Alert.AlertType.ERROR);
            return;
        }
        
        // Ambil data yang dipilih
        int mahasiswaIndex = mahasiswaComboBox.getSelectionModel().getSelectedIndex();
        int matkulIndex = mataKuliahComboBox.getSelectionModel().getSelectedIndex();
        
        if (mahasiswaIndex < 0 || matkulIndex < 0) {
            showAlert("Error", "Data tidak valid!", Alert.AlertType.ERROR);
            return;
        }
        
        String npm = mahasiswaList.get(mahasiswaIndex).getNpm();
        String namaMahasiswa = mahasiswaList.get(mahasiswaIndex).getNama();
        String idMatkul = mataKuliahList.get(matkulIndex).getIdMatkul();
        String namaMatkul = mataKuliahList.get(matkulIndex).getNamaMatkul();
        
        int jumlahQuiz = jumlahQuizSpinner.getValue();
        int jumlahTugas = jumlahTugasSpinner.getValue();
        
        System.out.println("NPM: " + npm);
        System.out.println("Nama: " + namaMahasiswa);
        System.out.println("ID Matkul: " + idMatkul);
        System.out.println("Nama Matkul: " + namaMatkul);
        System.out.println("Jumlah Quiz: " + jumlahQuiz);
        System.out.println("Jumlah Tugas: " + jumlahTugas);
        
        // Cek apakah data sudah ada
        if (checkDataExists(npm, idMatkul)) {
            showAlert("Warning", "Data nilai untuk mahasiswa ini pada mata kuliah ini sudah ada!\nSilakan gunakan fitur Edit.", Alert.AlertType.WARNING);
            return;
        }
        
        // Pindah ke halaman Input Nilai
        try {
            System.out.println("Loading input_nilai.fxml...");
            
            // Coba berbagai cara untuk load FXML
            URL fxmlUrl = null;
            
            // Cara 1: Menggunakan getResource dari class
            fxmlUrl = getClass().getResource("/com/mycompany/penilaian_mahasiswa/input_nilai.fxml");
            System.out.println("Method 1 - URL: " + fxmlUrl);
            
            if (fxmlUrl == null) {
                // Cara 2: Tanpa leading slash
                fxmlUrl = getClass().getResource("input_nilai.fxml");
                System.out.println("Method 2 - URL: " + fxmlUrl);
            }
            
            if (fxmlUrl == null) {
                // Cara 3: Menggunakan ClassLoader
                fxmlUrl = getClass().getClassLoader().getResource("com/mycompany/penilaian_mahasiswa/input_nilai.fxml");
                System.out.println("Method 3 - URL: " + fxmlUrl);
            }
            
            if (fxmlUrl == null) {
                showAlert("Error", 
                    """
                    File input_nilai.fxml tidak ditemukan!
                    
                    Pastikan file ada di:
                    src/main/resources/com/mycompany/penilaian_mahasiswa/input_nilai.fxml
                    
                    Dan sudah di-rebuild project Maven.""", 
                    Alert.AlertType.ERROR);
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Pass data ke controller InputNilai
            InputNilaiController controller = loader.getController();
            if (controller == null) {
                showAlert("Error", "Controller InputNilaiController tidak ditemukan!", Alert.AlertType.ERROR);
                return;
            }
            
            controller.initData(npm, namaMahasiswa, idMatkul, namaMatkul, jumlahQuiz, jumlahTugas);
            
            Stage stage = (Stage) simpanButton.getScene().getWindow();
            Scene scene = new Scene(root, 900, 700);
            stage.setScene(scene);
            stage.setTitle("Input Nilai - " + namaMahasiswa + " - " + namaMatkul);
            
            System.out.println("Successfully navigated to input_nilai.fxml");
            
        } catch (Exception e) {
            System.err.println("Error loading input_nilai.fxml: " + e.getMessage());
            e.printStackTrace();
            
            showAlert("Error", 
                "Gagal membuka halaman input nilai!\n\n" +
                "Error: " + e.getMessage() + "\n\n" +
                "Cek console untuk detail error.", 
                Alert.AlertType.ERROR);
        }
    }
    
    private boolean checkDataExists(String npm, String idMatkul) {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT COUNT(*) as count FROM nilai_akhir_mahasiswa WHERE npm = ? AND id_matkul = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, npm);
            pst.setString(2, idMatkul);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking data: " + e.getMessage());
        }
        return false;
    }
    
    @FXML
    public void handleBatal() {
        System.out.println("handleBatal called");
        try {
            Stage stage = (Stage) batalButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/penilaian_mahasiswa/primary.fxml"));
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Manajemen Nilai Akademik");
        } catch (Exception e) {
            showAlert("Error", "Gagal kembali ke halaman utama: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
