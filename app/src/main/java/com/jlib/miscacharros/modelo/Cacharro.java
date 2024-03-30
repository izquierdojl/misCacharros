package com.jlib.miscacharros.modelo;

import java.util.Arrays;

public class Cacharro {

    private int id;
    private String name;
    private String fabricante;
    private int idContacto;
    private int idTipo;
    private byte[] imagen;
    private byte[] archivo;
    private long alta;

    public Cacharro() {
    }

    public Cacharro(int id, String name, String fabricante, int idContacto, int idTipo, byte[] imagen, byte[] archivo, long alta) {
        this.id = id;
        this.name = name;
        this.fabricante = fabricante;
        this.idContacto = idContacto;
        this.idTipo = idTipo;
        this.imagen = imagen;
        this.archivo = archivo;
        this.alta = alta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public long getAlta(){
        return alta;
    }

    public void setAlta(long alta){
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "Cacharro{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", idContacto=" + idContacto +
                ", idTipo=" + idTipo +
                ", imagen=" + Arrays.toString(imagen) +
                ", archivo=" + Arrays.toString(archivo) +
                ", alta=" + alta +
                '}';
    }
}
