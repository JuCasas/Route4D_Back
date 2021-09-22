package com.company.model;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.Date;
public class Nodo {
    private int x;
    private int y;
    private Nodo nodoAnterior = null;
    private double distanciaMinima = Double.MAX_VALUE;
    private boolean bloqueado;
    private boolean almacen;
    private double h;
    private Date fechaIniBloqueo;
    private Date fechaFinBloqueo;
    private int pedido;
    private int limite;

    public Nodo(int x,int y,int pedido,int limite){
        this.x = x;
        this.y = y;
        this.pedido = pedido;
        this.limite = limite;
    }

    public double calcularHeuristica(Nodo fin){
        this.h=Math.abs(this.getX()-fin.getX())+Math.abs(this.getY()-fin.getY());
        return this.h;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPedido() {
        return pedido;
    }

    public int getLimite() {
        return limite;
    }

    public boolean isBloqueado() {
        if(this.fechaIniBloqueo ==null || this.fechaFinBloqueo==null)
            return false;
        Date actual = Helper.DateUtils.convertToDateViaInstant(LocalDateTime.now());
        return actual.compareTo(this.fechaIniBloqueo)>0 && actual.compareTo(this.fechaFinBloqueo)<0;
    }

    public boolean isBloquead(Date fecha){
        if(this.fechaIniBloqueo ==null || this.fechaFinBloqueo==null)
            return false;
        return fecha.compareTo(this.fechaIniBloqueo)>0 && fecha.compareTo(this.fechaFinBloqueo)<0;
    }

    @Override
    public String toString() {
        return "Nodo{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
