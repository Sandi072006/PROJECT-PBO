package com.mycompany.penilaian_mahasiswa;

public class KomponenQuiz extends KomponenNilai {

    public KomponenQuiz(double persen) {
        super("Quiz", persen);
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
        return "Quiz: " + getNilai() + " (" + getPersen() + "%)";
    }
}
