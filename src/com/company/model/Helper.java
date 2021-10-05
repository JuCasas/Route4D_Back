package com.company.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Helper {
    public static double probabilidad(double d1, double d2, double temp) {
        if (d2 < d1)
            return 1;
        return Math.exp((d1 - d2) / temp);
    }

    public static double distancia(Pedido n1, Pedido n2) {
        int xDist = Math.abs(n1.getCordX() - n2.getCordX());
        int yDist = Math.abs(n1.getCordY() - n2.getCordY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public static int[][] calcMatriz(List<Pedido> clientes) {
        int num = clientes.size();
        int[][] matrizDistancias = new int[num][num];

        for (int i = 0; i < num; i++) {
            Pedido nodoi = clientes.get(i);
            for (int j = 0; j < num; j++) {
                // dist de cliente i a cliente j
                Pedido nodoj = clientes.get(j);
                // Distancia manhattan
                int distancia = (nodoi.getCordX() - nodoj.getCordX()) + (nodoi.getCordY() - nodoj.getCordY());
                matrizDistancias[i][j] = distancia;
            }
        }

        return matrizDistancias;
    }

    public static class DateUtils {
        public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
            return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
        }
    }
}
