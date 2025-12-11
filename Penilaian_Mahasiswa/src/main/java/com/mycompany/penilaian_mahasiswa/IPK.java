package com.mycompany.penilaian_mahasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class utility untuk perhitungan dan pengelolaan IPK Mahasiswa
 */
public class IPK {
    
    /**
     * Hitung IPK untuk satu mahasiswa berdasarkan NPM
     * @param npm NPM mahasiswa
     * @return true jika berhasil, false jika gagal
     */
    public static boolean calculateIPK(String npm) {
        try (Connection conn = KoneksiDB.getConnection()) {
            // Query untuk menghitung IPK
            String query = 
                "SELECT " +
                "    SUM(nam.bobot_matkul * mk.sks) as total_bobot_x_sks, " +
                "    SUM(mk.sks) as total_sks, " +
                "    COUNT(nam.id_matkul) as jumlah_matkul " +
                "FROM nilai_akhir_mahasiswa nam " +
                "JOIN mata_kuliah mk ON nam.id_matkul = mk.id_matkul " +
                "WHERE nam.npm = ?";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, npm);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                float totalBobotXSks = rs.getFloat("total_bobot_x_sks");
                int totalSks = rs.getInt("total_sks");
                int jumlahMatkul = rs.getInt("jumlah_matkul");
                
                // Hitung IPK
                float ipk = totalSks > 0 ? totalBobotXSks / totalSks : 0;
                
                // Simpan atau update IPK ke database
                return saveIPK(npm, totalBobotXSks, totalSks, ipk, jumlahMatkul);
            }
            
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error calculating IPK: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Simpan atau update IPK mahasiswa ke tabel ipk_mahasiswa
     */
    private static boolean saveIPK(String npm, float totalBobotXSks, int totalSks, 
                                   float ipk, int jumlahMatkul) {
        try (Connection conn = KoneksiDB.getConnection()) {
            // Check apakah data sudah ada
            String checkQuery = "SELECT npm FROM ipk_mahasiswa WHERE npm = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkQuery);
            checkPst.setString(1, npm);
            ResultSet checkRs = checkPst.executeQuery();
            
            String query;
            if (checkRs.next()) {
                // Update data yang sudah ada
                query = "UPDATE ipk_mahasiswa SET " +
                       "total_bobot_x_sks = ?, " +
                       "total_sks = ?, " +
                       "ipk = ?, " +
                       "jumlah_matkul = ? " +
                       "WHERE npm = ?";
            } else {
                // Insert data baru
                query = "INSERT INTO ipk_mahasiswa " +
                       "(npm, total_bobot_x_sks, total_sks, ipk, jumlah_matkul) " +
                       "VALUES (?, ?, ?, ?, ?)";
            }
            
            PreparedStatement pst = conn.prepareStatement(query);
            
            if (checkRs.next()) {
                // Parameter untuk UPDATE
                pst.setFloat(1, totalBobotXSks);
                pst.setInt(2, totalSks);
                pst.setFloat(3, ipk);
                pst.setInt(4, jumlahMatkul);
                pst.setString(5, npm);
            } else {
                // Parameter untuk INSERT
                pst.setString(1, npm);
                pst.setFloat(2, totalBobotXSks);
                pst.setInt(3, totalSks);
                pst.setFloat(4, ipk);
                pst.setInt(5, jumlahMatkul);
            }
            
            int result = pst.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving IPK: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Hitung ulang semua IPK untuk seluruh mahasiswa
     */
    public static void recalculateAllIPK() {
        try (Connection conn = KoneksiDB.getConnection()) {
            // Get semua NPM mahasiswa yang ada nilai akhirnya
            String query = "SELECT DISTINCT npm FROM nilai_akhir_mahasiswa";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                String npm = rs.getString("npm");
                if (calculateIPK(npm)) {
                    count++;
                }
            }
            
            System.out.println("Berhasil menghitung ulang IPK untuk " + count + " mahasiswa");
            
        } catch (SQLException e) {
            System.err.println("Error recalculating all IPK: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get IPK mahasiswa berdasarkan NPM
     * @param npm NPM mahasiswa
     * @return nilai IPK, atau 0 jika tidak ditemukan
     */
    public static float getIPK(String npm) {
        try (Connection conn = KoneksiDB.getConnection()) {
            String query = "SELECT ipk FROM ipk_mahasiswa WHERE npm = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, npm);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return rs.getFloat("ipk");
            }
            
            return 0;
            
        } catch (SQLException e) {
            System.err.println("Error getting IPK: " + e.getMessage());
            return 0;
        }
    }
}