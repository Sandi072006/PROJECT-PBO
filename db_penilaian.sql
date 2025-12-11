SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
SET NAMES utf8mb4;

CREATE TABLE mahasiswa (
    npm             VARCHAR(20)  NOT NULL,
    nama            VARCHAR(100) NOT NULL,
    jurusan         VARCHAR(100) NOT NULL,
    angkatan        INT          NOT NULL,
    program_studi   VARCHAR(100) NOT NULL,
    PRIMARY KEY (npm)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE mata_kuliah (
    id_matkul   INT NOT NULL AUTO_INCREMENT,
    nama_matkul VARCHAR(100) NOT NULL,
    sks         INT NOT NULL,
    PRIMARY KEY (id_matkul)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE absensi (
    id_absensi          INT NOT NULL AUTO_INCREMENT,
    id_matkul           INT NOT NULL,
    npm                 VARCHAR(20) NOT NULL,
    jumlah_pertemuan    INT NOT NULL,
    jumlah_hadir        INT NOT NULL,
    nilai_absensi       FLOAT DEFAULT NULL,
    nilai_akhir_absensi FLOAT DEFAULT NULL,
    PRIMARY KEY (id_absensi),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT absensi_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT absensi_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_uts (
    id_uts     INT NOT NULL AUTO_INCREMENT,
    id_matkul  INT NOT NULL,
    npm        VARCHAR(20) NOT NULL,
    nilai_uts  FLOAT NOT NULL,
    PRIMARY KEY (id_uts),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT nilai_uts_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_uts_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_uas (
    id_uas     INT NOT NULL AUTO_INCREMENT,
    id_matkul  INT NOT NULL,
    npm        VARCHAR(20) NOT NULL,
    nilai_uas  FLOAT NOT NULL,
    PRIMARY KEY (id_uas),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT nilai_uas_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_uas_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_quiz (
    id_quiz         INT NOT NULL AUTO_INCREMENT,
    id_matkul       INT NOT NULL,
    npm             VARCHAR(20) NOT NULL,
    rata_rata_quiz  FLOAT NOT NULL,
    PRIMARY KEY (id_quiz),
    UNIQUE KEY uniq_matkul_npm_quiz (id_matkul, npm),
    KEY (npm),
    CONSTRAINT nilai_quiz_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_quiz_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_quiz_detail (
    id_quiz_detail INT NOT NULL AUTO_INCREMENT,
    id_matkul      INT NOT NULL,
    npm            VARCHAR(20) NOT NULL,
    quiz_number    INT NOT NULL,
    nilai_quiz     FLOAT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_quiz_detail),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT nilai_quiz_detail_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_quiz_detail_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_tugas (
    id_tugas        INT NOT NULL AUTO_INCREMENT,
    id_matkul       INT NOT NULL,
    npm             VARCHAR(20) NOT NULL,
    rata_rata_tugas FLOAT NOT NULL,
    PRIMARY KEY (id_tugas),
    UNIQUE KEY uniq_matkul_npm_tugas (id_matkul, npm),
    KEY (npm),
    CONSTRAINT nilai_tugas_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_tugas_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_tugas_detail (
    id_tugas_detail INT NOT NULL AUTO_INCREMENT,
    id_matkul       INT NOT NULL,
    npm             VARCHAR(20) NOT NULL,
    tugas_number    INT NOT NULL,
    nilai_tugas     FLOAT NOT NULL DEFAULT 0,
    PRIMARY KEY (id_tugas_detail),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT nilai_tugas_detail_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_tugas_detail_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_responsi (
    id_responsi     INT NOT NULL AUTO_INCREMENT,
    id_matkul       INT NOT NULL,
    npm             VARCHAR(20) NOT NULL,
    nilai_responsi  FLOAT DEFAULT NULL,
    PRIMARY KEY (id_responsi),
    KEY (id_matkul),
    KEY (npm),
    CONSTRAINT nilai_responsi_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_responsi_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE persentase_matkul (
    id_persentase         INT NOT NULL AUTO_INCREMENT,
    id_matkul             INT NOT NULL,
    persentase_absensi    FLOAT NOT NULL,
    persentase_tugas      FLOAT NOT NULL,
    persentase_quiz       FLOAT NOT NULL,
    persentase_uts        FLOAT NOT NULL,
    persentase_uas        FLOAT NOT NULL,
    persentase_responsi   FLOAT DEFAULT NULL,
    PRIMARY KEY (id_persentase),
    KEY (id_matkul),
    CONSTRAINT persentase_matkul_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE nilai_akhir_mahasiswa (
    id_nilai           INT NOT NULL AUTO_INCREMENT,
    id_matkul          INT NOT NULL,
    npm                VARCHAR(20) NOT NULL,
    nilai_akhir_total  FLOAT DEFAULT NULL,
    huruf_matkul       VARCHAR(2) DEFAULT NULL,
    bobot_matkul       FLOAT DEFAULT NULL,
    PRIMARY KEY (id_nilai),
    UNIQUE KEY uniq_nilai_matkul_npm (id_matkul, npm),
    KEY (npm),
    CONSTRAINT nilai_akhir_mahasiswa_ibfk_1 FOREIGN KEY (id_matkul)
        REFERENCES mata_kuliah(id_matkul) ON DELETE CASCADE,
    CONSTRAINT nilai_akhir_mahasiswa_ibfk_2 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE ipk_mahasiswa (
    npm                  VARCHAR(20) NOT NULL,
    total_bobot_x_sks    FLOAT DEFAULT 0,
    total_sks            INT DEFAULT 0,
    ipk                  FLOAT DEFAULT 0,
    jumlah_matkul        INT DEFAULT 0,
    last_updated         TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (npm),
    CONSTRAINT ipk_mahasiswa_ibfk_1 FOREIGN KEY (npm)
        REFERENCES mahasiswa(npm) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

COMMIT;
