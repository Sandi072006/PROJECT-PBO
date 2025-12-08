package com.mycompany.penilaian_mahasiswa;

import java.util.ArrayList;
import java.util.List;

public class Mahasiswa {

    private String nama;
    private String npm;
    private String jurusan;
    private int angkatan;
    private String prodi;
    private List<MataKuliah> listMatakuliah;
    private int jumlahMK = 0;
    public Mahasiswa(String nama, String npm, String jurusan, int angkatan, String prodi) {
        this.nama = nama;
        this.npm = npm;
        this.jurusan = jurusan;
        this.angkatan = angkatan;
        this.prodi = prodi;
        this.listMatakuliah = new ArrayList<>();
    }
    public void tambah(MataKuliah mk) {
        if (!listMatakuliah.contains(mk)) {
            listMatakuliah.add(mk);
            jumlahMK++;
        }
    }
    public int getJumlahMK() {
        return jumlahMK;
    }
    public List<MataKuliah> getListMatakuliah() {
        return listMatakuliah;
    }
    public String getNama() {
        return nama;
    }
    public String getNpm() {
        return npm;
    }
    public String getJurusan() {
        return jurusan;
    }
    public int getAngkatan() {
        return angkatan;
    }
    public String getProdi() {
        return prodi;
    }
    public double hitungIPK() {
        double totalBobot = 0;
        int totalSks = 0;
        for (MataKuliah mk : listMatakuliah) {
            totalBobot += mk.getBobot() * mk.getSks();
            totalSks += mk.getSks();
        }
        return (totalSks == 0) ? 0 : totalBobot / totalSks;
    }
    public void tampilkanInformasi() {
        System.out.println("Nama Mahasiswa: " + nama);
        System.out.println("NPM: " + npm);
        System.out.println("Jurusan: " + jurusan);
        System.out.println("Angkatan: " + angkatan);
        System.out.println("Program Studi: " + prodi);
        System.out.println("Daftar Mata Kuliah:");

        for (MataKuliah mk : listMatakuliah) {
            System.out.println("- " + mk.getNamaMatkul() + " (" + mk.getSks() + " SKS)");
        }
        System.out.println("IPK: " + hitungIPK());
    }

}
