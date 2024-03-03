package com.jlib.miscacharros.modelo;

public class Tipo {

    private String nombre;
    private int id;
    private int prioridad;

    public Tipo() {
    }

    public Tipo(String nombre, int id, int prioridad) {
        this.nombre = nombre;
        this.id = id;
        this.prioridad = prioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
}
