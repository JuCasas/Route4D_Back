package com.company.model;

import java.time.LocalDateTime;

public class IntervaloB {
    private Integer id;
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }
}
