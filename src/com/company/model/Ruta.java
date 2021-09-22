package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ruta {
    private List<Nodo> nodos;
    private int distancia;

    public Ruta(List<Nodo> nodos) {
        this.nodos = new ArrayList<>(nodos);
        Collections.shuffle(this.nodos);
    }
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

    public Ruta duplicar(){
        return new Ruta(new ArrayList<>(nodos));
    }

    public int numClientes(){
        return nodos.size();
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    @Override
    public String toString() {
        return   nodos +
                ", distancia=" + distancia ;
    }
}
