package com.company.model;

import java.util.Date;


public class Pedido {
    private int idPedido;
    private Cliente cliente;
    private int idRuta;
    private int cordX;
    private int cordY;
    private Date fechaPedido;
    private int horaLimite;
    private Date fechaEntrega;
    private boolean penalidad;
    private double costoPenalidad;
    private long duracion;

    public boolean isPenalidad() {
        return penalidad;
    }

    public Pedido(int idPedido,Cliente cliente, int idRuta,int cordX,int cordY, Date fechaPedido,int horaLimite,
                  Date fechaEntrega,boolean penalidad,double costoPenalidad, long duracion){
        this.idPedido = idPedido;
        this.cliente = cliente;
        this.idRuta = idRuta;
        this.cordX =cordX;
        this.cordY =cordY;
        this.fechaPedido = fechaPedido;
        this.horaLimite=horaLimite;
        this.fechaEntrega = fechaEntrega;
        this.penalidad=penalidad;
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

    public int getHoraLimite() {
        return horaLimite;
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
}
