package com.mycompany.penilaian_mahasiswa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db_penilaian"; 
        String username = "root"; 
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Koneksi berhasil!");
            } else {
                System.out.println("Koneksi gagal! Koneksi tidak terhubung.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
