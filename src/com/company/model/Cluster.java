package com.company.model;

import java.util.PriorityQueue;

public class Cluster implements Comparable<Cluster>{

    private int c1;
    private int c2;
    private Vehicle vehiculo;
    private  Pedido primerPedido = null;
    private int capacidad =0;
    private PriorityQueue<Pedido> pedidos;

    public void setClusterNo(Pedido pedido){
        pedidos.add(pedido);
        this.capacidad += pedido.getCantidad();
    }

    @Override
    public int compareTo(Cluster c) {
        if( this.primerPedido.getTiempoLimite() == c.primerPedido.getTiempoLimite() ){
            if( this.primerPedido.getCantidad() == c.primerPedido.getCantidad() ) return 1;
            else if( this.primerPedido.getCantidad() < c.primerPedido.getCantidad() ) return 1;
            else return -1;
        }
        else if( this.primerPedido.getTiempoLimite() > c.primerPedido.getTiempoLimite() ) return 1;
        else return -1;
    }
}
