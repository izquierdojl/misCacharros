package com.jlib.miscacharros.datos.cacharro;
import com.jlib.miscacharros.modelo.Cacharro;

public interface RepositorioCacharros {
    Cacharro elemento(int id); // devuelve un tipo en base a su id
    void anade(Cacharro cacharro); // añade un objeto tipo
    void borrar(int id); // elimina un elemento por su ID
    int tamano(); // devuelve el tamaño del repositorio o número de elementos
    void actualiza(int id, Cacharro cacharro); // actualiza un tipo por su posicion
    void anadeEjemplos(); // para pruebas
    void sortName();

}
