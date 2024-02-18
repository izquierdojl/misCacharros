package com.jlib.miscacharros.datos;
import com.jlib.miscacharros.modelo.Tipo;

import java.util.ArrayList;
import java.util.List;

public class TiposLista implements RepositorioTipos {

    protected List<Tipo> listaTipos;

    public TiposLista() {
        listaTipos = new ArrayList<Tipo>();
    }

    @Override
    public Tipo tipo(int id) {
        return listaTipos.get(id);
    }

    @Override
    public void anade(Tipo tipo) {
        listaTipos.add(tipo);
    }

    @Override
    public int nuevo() {
        Tipo tipo=new Tipo();
        listaTipos.add(tipo);
        return listaTipos.size();
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

    public void anadeEjemplos()
    {
        anade(new Tipo("Cocina",1));
        anade(new Tipo("Baño",2));
        anade(new Tipo("Comedor",2));
    }
}
