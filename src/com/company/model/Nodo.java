package com.company.model;

public class Nodo {
    private int x;
    private int y;
    private int pedido;
    private int limite;

    public Nodo(int x,int y,int pedido,int limite){
        this.x = x;
        this.y = y;
        this.pedido = pedido;
        this.limite = limite;
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

    @Override
    public String toString() {
        return "Nodo{" +
                "x=" + x +
                ", y=" + y +
                ", pedido=" + pedido +
                ", limite=" + limite +
                '}';
    }
}
