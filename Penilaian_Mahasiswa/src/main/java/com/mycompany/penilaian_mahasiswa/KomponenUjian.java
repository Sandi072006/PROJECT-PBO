package com.mycompany.penilaian_mahasiswa;

public class KomponenUjian extends KomponenNilai {
    public KomponenUjian(double persen) {
        super("Ujian", persen);
    }
    @Override
    public void setNilai(double nilai) {
        this.nilai = nilai;
    }
    @Override
    public double hitungNilaiAkhir() {
        return nilai * persen / 100;
    }
    @Override
    public String toString() {
        return "Ujian: " + getNilai() + " (" + getPersen() + "%)";
    }
}
