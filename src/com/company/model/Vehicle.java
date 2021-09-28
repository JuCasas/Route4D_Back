package com.company.model;

public class Vehicle {
    private int id;
    private double capacidad;
    private int tipo;
    private double peso;
    private double pesoCarga;
    private String placa;
    private int estado;
    public double velocidad;

    public  Vehicle(int id,double capacidad,int tipo,double peso,String placa,int estado,double velocidad){
        this.id = id;
        this.capacidad=capacidad;
        this.tipo = tipo;
        this.placa = placa;
        this.estado = estado;
        this.peso = peso;
        this.velocidad = velocidad;
    }

    public Vehicle() {

    }

    public double getCapacidad() {
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setPesoCarga(double pesoCarga) {
        this.pesoCarga = pesoCarga;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
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
