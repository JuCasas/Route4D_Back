package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulated {
    private static final double temperatura = 1000;
    private static final double factor = 0.995;

    public static void main(String[] args) {
        List<Nodo> clientes = new ArrayList<>();

        Nodo cliente1 = new Nodo(100, 100);
        clientes.add(cliente1);

        Nodo cliente2 = new Nodo(150, 200);
        clientes.add(cliente2);

        Nodo cliente3 = new Nodo(100, 200);
        clientes.add(cliente3);

        Nodo cliente4 = new Nodo(200, 100);
        clientes.add(cliente4);

        Ruta current = new Ruta(clientes);
        Ruta best = current.duplicar();

        for (double t = temperatura; t > 1; t *= factor) {
            Ruta vecino = current.duplicar();

            int index1 = (int) (vecino.numClientes() * Math.random());
            int index2 = (int) (vecino.numClientes() * Math.random());

            Collections.swap(vecino.getNodos(), index1, index2);

            int currentLength = current.getDistanciaRuta();
            int neighborLength = vecino.getDistanciaRuta();

            if (Math.random() < Helper.probabilidad(currentLength, neighborLength, t)) {
                current = vecino.duplicar();
            }

            if (current.getDistanciaRuta() < best.getDistanciaRuta()) {
                best = current.duplicar();
            }
        }

        System.out.println("Distancia Recorrida: " + best.getDistanciaRuta());
        System.out.println("Tour: " + best);
    }
}