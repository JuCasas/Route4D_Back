package com.company.model;

import java.util.Date;


public class Pedido implements Comparable<Pedido> {
    private final int idPedido;
    private int idCluster;
    private final Cliente cliente;
    private final int idRuta;
    private final int cordX;
    private final int cordY;
    private final Date fechaPedido;
    private final int tiempoLimite;
    private final Date fechaEntrega;
    private final boolean penalidad;
    private final int cantidad;
    private final double costoPenalidad;
    private final long duracion;

    public boolean isPenalidad() {
        return penalidad;
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
