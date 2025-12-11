package com.mycompany.penilaian_mahasiswa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller untuk halaman Login
 * Mengelola autentikasi admin dan user
 */
public class LoginController implements Initializable {
    
    @FXML private RadioButton adminRadio;
    @FXML private RadioButton userRadio;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;
    
    private ToggleGroup roleGroup;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setup ToggleGroup untuk radio buttons
        roleGroup = new ToggleGroup();
        adminRadio.setToggleGroup(roleGroup);
        userRadio.setToggleGroup(roleGroup);
        
        // Set default selection
        adminRadio.setSelected(true);
        
        // Clear error message saat user mengetik
        usernameField.textProperty().addListener((obs, old, newVal) -> errorLabel.setText(""));
        passwordField.textProperty().addListener((obs, old, newVal) -> errorLabel.setText(""));
        
        // Clear error saat role berubah
        roleGroup.selectedToggleProperty().addListener((obs, old, newVal) -> errorLabel.setText(""));
        
        // Enable login dengan Enter key
        passwordField.setOnAction(e -> handleLogin());
    }
    
    /**
     * Handler untuk tombol Login
     */
    @FXML
    public void handleLogin() {
        // Clear error message
        errorLabel.setText("");
        
        // Validasi input
        if (!validateInput()) {
            return;
        }
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Cek role yang dipilih
        if (adminRadio.isSelected()) {
            // Login sebagai Admin
            if (authenticateAdmin(username, password)) {
                navigateToMainMenu();
            } else {
                errorLabel.setText("❌ Username atau password Admin salah!");
            }
        } else if (userRadio.isSelected()) {
            // Login sebagai User
            if (authenticateUser(username, password)) {
                navigateToNilaiAkhir();
            } else {
                errorLabel.setText("❌ Username atau password User salah!");
            }
        }
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        // Cek apakah role sudah dipilih
        if (roleGroup.getSelectedToggle() == null) {
            errorLabel.setText("⚠️ Silakan pilih role terlebih dahulu!");
            return false;
        }
        
        // Cek username
        if (usernameField.getText().trim().isEmpty()) {
            errorLabel.setText("⚠️ Username tidak boleh kosong!");
            usernameField.requestFocus();
            return false;
        }
        
        // Cek password
        if (passwordField.getText().isEmpty()) {
            errorLabel.setText("⚠️ Password tidak boleh kosong!");
            passwordField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Autentikasi Admin dari database
     */
    private boolean authenticateAdmin(String username, String password) {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ Admin login berhasil: " + username);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error authenticating admin: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error Database", 
                     "Gagal mengakses database admin!\n" + e.getMessage(), 
                     Alert.AlertType.ERROR);
        }
        
        return false;
    }
    
    /**
     * Autentikasi User dari database
     */
    private boolean authenticateUser(String username, String password) {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ User login berhasil: " + username);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error Database", 
                     "Gagal mengakses database user!\n" + e.getMessage(), 
                     Alert.AlertType.ERROR);
        }
        
        return false;
    }
    
    /**
     * Navigasi ke halaman Main Menu (untuk Admin)
     */
    private void navigateToMainMenu() {
        try {
            System.out.println("Navigating to Main Menu...");
            
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mycompany/penilaian_mahasiswa/primary.fxml")
            );
            Parent root = loader.load();
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Manajemen Nilai Akademik - Admin");
            stage.show();
            
            System.out.println("✓ Successfully navigated to Main Menu");
            
        } catch (Exception e) {
            System.err.println("Error navigating to main menu: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", 
                     "Gagal membuka halaman utama!\n" + e.getMessage(), 
                     Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Navigasi ke halaman Nilai Akhir (untuk User)
     */
    private void navigateToNilaiAkhir() {
        try {
            System.out.println("Navigating to Nilai Akhir...");
            
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mycompany/penilaian_mahasiswa/nilai_akhir.fxml")
            );
            Parent root = loader.load();
            
            // Set user role di controller
            NilaiAkhirController controller = loader.getController();
            controller.setUserRole(true); // true = login sebagai User
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            stage.setScene(scene);
            stage.setTitle("Daftar Nilai Akhir Mahasiswa - User");
            stage.show();
            
            System.out.println("✓ Successfully navigated to Nilai Akhir");
            
        } catch (Exception e) {
            System.err.println("Error navigating to nilai akhir: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", 
                     "Gagal membuka halaman nilai akhir!\n" + e.getMessage(), 
                     Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Menampilkan alert dialog
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}