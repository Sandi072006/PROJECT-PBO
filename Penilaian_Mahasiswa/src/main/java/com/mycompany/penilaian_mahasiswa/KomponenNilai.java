package com.mycompany.penilaian_mahasiswa;

public abstract class KomponenNilai {

    protected String nama;
    protected double nilai;
    protected double persen;

    public KomponenNilai(String nama, double persen) {
        this.nama = nama;
        this.persen = persen;
    }
    
    public abstract void setNilai(double nilai);
    public abstract double hitungNilaiAkhir();

    public String getNama() {
        return nama;
    }

    public double getNilai() {
        return nilai;
    }
    public double getPersen() {
        return persen;
    }
}
