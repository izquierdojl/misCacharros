package com.jlib.miscacharros.datos.cacharro;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.modelo.Contacto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CacharrosLista implements RepositorioCacharros {

    protected List<Cacharro> listaCacharros;

    public CacharrosLista() {
        listaCacharros = new ArrayList<Cacharro>();
    }

    @Override
    public Cacharro elemento(int id) {
        return listaCacharros.get(id);
    }

    @Override
    public void anade(Cacharro cacharro) {
        listaCacharros.add(cacharro);
    }

    @Override
    public void borrar(int id) {
        listaCacharros.remove(id);
    }

    @Override
    public int tamano() {
        return listaCacharros.size();
    }

    @Override
    public void actualiza(int id, Cacharro cacharro) {
        listaCacharros.set(id,cacharro);
    }

    public void sortName() {
        listaCacharros.sort( new Comparator<Cacharro>() {
                        public int compare(Cacharro carracho1, Cacharro cacharro2) {
                            return carracho1.getName().compareTo(cacharro2.getName());
            }
        });
    }
    public void anadeEjemplos()
    {
    }
}
