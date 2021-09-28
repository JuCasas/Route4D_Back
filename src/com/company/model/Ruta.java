package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ruta {
    private final List<Pedido> pedidos;
    private List<Integer> puntosX;
    private List<Integer> puntosY;
    private List<Integer> camino;
    private List<Integer> retorno;
    private int distancia;
    private int tiempo;
    private int capacidad;
    private Vehicle vehiculo;


    public Ruta(){
        this.camino = new ArrayList< Integer >();
        this.retorno = new ArrayList< Integer >();
        this.pedidos = new ArrayList<Pedido>();
        this.tiempo = Integer.MAX_VALUE;
        this.capacidad = 0;
        this.vehiculo = new Vehicle();
    }

    public Ruta(Vehicle vehiculo, int capacidad){
        this.camino = new ArrayList< Integer >();
        this.retorno = new ArrayList< Integer >();
        this.pedidos = new ArrayList< Pedido>();
        this.tiempo = Integer.MAX_VALUE;
        this.capacidad = capacidad;
        this.vehiculo = vehiculo;
    }

    public void agregarPedido(Pedido pedido){
        if(pedido.getTiempoLimite() < tiempo) tiempo = pedido.getTiempoLimite();
        pedidos.add(pedido);
    }

    public void agregarNodo(int idNodo){
        camino.add(idNodo);
    }

    public void agregarNodoR(int idNodo){
        retorno.add(idNodo);
    }

    public Ruta(List<Pedido> pedidos) {
        this.pedidos = new ArrayList<>(pedidos);
        Collections.shuffle(this.pedidos);
    }
    /*
    public Nodo getNodo(int index){
        return nodos.get(index);
    }

    public int getDistanciaRuta(){
        if(distancia!=0) return distancia;
        int distanciaTotal =0;

        for(int i =0;i<numClientes();i++) {
            Nodo inicio = getNodo(i);
            Nodo fin = getNodo(i + 1 < numClientes() ? i+1 : 0); //SE DEBE ACTUALIZAR POR QUE ES MAS DE UN VEHICULO
            //POR CLIENTE
            distanciaTotal += Helper.distancia(inicio, fin);
        }
        distancia = distanciaTotal;
        return distanciaTotal;
    }
    */

    public List<Integer> getCamino() {
        return camino;
    }

    public Ruta duplicar(){
        return new Ruta(new ArrayList<>(pedidos));
    }

    public int numClientes(){
        return pedidos.size();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public String toString() {
        return   pedidos +
                ", distancia=" + distancia ;
    }
}
