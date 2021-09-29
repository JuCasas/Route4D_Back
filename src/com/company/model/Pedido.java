package com.company.model;

import java.time.LocalDateTime;
import java.util.Date;


public class Pedido implements Comparable<Pedido> {
    private  int idPedido;
    private int idCluster;
    private  int idCliente;
    private  int idRuta;
    private  int cordX;
    private  int cordY;
    private int idDestino;
    private LocalDateTime fechaPedido;
    private  int tiempoLimite;
    private LocalDateTime plazoEntrega;
    private  Date fechaEntrega;
    private  boolean penalidad;
    private  int cantidad;
    private  double costoPenalidad;
    private  long duracion;
    private int tiempoEntrega;

    public Pedido() {

    }

    public void setPlazoEntrega(LocalDateTime plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public LocalDateTime getPlazoEntrega() {
        return plazoEntrega;
    }

    public boolean isPenalidad() {
        return penalidad;
    }

    public void setTiempoLimite(int tiempoLimite) {
        this.tiempoLimite = tiempoLimite;
    }

    public Pedido(int id, int x, int y, int cantidad, int tiempoLimite){
        this.idPedido = id;
        this.cordX = x;
        this.cordY = y;
        this.cantidad = cantidad;
        this.tiempoLimite = tiempoLimite;
    }

    public Pedido(int idPedido,int cliente, int idRuta,int cordX,int cordY, LocalDateTime fechaPedido,int tiempoLimite,
                  Date fechaEntrega,boolean penalidad,double costoPenalidad, long duracion,int cantidad){
        this.idPedido = idPedido;
        this.idCliente = cliente;
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

    public LocalDateTime getFechaPedido() {
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

    public int getIdCliente() {
        return idCliente;
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

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }

    public int getIdDestino() {
        return idDestino;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCliente(int cliente) {
        this.idCliente = cliente;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setPenalidad(boolean penalidad) {
        this.penalidad = penalidad;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
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
