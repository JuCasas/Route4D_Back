package com.company.algoritmo.dijkstra;

public class Nodo implements Comparable<Nodo>{
    int primero, segundo;

    //constructor
    public Nodo( int d , int p ){
        this.primero = d;
        this.segundo = p;
    }

    //para la cola de prioridad
    public int compareTo( Nodo o){
        if( segundo > o.segundo ) return 1;
        if( segundo == o.segundo ) return 0;
        return -1;
    }
}
