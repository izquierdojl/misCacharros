package com.jlib.miscacharros.modelo;

public class Tipo {

    private String nombre;
    private int orden;
    private int prioridad;

    public Tipo() {
    }

    public Tipo(String nombre, int orden, int prioridad) {
        this.nombre = nombre;
        this.orden = orden;
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
