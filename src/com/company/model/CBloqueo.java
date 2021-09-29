package com.company.model;

import java.time.LocalDateTime;

public class CBloqueo {
    private Integer id;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private String nodos;

    public CBloqueo (int id,LocalDateTime inicio,LocalDateTime fin, String nodos){
        this.id=id;
        this.inicio=inicio;
        this.fin = fin;
        this.nodos=nodos;
    }
    public void setNodos(String nodos) {
        this.nodos = nodos;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public Integer getId() {
        return id;
    }

    public String getNodos() {
        return nodos;
    }
}

