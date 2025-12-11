package com.mycompany.penilaian_mahasiswa;

public class NilaiAkhir {
    private String npm;
    private String namaMahasiswa;
    private String idMatkul;
    private String namaMatkul;
    private int sks;
    private float nilaiAkhirTotal;
    private String hurufMatkul;
    private float bobotMatkul;

    public NilaiAkhir(String npm, String namaMahasiswa, String idMatkul, String namaMatkul, 
                      int sks, float nilaiAkhirTotal, String hurufMatkul, float bobotMatkul) {
        this.npm = npm;
        this.namaMahasiswa = namaMahasiswa;
        this.idMatkul = idMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
        this.nilaiAkhirTotal = nilaiAkhirTotal;
        this.hurufMatkul = hurufMatkul;
        this.bobotMatkul = bobotMatkul;
    }

    // Getters
    public String getNpm() {
        return npm;
    }

    public String getNamaMahasiswa() {
        return namaMahasiswa;
    }

    public String getIdMatkul() {
        return idMatkul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public int getSks() {
        return sks;
    }

    public float getNilaiAkhirTotal() {
        return nilaiAkhirTotal;
    }

    public String getHurufMatkul() {
        return hurufMatkul;
    }

    public float getBobotMatkul() {
        return bobotMatkul;
    }

    // Setters
    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setNamaMahasiswa(String namaMahasiswa) {
        this.namaMahasiswa = namaMahasiswa;
    }

    public void setIdMatkul(String idMatkul) {
        this.idMatkul = idMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public void setSks(int sks) {
        this.sks = sks;
    }

    public void setNilaiAkhirTotal(float nilaiAkhirTotal) {
        this.nilaiAkhirTotal = nilaiAkhirTotal;
    }

    public void setHurufMatkul(String hurufMatkul) {
        this.hurufMatkul = hurufMatkul;
    }

    public void setBobotMatkul(float bobotMatkul) {
        this.bobotMatkul = bobotMatkul;
    }
}