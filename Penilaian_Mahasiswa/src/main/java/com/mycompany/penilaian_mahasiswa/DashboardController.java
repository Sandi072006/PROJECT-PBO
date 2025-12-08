package com.mycompany.penilaian_mahasiswa;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class DashboardController {

    @FXML
    private PieChart nilaiPieChart;
    @FXML
    public void tampilkanStatistik() {
        PieChart.Data data1 = new PieChart.Data("A", 40);
        PieChart.Data data2 = new PieChart.Data("B", 30);
        PieChart.Data data3 = new PieChart.Data("C", 20);
        PieChart.Data data4 = new PieChart.Data("D", 10);

        nilaiPieChart.getData().addAll(data1, data2, data3, data4);
    }
}
