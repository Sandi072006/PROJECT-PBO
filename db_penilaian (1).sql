-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 08, 2025 at 05:49 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_penilaian`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi`
--

CREATE TABLE `absensi` (
  `id_absensi` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `jumlah_pertemuan` int NOT NULL,
  `jumlah_hadir` int NOT NULL,
  `nilai_absensi` float DEFAULT NULL,
  `nilai_akhir_absensi` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `mahasiswa`
--

CREATE TABLE `mahasiswa` (
  `npm` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jurusan` varchar(100) NOT NULL,
  `angkatan` int NOT NULL,
  `program_studi` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mahasiswa`
--

INSERT INTO `mahasiswa` (`npm`, `nama`, `jurusan`, `angkatan`, `program_studi`) VALUES
('12345', 'John Doe', 'Teknik Informatika', 2021, 'Sarjana Komputer'),
('823918283', 'smkakska', 'askanksnas', 2020, 'amsakasmka'),
('823918283232', 'sannndi', 'askanksnas', 2020, 'amsakasmka');

-- --------------------------------------------------------

--
-- Table structure for table `mata_kuliah`
--

CREATE TABLE `mata_kuliah` (
  `id_matkul` int NOT NULL,
  `nama_matkul` varchar(100) NOT NULL,
  `sks` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_akhir_mahasiswa`
--

CREATE TABLE `nilai_akhir_mahasiswa` (
  `id_nilai` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `nilai_akhir_total` float DEFAULT NULL,
  `huruf_matkul` char(1) DEFAULT NULL,
  `bobot_matkul` float DEFAULT NULL,
  `komentar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_quiz`
--

CREATE TABLE `nilai_quiz` (
  `id_quiz` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `quiz_1` float NOT NULL,
  `quiz_2` float NOT NULL,
  `rata_rata_quiz` float GENERATED ALWAYS AS (((`quiz_1` + `quiz_2`) / 2)) STORED,
  `nilai_akhir_quiz` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_responsi`
--

CREATE TABLE `nilai_responsi` (
  `id_responsi` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `nilai_responsi` float DEFAULT NULL,
  `nilai_akhir_responsi` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_tugas`
--

CREATE TABLE `nilai_tugas` (
  `id_tugas` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `tugas_1` float NOT NULL,
  `tugas_2` float NOT NULL,
  `tugas_3` float NOT NULL,
  `tugas_4` float NOT NULL,
  `rata_rata_tugas` float GENERATED ALWAYS AS (((((`tugas_1` + `tugas_2`) + `tugas_3`) + `tugas_4`) / 4)) STORED,
  `nilai_akhir_tugas` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_uas`
--

CREATE TABLE `nilai_uas` (
  `id_uas` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `nilai_uas` float NOT NULL,
  `nilai_akhir_uas` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `nilai_uts`
--

CREATE TABLE `nilai_uts` (
  `id_uts` int NOT NULL,
  `id_matkul` int NOT NULL,
  `npm` varchar(20) NOT NULL,
  `nilai_uts` float NOT NULL,
  `nilai_akhir_uts` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `persentase_matkul`
--

CREATE TABLE `persentase_matkul` (
  `id_persentase` int NOT NULL,
  `id_matkul` int NOT NULL,
  `persentase_absensi` float NOT NULL,
  `persentase_tugas` float NOT NULL,
  `persentase_quiz` float NOT NULL,
  `persentase_uts` float NOT NULL,
  `persentase_uas` float NOT NULL,
  `persentase_responsi` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id_absensi`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `mahasiswa`
--
ALTER TABLE `mahasiswa`
  ADD PRIMARY KEY (`npm`);

--
-- Indexes for table `mata_kuliah`
--
ALTER TABLE `mata_kuliah`
  ADD PRIMARY KEY (`id_matkul`);

--
-- Indexes for table `nilai_akhir_mahasiswa`
--
ALTER TABLE `nilai_akhir_mahasiswa`
  ADD PRIMARY KEY (`id_nilai`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `nilai_quiz`
--
ALTER TABLE `nilai_quiz`
  ADD PRIMARY KEY (`id_quiz`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `nilai_responsi`
--
ALTER TABLE `nilai_responsi`
  ADD PRIMARY KEY (`id_responsi`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `nilai_tugas`
--
ALTER TABLE `nilai_tugas`
  ADD PRIMARY KEY (`id_tugas`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `nilai_uas`
--
ALTER TABLE `nilai_uas`
  ADD PRIMARY KEY (`id_uas`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `nilai_uts`
--
ALTER TABLE `nilai_uts`
  ADD PRIMARY KEY (`id_uts`),
  ADD KEY `id_matkul` (`id_matkul`),
  ADD KEY `npm` (`npm`);

--
-- Indexes for table `persentase_matkul`
--
ALTER TABLE `persentase_matkul`
  ADD PRIMARY KEY (`id_persentase`),
  ADD KEY `fk_persentase_matkul` (`id_matkul`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id_absensi` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mata_kuliah`
--
ALTER TABLE `mata_kuliah`
  MODIFY `id_matkul` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_akhir_mahasiswa`
--
ALTER TABLE `nilai_akhir_mahasiswa`
  MODIFY `id_nilai` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_quiz`
--
ALTER TABLE `nilai_quiz`
  MODIFY `id_quiz` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_responsi`
--
ALTER TABLE `nilai_responsi`
  MODIFY `id_responsi` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_tugas`
--
ALTER TABLE `nilai_tugas`
  MODIFY `id_tugas` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_uas`
--
ALTER TABLE `nilai_uas`
  MODIFY `id_uas` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nilai_uts`
--
ALTER TABLE `nilai_uts`
  MODIFY `id_uts` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `persentase_matkul`
--
ALTER TABLE `persentase_matkul`
  MODIFY `id_persentase` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `absensi_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `absensi_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_akhir_mahasiswa`
--
ALTER TABLE `nilai_akhir_mahasiswa`
  ADD CONSTRAINT `nilai_akhir_mahasiswa_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_akhir_mahasiswa_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_quiz`
--
ALTER TABLE `nilai_quiz`
  ADD CONSTRAINT `nilai_quiz_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_quiz_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_responsi`
--
ALTER TABLE `nilai_responsi`
  ADD CONSTRAINT `nilai_responsi_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_responsi_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_tugas`
--
ALTER TABLE `nilai_tugas`
  ADD CONSTRAINT `nilai_tugas_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_tugas_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_uas`
--
ALTER TABLE `nilai_uas`
  ADD CONSTRAINT `nilai_uas_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_uas_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `nilai_uts`
--
ALTER TABLE `nilai_uts`
  ADD CONSTRAINT `nilai_uts_ibfk_1` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE,
  ADD CONSTRAINT `nilai_uts_ibfk_2` FOREIGN KEY (`npm`) REFERENCES `mahasiswa` (`npm`) ON DELETE CASCADE;

--
-- Constraints for table `persentase_matkul`
--
ALTER TABLE `persentase_matkul`
  ADD CONSTRAINT `fk_persentase_matkul` FOREIGN KEY (`id_matkul`) REFERENCES `mata_kuliah` (`id_matkul`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
