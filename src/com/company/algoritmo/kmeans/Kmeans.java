package com.company.algoritmo.kmeans;

import com.company.model.Cluster;
import com.company.model.Inicializacion;
import com.company.model.Pedido;

import java.util.*;

public class Kmeans {
    // a mayor precision mayor error
    static final Double fTiempo = 1.5;
    static final Double fPrecision = 0.0;
    private final int cantVehiculos1;
    private final int cantVehiculos2;
    private final int cantVehiculos3;
    private final int cantVehiculos4;

    public Kmeans(int cantidad1, int cantidad2, int cantidad3, int cantidad4) {
        this.cantVehiculos1 = cantidad1;
        this.cantVehiculos2 = cantidad2;
        this.cantVehiculos3 = cantidad3;
        this.cantVehiculos4 = cantidad4;
    }

    // selects initial cluster centers for k-mean clustering in a smart way to speed
    // up convergence
    private List<Cluster> kmpp(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.setC1((int) (Math.random() * 71));
            cluster.setC2((int) (Math.random() * 51));
            cluster.setC3((int) (Math.random() * 1440));
        }
        return clusters;
    }

    private double distancia(List<Cluster> clusters, Pedido pedido, int i) {

        int x1 = pedido.getCordX();
        int y1 = pedido.getCordY();

        int x2 = clusters.get(i).getC1();
        int y2 = clusters.get(i).getC2();
        double d;
        d = Math.abs(y2 - y1) + Math.abs(x2 - x1);
        return d;
    }

    private double euclideanDistanceXCluster(Cluster cluster, Pedido pedido) {
        int x1 = pedido.getCordX();
        int y1 = pedido.getCordY();

        int x2 = cluster.getC1();
        int y2 = cluster.getC2();

        double d;
        d = Math.abs(y2 - y1) + Math.abs(x2 - x1);
        return d;
    }

    private double SSE(Cluster cluster) {

        double suma = 0;
        double media = euclideanDistanceXCluster(cluster, cluster.getPrimerPedido());
        int cant = 1;

        // media
        for (Pedido pedido : cluster.getPedidos()) {
            cant++;
            media += euclideanDistanceXCluster(cluster, pedido);
        }
        if (cant != 0)
            media = media / cant;
        else
            media = 0;

        for (Pedido pedido : cluster.getPedidos()) {
            suma += Math.pow((euclideanDistanceXCluster(cluster, pedido) - media), 2);
        }

        return suma;
    }

    private double calculateTotalSSE(List<Cluster> clusters) {

        double totalsse = 0;
        int totalVehiculos1 = 0;
        int totalVehiculos2 = 0;
        int totalVehiculos3 = 0;
        int totalVehiculos4 = 0;
        List<Pedido> listaVehiculos1 = new ArrayList<>();
        List<Pedido> listaVehiculos2 = new ArrayList<>();
        List<Pedido> listaVehiculos3 = new ArrayList<>();
        List<Pedido> listaVehiculos4 = new ArrayList<>();
        int cont = 0;
        for (Cluster cluster : clusters) {
            if (cluster.getPrimerPedido() == null) {
                cont++;
                continue;
            }
            cluster.getPrimerPedido().setIdCluster(cont);
            if (cluster.getVehiculo().getTipo() == 1)
                listaVehiculos1.add(cluster.getPrimerPedido());
            if (cluster.getVehiculo().getTipo() == 2)
                listaVehiculos2.add(cluster.getPrimerPedido());
            if (cluster.getVehiculo().getTipo() == 3)
                listaVehiculos3.add(cluster.getPrimerPedido());
            if (cluster.getVehiculo().getTipo() == 4)
                listaVehiculos4.add(cluster.getPrimerPedido());

            cont++;
        }
        Collections.sort(listaVehiculos1);
        Collections.sort(listaVehiculos2);
        Collections.sort(listaVehiculos3);
        Collections.sort(listaVehiculos4);
        for (Pedido pedido : listaVehiculos1) {
            if (totalVehiculos1 < cantVehiculos1) {
                totalsse += SSE(clusters.get(pedido.getIdCluster()));
                totalVehiculos1++;
            }
        }
        for (Pedido pedido : listaVehiculos2) {
            if (totalVehiculos2 < cantVehiculos2) {
                totalsse += SSE(clusters.get(pedido.getIdCluster()));
                totalVehiculos2++;
            }
        }
        for (Pedido pedido : listaVehiculos3) {
            if (totalVehiculos3 < cantVehiculos3) {
                totalsse += SSE(clusters.get(pedido.getIdCluster()));
                totalVehiculos3++;
            }
        }
        for (Pedido pedido : listaVehiculos4) {
            if (totalVehiculos4 < cantVehiculos4) {
                totalsse += SSE(clusters.get(pedido.getIdCluster()));
                totalVehiculos4++;
            }
        }

        return totalsse;
    }

    private void limpiaPedidos(List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            cluster.setPrimerPedido(null);
            cluster.setCapacidad(0);
            cluster.setPedidos(new PriorityQueue<Pedido>(40, new Comparator<Pedido>() {
                // override compare method
                public int compare(Pedido i, Pedido j) {
                    if (Math.abs(i.getCordX() - Inicializacion.almacenCX) + Math.abs(
                            i.getCordY() - Inicializacion.almacenCY) > Math.abs(j.getCordX() - Inicializacion.almacenCX)
                                    + Math.abs(j.getCordY() - Inicializacion.almacenCY))
                        return 1;
                    else if (Math.abs(i.getCordX() - Inicializacion.almacenCX) + Math.abs(
                            i.getCordY() - Inicializacion.almacenCY) < Math.abs(j.getCordX() - Inicializacion.almacenCX)
                                    + Math.abs(j.getCordY() - Inicializacion.almacenCY))
                        return -1;
                    else if (i.getCantidad() > j.getCantidad())
                        return 1;
                    else if (i.getCantidad() < j.getCantidad())
                        return -1;
                    else
                        return 1;
                }
            }));
        }
    }

    private void recalculaCentroides(List<Cluster> clusters) {
        int promX, promY, promZ, cant;
        for (Cluster cluster : clusters) {
            if (cluster.getPrimerPedido() == null) {
                cluster.setC1((int) (Math.random() * 71));
                cluster.setC2((int) (Math.random() * 51));
                cluster.setC3((int) (Math.random() * 1440));
                continue;
            }
            promX = cluster.getPrimerPedido().getCordX();
            promY = cluster.getPrimerPedido().getCordY();
            promZ = cluster.getPrimerPedido().getTiempoLimite();
            cant = 1;

            for (Pedido pedido : cluster.getPedidos()) {
                cant++;
                promX += pedido.getCordX();
                promY += pedido.getCordY();
                promZ += pedido.getTiempoLimite();
            }
            if (cant != 0) {
                cluster.setC1(promX / cant);
                cluster.setC2(promY / cant);
                cluster.setC3(promZ / cant);
            }
        }
    }

    public List<Cluster> kmeans(List<Pedido> pedidos, List<Cluster> clusters, int K, List<Cluster> clusterAns) {

        int runs = 100;
        double SSE = Double.MAX_VALUE;
        while (runs-- != 0) {
            // Initialize Sum of Squared Errors to max, we'll lower it at each iteration
            limpiaPedidos(clusters);
            // Select K initial centroids
            kmpp(clusters);
            int iterations = 30;
            while (iterations-- != 0) {
                // Assign observations to centroids
                // var records = data.getRecords();
                limpiaPedidos(clusters);
                // For each record
                for (Pedido pedido : pedidos) {
                    double minDist = Double.MAX_VALUE;
                    // Find the centroid at a minimum distance from it and add the record to its
                    // cluster
                    int asigned = -1;
                    boolean first = false;
                    for (int i = 0; i < K; i++) {
                        double dist = distancia(clusters, pedido, i);
                        if (dist < minDist) {
                            if (pedido.getCantidad() + clusters.get(i).getCapacidad() <= clusters.get(i).getVehiculo()
                                    .getCapacidad()) {
                                minDist = dist;
                                if (asigned > -1) {
                                    if (first)
                                        clusters.get(asigned).setPrimerPedido(null);
                                    else
                                        clusters.get(asigned).getPedidos().remove(pedido);
                                    clusters.get(asigned).setCapacidad(pedido.getCantidad());
                                }
                                if (clusters.get(i).getPrimerPedido() == null) {
                                    clusters.get(i).setPrimerPedido(pedido);
                                    int cap = clusters.get(i).getCapacidad();
                                    cap += pedido.getCantidad();
                                    clusters.get(i).setCapacidad(cap);
                                    asigned = i;
                                    first = true;
                                } else {
                                    clusters.get(i).setClusterNo(pedido);
                                    asigned = i;
                                    first = false;
                                }
                            }
                        }
                    }
                }
                double newSSE = calculateTotalSSE(clusters);

                if (newSSE < SSE) {
                    SSE = newSSE;
                    for (int i = 0; i < clusters.size(); i++) {

                        clusterAns.get(i).setC1(clusters.get(i).getC1());
                        clusterAns.get(i).setC2(clusters.get(i).getC2());
                        clusterAns.get(i).setC3(clusters.get(i).getC3());
                    }
                    System.out.println("newSSE: " + newSSE);
                }

                recalculaCentroides(clusters);
            }
        }
        System.out.println("Rutas calculadas con un SSE=" + SSE);
        return clusterAns;
    }

    public Double getOptimo(List<Pedido> pedidos, List<Cluster> clusters, int K) {
        for (Pedido pedido : pedidos) {
            double minDist = Double.MAX_VALUE;
            // Find the centroid at a minimum distance from it and add the record to its
            // cluster
            int asigned = -1;
            boolean first = false;
            for (int i = 0; i < K; i++) {
                double dist = distancia(clusters, pedido, i);
                int cap;
                if (dist < minDist) {
                    if (pedido.getCantidad() + clusters.get(i).getCapacidad() <= clusters.get(i).getVehiculo()
                            .getCapacidad()) {
                        minDist = dist;
                        if (asigned > -1) {
                            if (first)
                                clusters.get(asigned).setPrimerPedido(null);
                            else
                                clusters.get(asigned).getPedidos().remove(pedido);
                            cap = clusters.get(asigned).getCapacidad();
                            cap -= pedido.getCantidad();
                            clusters.get(asigned).setCapacidad(cap);
                        }
                        if (clusters.get(i).getPrimerPedido() == null) {
                            clusters.get(i).setPrimerPedido(pedido);
                            int capa = clusters.get(i).getCapacidad();
                            capa += pedido.getCantidad();
                            clusters.get(i).setCapacidad(capa);
                            asigned = i;
                            first = true;
                        } else {
                            clusters.get(i).setClusterNo(pedido);
                            asigned = i;
                            first = false;
                        }
                    }
                }
            }
        }
        return calculateTotalSSE(clusters);
    }

}
