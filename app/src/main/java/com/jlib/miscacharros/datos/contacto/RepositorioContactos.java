package com.jlib.miscacharros.datos.contacto;

import com.jlib.miscacharros.modelo.Contacto;

public interface RepositorioContactos {
    Contacto elemento(int id); // devuelve un tipo en base a su id
    void anade(Contacto contacto); // añade un objeto tipo
    void borrar(int id); // elimina un elemento por su ID
    int tamano(); // devuelve el tamaño del repositorio o número de elementos
    void actualiza(int id, Contacto contacto); // actualiza un tipo por su posicion
    void anadeEjemplos(); // para pruebas
    void sortName();

}
