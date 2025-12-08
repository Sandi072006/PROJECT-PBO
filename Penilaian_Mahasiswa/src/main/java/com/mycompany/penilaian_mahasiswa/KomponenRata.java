package com.mycompany.penilaian_mahasiswa;

public class KomponenRata extends KomponenNilai {

    private double totalTugas;
    private double totalQuiz;
    private int jumlahTugas;
    private int jumlahQuiz;
    
    public KomponenRata(String nama, double persen) {
        super(nama, persen);
        this.totalTugas = 0;
        this.totalQuiz = 0;
        this.jumlahTugas = 0;
        this.jumlahQuiz = 0;
    }
    
    public void tambahNilaiTugas(double nilaiTugas) {
        this.totalTugas += nilaiTugas;
        this.jumlahTugas++;
    }

    public void tambahNilaiQuiz(double nilaiQuiz) {
        this.totalQuiz += nilaiQuiz;
        this.jumlahQuiz++;
    }

    @Override
    public void setNilai(double nilai) {
        double rataTugas = this.jumlahTugas > 0 ? this.totalTugas / this.jumlahTugas : 0;
        double rataQuiz = this.jumlahQuiz > 0 ? this.totalQuiz / this.jumlahQuiz : 0;

        this.nilai = (rataTugas + rataQuiz) / 2;
    }

    @Override
    public double hitungNilaiAkhir() {
        return nilai * (persen / 100); 
    }

    @Override
    public String toString() {
        return "Rata-rata: Tugas = " + totalTugas / jumlahTugas + ", Quiz = " + totalQuiz / jumlahQuiz + " (" + getPersen() + "%)";
    }
}
