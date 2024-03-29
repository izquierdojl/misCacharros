package com.jlib.miscacharros.datos.tipo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import com.jlib.miscacharros.datos.generalBD;
import com.jlib.miscacharros.modelo.Tipo;

public class TiposBD extends generalBD implements RepositorioTipos {

    Context contexto;

    public TiposBD(Context context) {
        super(context);
    }

    public static Tipo extraeTipo(Cursor cursor) {
        Tipo tipo = new Tipo();
        tipo.setId(cursor.getInt(0));
        tipo.setNombre(cursor.getString(1));
        tipo.setPrioridad(cursor.getInt(2));
        tipo.setColor(cursor.getInt(3));
        return tipo;
    }

    public Cursor extraeCursor() {
        String sql = "SELECT * FROM tipo ORDER BY prioridad DESC";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public Tipo elemento(int id) {
        String sql = "SELECT * FROM tipos WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraeTipo(cursor);
            else
                throw new SQLException("Error al acceder al elemento id" + id);
        } catch (Exception e) {
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    @Override
    public void anade(Tipo tipo) {
        String sql = "INSERT INTO tipo (nombre,prioridad,color) VALUES ('" + tipo.getNombre() + "'," + tipo.getPrioridad() + "," + tipo.getColor() + ")";
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM tipo "
                   + " WHERE id=" + id;
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public int tamano() {
        String sql = "SELECT COUNT(id) FROM tipo ";
        try {
            Cursor cursor = getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            } else {
                return 0;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            return 0; // Otra acción adecuada según tus requisitos.
        }
    }

    @Override
    public void actualiza(int id, Tipo tipo) {
        String sql = "UPDATE tipo SET "
                + " nombre= '" + tipo.getNombre() + "', "
                + " prioridad= " + tipo.getPrioridad()+ ", "
                + " color= " + tipo.getColor()
                + " WHERE id = " + id ;
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public void anadeEjemplos() {
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (1,'Cocina y Electrodoméstidos',3,"+Color.rgb(250,219,216)+")");
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (2,'Entretenimiento',5,"+Color.rgb(212,230,241)+")");
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (3,'Móviles y Accesorios',4,"+Color.rgb(253,235,208)+")");
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (4,'Hogar',2,"+Color.rgb(234,237,237)+")");
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (5,'Varios',1,"+Color.rgb(215,218,226)+")");
    }

    public void limpia()
    {
        //String sql = "DROP TABLE tipo  ";
        String sql = "DELETE FROM tipo  ";
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public void sortPrioridad() {
    }
}
