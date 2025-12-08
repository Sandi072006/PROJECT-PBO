package com.mycompany.penilaian_mahasiswa;

public class KomponenAbsensi extends KomponenNilai {

    public KomponenAbsensi(double persen) {
        super("Absensi", persen);
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
        return "Absensi: " + getNilai() + " (" + getPersen() + "%)";
    }
}
