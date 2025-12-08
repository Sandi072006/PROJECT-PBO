package com.mycompany.penilaian_mahasiswa;

public class KomponenTugas extends KomponenNilai {

    public KomponenTugas(double persen) {
        super("Tugas", persen);
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
        return "Tugas: " + getNilai() + " (" + getPersen() + "%)";
    }
}
