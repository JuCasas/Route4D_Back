package com.company.model;

import java.util.HashSet;

public class Bloqueos {
    private final int id;
    private final int inicio;
    private final int fin;
    private final HashSet<Integer> nodos;

    public Bloqueos(int id, int inicio, int fin) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.nodos = new HashSet<Integer>();
    }

    public void agregar(int id) {
        nodos.add(id);
    }

    public boolean estaNodo(int nodoId){
        return nodos.contains(nodoId);
    }
}
