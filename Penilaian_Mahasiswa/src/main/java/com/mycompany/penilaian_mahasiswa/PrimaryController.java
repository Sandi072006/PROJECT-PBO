package com.mycompany.penilaian_mahasiswa;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.net.URL;

public class PrimaryController {
    
    @FXML
    private Button manageMahasiswaButton;
    @FXML
    private Button manageMataKuliahButton;
    @FXML
    private Button manageNilaiButton;
    @FXML
    private Button lihatNilaiAkhirButton;
    @FXML
    private Button logoutButton;
    
    @FXML
    public void manageMahasiswa() {
        try {
            navigateToPage("mahasiswa", "Kelola Data Mahasiswa", 800, 600);
        } catch (Exception e) {
            showError("Gagal membuka halaman Manage Mahasiswa", e);
        }
    }

    @FXML
    public void manageMataKuliah() {
        try {
            navigateToPage("matakuliah", "Kelola Data Mata Kuliah", 800, 600);
        } catch (Exception e) {
            showError("Gagal membuka halaman Manage Mata Kuliah", e);
        }
    }

    @FXML
    public void manageNilai() {
        System.out.println("=== MANAGE NILAI CLICKED ===");
        try {
            String fxmlPath = "/com/mycompany/penilaian_mahasiswa/manage_nilai.fxml";
            URL fxmlUrl = getClass().getResource(fxmlPath);
            
            System.out.println("FXML Path: " + fxmlPath);
            System.out.println("FXML URL: " + fxmlUrl);
            
            if (fxmlUrl == null) {
                showAlert("Info", 
                    "File manage_nilai.fxml tidak ditemukan.\n" +
                    "Mencoba membuka input_nilai.fxml...", 
                    Alert.AlertType.INFORMATION);
                
                openInputNilaiDirect();
                return;
            }
            
            navigateToPage("manage_nilai", "Kelola Nilai Mahasiswa", 900, 700);
            
        } catch (Exception e) {
            System.err.println("Error in manageNilai: " + e.getMessage());
            e.printStackTrace();
            showError("Gagal membuka halaman Manage Nilai", e);
        }
    }
  
    @FXML
    public void lihatNilaiAkhir() {
        System.out.println("=== LIHAT NILAI AKHIR CLICKED ===");
        
        try {
            String[] pathsToTry = {
                "/com/mycompany/penilaian_mahasiswa/nilai_akhir.fxml",
                "com/mycompany/penilaian_mahasiswa/nilai_akhir.fxml",
                "/nilai_akhir.fxml"
            };
            
            URL fxmlUrl = null;
            
            for (String path : pathsToTry) {
                System.out.println("Trying path: " + path);
                fxmlUrl = getClass().getResource(path);
                if (fxmlUrl != null) {
                    System.out.println("✓ Found at: " + fxmlUrl);
                    break;
                } else {
                    System.out.println("✗ Not found at: " + path);
                }
            }
          
            if (fxmlUrl == null) {
                System.out.println("Trying with ClassLoader...");
                fxmlUrl = getClass().getClassLoader().getResource("com/mycompany/penilaian_mahasiswa/nilai_akhir.fxml");
                if (fxmlUrl != null) {
                    System.out.println("✓ Found with ClassLoader: " + fxmlUrl);
                }
            }
            
            if (fxmlUrl == null) {
                String errorMsg = "File nilai_akhir.fxml tidak ditemukan!\n\n" +
                                "LANGKAH PERBAIKAN:\n" +
                                "1. Pastikan file ada di:\n" +
                                "   src/main/resources/com/mycompany/penilaian_mahasiswa/nilai_akhir.fxml\n\n" +
                                "2. Di NetBeans:\n" +
                                "   - Klik kanan project → Clean and Build\n" +
                                "   - Tunggu sampai selesai\n\n" +
                                "3. Cek folder target/classes/ apakah file sudah ter-copy\n\n" +
                                "4. Restart NetBeans dan coba lagi";
                
                showAlert("Error - File Tidak Ditemukan", errorMsg, Alert.AlertType.ERROR);
                return;
            }
         
            System.out.println("Loading FXML from: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            System.out.println("✓ FXML loaded successfully");
            
            Stage stage = getStageFromButtons();
            if (stage == null) {
                throw new IOException("Cannot get stage from buttons");
            }
            
            Scene scene = new Scene(root, 900, 650);
            stage.setScene(scene);
            stage.setTitle("Daftar Nilai Akhir Mahasiswa");
            stage.show();
            
            System.out.println("✓ Successfully navigated to Nilai Akhir page");
            
        } catch (IOException e) {
            System.err.println("IOException in lihatNilaiAkhir: " + e.getMessage());
            e.printStackTrace();
            showError("Gagal membuka halaman Nilai Akhir (IO Error)", e);
        } catch (Exception e) {
            System.err.println("Exception in lihatNilaiAkhir: " + e.getMessage());
            e.printStackTrace();
            showError("Gagal membuka halaman Nilai Akhir", e);
        }
    }
 
    @FXML
    public void handleLogout() {
        System.out.println("=== LOGOUT CLICKED ===");
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Logout");
        confirm.setHeaderText("Anda yakin ingin logout?");
        confirm.setContentText("Anda akan kembali ke halaman login.");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                System.out.println("Logging out admin...");
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/penilaian_mahasiswa/login.fxml")
                );
                Parent root = loader.load();
                
                Stage stage = getStageFromButtons();
                if (stage == null) {
                    throw new IOException("Cannot get stage from buttons");
                }
                
                Scene scene = new Scene(root, 600, 700);
                stage.setScene(scene);
                stage.setTitle("Login - Sistem Penilaian Mahasiswa");
                stage.show();
                
                System.out.println("✓ Successfully logged out");
                
            } catch (Exception e) {
                System.err.println("Error during logout: " + e.getMessage());
                e.printStackTrace();
                showError("Gagal logout", e);
            }
        }
    }
   
    private void openInputNilaiDirect() {
        try {
            String fxmlPath = "/com/mycompany/penilaian_mahasiswa/input_nilai.fxml";
            URL fxmlUrl = getClass().getResource(fxmlPath);
            
            if (fxmlUrl == null) {
                showAlert("Error", 
                    "File input_nilai.fxml juga tidak ditemukan!\n" +
                    "Pastikan file FXML ada di folder resources.", 
                    Alert.AlertType.ERROR);
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            InputNilaiController controller = loader.getController();
            controller.initData("DUMMY001", "Test Mahasiswa", "DUMMY01", "Test Matkul", 3, 3);
            
            Stage stage = (Stage) manageNilaiButton.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 700));
            stage.setTitle("Input Nilai - Testing");
            stage.show();
            
        } catch (Exception e) {
            showError("Gagal membuka input nilai", e);
        }
    }

    private void navigateToPage(String fxmlFile, String title, int width, int height) throws IOException {
        String fxmlPath = "/com/mycompany/penilaian_mahasiswa/" + fxmlFile + ".fxml";
        System.out.println("Attempting to load: " + fxmlPath);
        
        URL fxmlUrl = getClass().getResource(fxmlPath);
        System.out.println("Resource URL: " + fxmlUrl);
        
        if (fxmlUrl == null) {
            throw new IOException("Cannot find FXML file: " + fxmlPath);
        }
        
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Stage stage = getStageFromButtons();
        if (stage == null) {
            throw new IOException("Cannot get stage from buttons");
        }
        
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        
        System.out.println("Successfully loaded: " + title);
    }
  
    private Stage getStageFromButtons() {
        if (manageMahasiswaButton != null && manageMahasiswaButton.getScene() != null) {
            return (Stage) manageMahasiswaButton.getScene().getWindow();
        }
        if (manageMataKuliahButton != null && manageMataKuliahButton.getScene() != null) {
            return (Stage) manageMataKuliahButton.getScene().getWindow();
        }
        if (manageNilaiButton != null && manageNilaiButton.getScene() != null) {
            return (Stage) manageNilaiButton.getScene().getWindow();
        }
        if (lihatNilaiAkhirButton != null && lihatNilaiAkhirButton.getScene() != null) {
            return (Stage) lihatNilaiAkhirButton.getScene().getWindow();
        }
        if (logoutButton != null && logoutButton.getScene() != null) {
            return (Stage) logoutButton.getScene().getWindow();
        }
        return null;
    }

    private void showError(String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        
        String errorDetail = e.getMessage();
        if (errorDetail == null || errorDetail.isEmpty()) {
            errorDetail = e.getClass().getName();
        }
        
        alert.setContentText(errorDetail);
        alert.showAndWait();
        
        System.err.println("ERROR: " + message);
        e.printStackTrace();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
