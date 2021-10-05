package com.company;

import com.company.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulated {
    private static final double temperatura = 1000;
    private static final double factor = 0.995;

    public static void main(String[] args) {
        List<Pedido> clientes = new ArrayList<>();
        List<Vehicle> camiones = new ArrayList<>();

        // Agregando camiones
        Vehicle camion1 = new Vehicle(1, 20, 1, 20, "ABC001", 1, 10);
        Vehicle camion2 = new Vehicle(2, 15, 1, 20, "ABC002", 1, 20);
        Vehicle camion3 = new Vehicle(3, 20, 1, 20, "ABC003", 1, 10);
        camiones.add(camion1);
        camiones.add(camion2);
        camiones.add(camion3);

        // Agregando clientes
        Pedido cliente1 = new Pedido(1, 100, 100, 10, 4);
        Pedido cliente2 = new Pedido(2, 150, 200, 6, 3);
        Pedido cliente3 = new Pedido(3, 100, 200, 4, 2);
        Pedido cliente4 = new Pedido(4, 200, 100, 15, 1);
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
        clientes.add(cliente4);

        int num = clientes.size();
        int matrizDistancias[][] = Helper.calcMatriz(clientes);
        int tiempos[] = new int[num];
        int demandas[] = new int[num];
        for (int i = 0; i < num; i++) {
            Pedido cliente = clientes.get(i);
            tiempos[i] = cliente.getTiempoLimite();
            demandas[i] = cliente.getCantidad();
        }

        // Cargar en un arreglo la demanda de cada cliente
        // Verificar que cada cliente pueda rutearse:
        //
        // Iniciar la ruta k desde el depósito. Definir
        // Definir q(k) = Q;

        Ruta current = new Ruta(clientes);
        Ruta best = current.duplicar();

        for (double t = temperatura; t > 1; t *= factor) {

            Ruta vecino = current.duplicar();

            int index1 = (int) (vecino.numClientes() * Math.random());
            int index2 = (int) (vecino.numClientes() * Math.random());

            Collections.swap(vecino.getPedidos(), index1, index2);

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
