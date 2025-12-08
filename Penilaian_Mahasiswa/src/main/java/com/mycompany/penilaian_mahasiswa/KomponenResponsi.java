package com.mycompany.penilaian_mahasiswa;

public class KomponenResponsi extends KomponenNilai {

    public KomponenResponsi(double persen) {
        super("Responsi", persen);
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
        return "Responsi: " + getNilai() + " (" + getPersen() + "%)";
    }
}
