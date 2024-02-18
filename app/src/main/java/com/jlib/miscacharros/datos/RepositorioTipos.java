package com.jlib.miscacharros.datos;

import com.jlib.miscacharros.modelo.Tipo;

public interface RepositorioTipos {
    Tipo tipo(int id); // devuelve un tipo en base a su id
    void anade(Tipo tipo); // añade un objeto tipo
    int nuevo(); // añade un elemento en blanco y devuelve su ID
    void borrar(int id); // elimina un elemento por su ID
    int tamano(); // devuelve el tamaño del repositorio o número de elementos
    void actualiza(int id, Tipo tipo); // actualiza un tipo por su posicion
}
