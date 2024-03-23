package com.jlib.miscacharros.datos.contacto;
import com.jlib.miscacharros.modelo.Contacto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactosLista implements RepositorioContactos {

    protected List<Contacto> listaContactos;

    public ContactosLista() {
        listaContactos = new ArrayList<Contacto>();
    }

    @Override
    public Contacto elemento(int id) {
        return listaContactos.get(id);
    }

    @Override
    public void anade(Contacto contacto) {
        listaContactos.add(contacto);
    }

    @Override
    public void borrar(int id) {
        listaContactos.remove(id);
    }

    @Override
    public int tamano() {
        return listaContactos.size();
    }

    @Override
    public void actualiza(int id, Contacto contacto) {
        listaContactos.set(id,contacto);
    }

    public void sortName() {
        listaContactos.sort( new Comparator<Contacto>() {
                        public int compare(Contacto contacto1, Contacto contacto2) {
                            return contacto1.getName().compareTo(contacto2.getName());
            }
        });
    }
    public void anadeEjemplos()
    {
    }
}
