package com.company.model;

import java.util.Date;


public class Pedido implements Comparable<Pedido> {
    private final int idPedido;
    private int idCluster;
    private  Cliente cliente;
    private  int idRuta;
    private  int cordX;
    private  int cordY;
    private  Date fechaPedido;
    private  int tiempoLimite;
    private  Date fechaEntrega;
    private  boolean penalidad;
    private  int cantidad;
    private  double costoPenalidad;
    private  long duracion;
    private int tiempoEntrega;

    public boolean isPenalidad() {
        return penalidad;
    }

    public Pedido(int id, int x, int y, int cantidad, int tiempoLimite){
        this.idPedido = id;
        this.cordX = x;
        this.cordY = y;
        this.cantidad = cantidad;
        this.tiempoLimite = tiempoLimite;
    }

    public Pedido(int idPedido,Cliente cliente, int idRuta,int cordX,int cordY, Date fechaPedido,int tiempoLimite,
                  Date fechaEntrega,boolean penalidad,double costoPenalidad, long duracion,int cantidad){
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.idRuta = idRuta;
        this.cordX =cordX;
        this.cordY =cordY;
        this.fechaPedido = fechaPedido;
        this.tiempoLimite=tiempoLimite;
        this.fechaEntrega = fechaEntrega;
        this.penalidad=penalidad;
        this.cantidad=cantidad;
        this.costoPenalidad=costoPenalidad;
        this.duracion=duracion;
    }


    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public double getCostoPenalidad() {
        return costoPenalidad;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public int getTiempoLimite() {
        return tiempoLimite;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public long getDuracion() {
        return duracion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIdCluster() {
        return idCluster;
    }

    public void setIdCluster(int idCluster) {
        this.idCluster = idCluster;
    }

    @Override
    public int compareTo(Pedido o) {
        if( this.tiempoLimite == o.tiempoLimite ){
            if( this.cantidad == o.cantidad ) return 0;
            else if( this.cantidad < o.cantidad ) return 1;
            else return -1;
        }
        else if( this.tiempoLimite > o.tiempoLimite ) return 1;
        else return -1;
    }
}
