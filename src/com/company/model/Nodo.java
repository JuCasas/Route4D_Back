package com.company.model;

public class Nodo {
    private int x;
    private int y;
    private int pedido;

    public Nodo(int x,int y,int pedido){
        this.x = x;
        this.y = y;
        this.pedido = pedido;
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

    @Override
    public String toString() {
        return "Nodo{" +
                "x=" + x +
                ", y=" + y +
                ", pedido=" + pedido +
                '}';
    }
}
