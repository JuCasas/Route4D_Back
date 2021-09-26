package com.company.model;

public class Vehicle {
    private int id;
    private int capacidad;
    private int tipo;
    private int peso;
    private String placa;
    private int estado;
    public double velocidad;

    public  Vehicle(int id,int capacidad,int tipo,int peso,String placa,int estado,double velocidad){
        this.id = id;
        this.capacidad=capacidad;
        this.tipo = tipo;
        this.placa = placa;
        this.estado = estado;
        this.peso = peso;
        this.velocidad = velocidad;
    }
    public int getCapacidad() {
        return capacidad;
    }

    public int getEstado() {
        return estado;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", capacidad=" + capacidad +
                ", tipo=" + tipo +
                ", peso=" + peso +
                ", placa='" + placa + '\'' +
                ", estado=" + estado +
                '}';
    }
}
