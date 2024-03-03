package com.jlib.miscacharros.datos.tipo;
import com.jlib.miscacharros.modelo.Tipo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TiposLista implements RepositorioTipos {

    protected List<Tipo> listaTipos;

    public TiposLista() {
        listaTipos = new ArrayList<Tipo>();
    }

    @Override
    public Tipo elemento(int id) {
        return listaTipos.get(id);
    }

    @Override
    public void anade(Tipo tipo) {
        listaTipos.add(tipo);
    }

    @Override
    public void borrar(int id) {
        listaTipos.remove(id);
    }

    @Override
    public int tamano() {
        return listaTipos.size();
    }

    @Override
    public void actualiza(int id, Tipo tipo) {
        listaTipos.set(id,tipo);
    }

    public void sortPrioridad() {
        listaTipos.sort( new Comparator<Tipo>() {
                        public int compare(Tipo tipo1, Tipo tipo2) {
                            return Integer.compare(tipo2.getPrioridad(), tipo1.getPrioridad());
            }
        });
    }
    public void anadeEjemplos()
    {
        anade(new Tipo("Cocina y Electrodomésticos",1,3));
        anade(new Tipo("Entretenimiento",2,5));
        anade(new Tipo("Móviles y Accesorios",3,4));
        anade(new Tipo("Hogar",4,2));
        anade(new Tipo("Varios",5,1));
        sortPrioridad();
    }
}
