package com.company.model;

import java.util.HashSet;

public class Bloqueos {
    private int id;
    private int inicio;
    private int fin;
    private HashSet<Integer> nodos;

    public Bloqueos(Integer id, int minutosInicio, int minutosFin) {
        this.id = id;
        this.inicio = minutosInicio;
        this.fin = minutosFin;
        this.nodos = new HashSet<Integer>();
    }

    public int getInicio() {
        return inicio;
    }

    public int getFin() {
        return fin;
    }

    public void agregarNodo(int nodoId) {
        nodos.add(nodoId);
    }

    public boolean estaNodo(int nodoId) {
        return nodos.contains(nodoId);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public void setNodos(HashSet<Integer> nodos) {
        this.nodos = nodos;
    }

    public HashSet<Integer> getNodos() {
        return nodos;
    }
}
