package com.company;

public class Vehicle {
    private int id;
    private int capacidad;
    private
    private int tipo;
    private String placa;
    private int estado;

    public  Vehicle(int id,int capacidad,int tipo,String placa,int estado){
        this.id = id;
        this.capacidad=capacidad;
        this.tipo = tipo;
        this.placa = placa;
        this.estado = estado;

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


    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", capacidad=" + capacidad +
                ", tipo=" + tipo +
                ", placa='" + placa + '\'' +
                ", estado=" + estado +
                '}';
    }
}
