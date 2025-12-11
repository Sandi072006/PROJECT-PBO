
package com.mycompany.penilaian_mahasiswa;

/**
 * Class untuk menyimpan data IPK Mahasiswa
 * Digunakan untuk menampilkan data di TableView
 */
public class IPKMahasiswa {
    private String npm;
    private String namaMahasiswa;
    private float totalBobotXSks;
    private int totalSks;
    private float ipk;
    private int jumlahMatkul;

    // Constructor
    public IPKMahasiswa(String npm, String namaMahasiswa, float totalBobotXSks, 
                        int totalSks, float ipk, int jumlahMatkul) {
        this.npm = npm;
        this.namaMahasiswa = namaMahasiswa;
        this.totalBobotXSks = totalBobotXSks;
        this.totalSks = totalSks;
        this.ipk = ipk;
        this.jumlahMatkul = jumlahMatkul;
    }

    // Getters
    public String getNpm() {
        return npm;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public float getTotalBobotXSks() {
        return totalBobotXSks;
    }

    public int getTotalSks() {
        return totalSks;
    }

    public float getIpk() {
        return ipk;
    }

    public int getJumlahMatkul() {
        return jumlahMatkul;
    }

    // Setters
    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public void setTotalBobotXSks(float totalBobotXSks) {
        this.totalBobotXSks = totalBobotXSks;
    }

    public void setTotalSks(int totalSks) {
        this.totalSks = totalSks;
    }

    public void setIpk(float ipk) {
        this.ipk = ipk;
    }

    public void setJumlahMatkul(int jumlahMatkul) {
        this.jumlahMatkul = jumlahMatkul;
    }

    @Override
    public String toString() {
        return "IPKMahasiswa{" +
                "npm='" + npm + '\'' +
                ", namaMahasiswa='" + namaMahasiswa + '\'' +
                ", totalBobotXSks=" + totalBobotXSks +
                ", totalSks=" + totalSks +
                ", ipk=" + ipk +
                ", jumlahMatkul=" + jumlahMatkul +
                '}';
    }
}