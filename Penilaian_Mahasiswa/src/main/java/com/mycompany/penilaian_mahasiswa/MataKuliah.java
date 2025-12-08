package com.mycompany.penilaian_mahasiswa;

public class MataKuliah {
    private String idMatkul;
    private String namaMatkul;
    private int sks;
    private float persentaseAbsensi;
    private float persentaseTugas;
    private float persentaseQuiz;
    private float persentaseUts;
    private float persentaseUas;
    private float bobot;
    private float persentaseResponsi;

    public MataKuliah(String idMatkul, String namaMatkul, int sks) {
        this.idMatkul = idMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
    }
    public String getIdMatkul() {
        return idMatkul;
    }
    public void setIdMatkul(String idMatkul) {
        this.idMatkul = idMatkul;
    }
    public String getNamaMatkul() {
        return namaMatkul;
    }
    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }
    public int getSks() {
        return sks;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }
    public float getPersentaseAbsensi() {
        return persentaseAbsensi;
    }
     public Float getPersentaseResponsi() {
        return persentaseResponsi;
    }
    public void setPersentaseAbsensi(float persentaseAbsensi) {
        this.persentaseAbsensi = persentaseAbsensi;
    }
    public float getPersentaseTugas() {
        return persentaseTugas;
    }
    public void setPersentaseTugas(float persentaseTugas) {
        this.persentaseTugas = persentaseTugas;
    }
    public float getPersentaseQuiz() {
        return persentaseQuiz;
    }
    public void setPersentaseQuiz(float persentaseQuiz) {
        this.persentaseQuiz = persentaseQuiz;
    }
    public float getPersentaseUts() {
        return persentaseUts;
    }
    public void setPersentaseUts(float persentaseUts) {
        this.persentaseUts = persentaseUts;
    }
    public float getPersentaseUas() {
        return persentaseUas;
    }
    public void setPersentaseUas(float persentaseUas) {
        this.persentaseUas = persentaseUas;
    }
    
    public float getBobot() {
        return bobot;
    }

    public void setBobot(float bobot) {
        this.bobot = bobot;
    }

    public void setPersentaseResponsi(Float persentaseResponsi) {
        this.persentaseResponsi = persentaseResponsi;
    }

    @Override
    public String toString() {
        return "MataKuliah{" +
                "idMatkul='" + idMatkul + '\'' +
                ", namaMatkul='" + namaMatkul + '\'' +
                ", sks=" + sks +
                ", persentaseAbsensi=" + persentaseAbsensi +
                ", persentaseTugas=" + persentaseTugas +
                ", persentaseQuiz=" + persentaseQuiz +
                ", persentaseUts=" + persentaseUts +
                ", persentaseUas=" + persentaseUas +
                '}';
    }
}
