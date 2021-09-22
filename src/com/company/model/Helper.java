package com.company.model;

public class Helper {
    public static double probabilidad(double d1, double d2, double temp) {
        if (d2 < d1) return 1;
        return Math.exp((d1 - d2) / temp);
    }

    public static double distancia(Nodo n1, Nodo n2) {
        int xDist = Math.abs(n1.getX() - n2.getX());
        int yDist = Math.abs(n1.getY() - n2.getY());
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }
}
