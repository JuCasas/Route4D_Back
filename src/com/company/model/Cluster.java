package com.company.model;

import java.util.PriorityQueue;

public class Cluster implements Comparable<Cluster> {
    private int c1;
    private int c2;
    private int c3;
    private Vehicle vehiculo;
    private Pedido primerPedido = null;
    private int capacidad = 0;
    private PriorityQueue<Pedido> pedidos;

    public void setClusterNo(Pedido pedido) {
        pedidos.add(pedido);
        this.capacidad += pedido.getCantidad();
    }

    public int getC1() {
        return c1;
    }

    public int getC3() {
        return c3;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getC2() {
        return c2;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public void setC3(int c3) {
        this.c3 = c3;
    }

    public Vehicle getVehiculo() {
        return vehiculo;
    }

    public Pedido getPrimerPedido() {
        return primerPedido;
    }

    public PriorityQueue<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPrimerPedido(Pedido primerPedido) {
        this.primerPedido = primerPedido;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setPedidos(PriorityQueue<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setVehiculo(Vehicle vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public int compareTo(Cluster c) {
        if (this.primerPedido.getTiempoLimite() == c.primerPedido.getTiempoLimite()) {
            if (this.primerPedido.getCantidad() == c.primerPedido.getCantidad())
                return 1;
            else if (this.primerPedido.getCantidad() < c.primerPedido.getCantidad())
                return 1;
            else
                return -1;
        } else if (this.primerPedido.getTiempoLimite() > c.primerPedido.getTiempoLimite())
            return 1;
        else
            return -1;
    }
}
