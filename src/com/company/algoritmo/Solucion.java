package com.company.algoritmo;

import com.company.algoritmo.dijkstra.Dijkstra;
import com.company.algoritmo.kmeans.Kmeans;
import com.company.model.*;
import com.company.readers.Reader;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solucion {

    public List<Pedido> listaPedidos;
    public List<Vehicle> listaVehiculos;
    public List<Cluster> listaClusters;
    public List<Bloqueos> listaBloqueos;
    public List<Ruta> listaRutas;
    public Dijkstra dijkstra;
    public Kmeans kmeans;
    public int cantCluster1=0;
    public int cantCluster2=0;
    public int cantCluster3=0;
    public int cantCluster4=0;
    public int cantVehiculos1=0;
    public int cantVehiculos2=0;
    public int cantVehiculos3=0;
    public int cantVehiculos4=0;
    public int cantidadTotal=0;

    public String inicializar(){
        listaVehiculos = Reader.leerVehiculos(); //falta implementar
        listaPedidos = Reader.obtenerListaPedidos(); //falta implementar
        if(listaVehiculos.size() == 0) return "No hay vehículos disponibles para las rutas";
        if(listaPedidos.size() == 0) return "No hay pedidos en cola";
        obtenerCantidadClusters();
        kmeans = new Kmeans(cantVehiculos1,cantVehiculos2,cantVehiculos3,cantVehiculos4);
        listaBloqueos = Reader.obtenerCallesBloqueadas();
        obtenerListaAdyacente();
        return "correcto";
    }

    public void obtenerCantidadClusters(){
        //Se podría hacer en el metodo del reader
        for(Vehicle vehiculo:listaVehiculos){
            if(vehiculo.getTipo()==1) cantVehiculos1++;
            if(vehiculo.getTipo()==2) cantVehiculos2++;
            if(vehiculo.getTipo()==3) cantVehiculos3++;
            if(vehiculo.getTipo()==4) cantVehiculos4++;
        }

        System.out.println(listaVehiculos.size());
        int k = (int) (0.9 * (cantidadTotal /(cantVehiculos1*5+cantVehiculos2*10+cantVehiculos3*15+cantVehiculos4*25)));
        if(k > 10) k = 10;
        if(k < 3) k = 3;
        cantCluster1 = cantVehiculos1 * k;
        cantCluster2 = cantVehiculos2 * k;
        cantCluster3 = cantVehiculos3 * k;
        cantCluster4 = cantVehiculos4 * k;

    }

    public void obtenerListaAdyacente(){
        int origen, destino;
        InputStream grafo = getClass().getClassLoader().getResourceAsStream("grafo.txt");
        Scanner sc = new Scanner( grafo );
        dijkstra = new Dijkstra(Inicializacion.V, listaBloqueos);
        for( int i = 0 ; i < Inicializacion.E ; ++i ){
            origen = sc.nextInt() +1;
            destino = sc.nextInt() +1;
            dijkstra.addEdge(origen, destino);
        }
    }
    public List<Vehicle> inicializarVehiculos() {
        List<Vehicle> lista = new ArrayList<>();
        for(int i=0; i<cantClusterMotos;i++){
            AVehiculo vehiculo = new AVehiculo();
            vehiculo.setTipo("Moto");
            vehiculo.setCapacidad(4);
            vehiculo.setCosto_km(3.0);
            vehiculo.setVelocidad(60.00);
            vehiculo.setTipo_id(2);
            lista.add(vehiculo);
        }
        for(int i=0; i<cantClusterAutos;i++){
            AVehiculo vehiculo = new AVehiculo();
            vehiculo.setTipo("Auto");
            vehiculo.setCapacidad(25);
            vehiculo.setCosto_km(5.0);
            vehiculo.setVelocidad(30.00);
            vehiculo.setTipo_id(1);
            lista.add(vehiculo);
        }
        return lista;
    }

    public void obtenerPedidosClusterizados(){
        int cantClusters = cantCluster1 + cantCluster2+cantCluster3+cantCluster4;
        List<Vehicle> vehiculos = inicializarVehiculos();

        //inicializar clusters
        List<Cluster> clustersList = inicializarClusters(vehiculos);
        List<Cluster> clustersAns = inicializarClusters(vehiculos);

        //Clusterizacion
        clusterResult = kmeans.kmeans(listaPedidos,clustersList,cantClusters,clustersAns);
        Double SSE = kmeans.getOptimo(listaPedidos,clustersAns,cantClusters);
        System.out.println("------------------------------------------------------");
        System.out.println("Rutas calculadas con un SSE=" + SSE);
        System.out.println("------------------------------------------------------");
        System.out.println();
        System.out.println();
    }

    public String generarRutas(){
        LocalDateTime tiempo1,tiempo2;
        tiempo1 = LocalDateTime.now();
        obtenerPedidosClusterizados();
        obtenerRutas();
        asignarRutas();
        tiempo2 = LocalDateTime.now();
        log.info("Tiempo de ejecución del algortimo: " + (tiempo2.getSecond()-tiempo1.getSecond()) + " segundos");
        return "Rutas generadas exitosamente";
    }



}
