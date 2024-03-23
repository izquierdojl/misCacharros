package com.jlib.miscacharros.modelo;

/**
 * Clase para el manejo de puntos geográficos por su ubicación
 * @author José Luis Izquierdo
 */
public class GeoPunto {

    private double latitud;
    private double longitud;

    public GeoPunto(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "GeoPunto{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }

}
