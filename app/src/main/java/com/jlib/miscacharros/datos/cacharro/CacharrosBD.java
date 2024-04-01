package com.jlib.miscacharros.datos.cacharro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.jlib.miscacharros.datos.generalBD;
import com.jlib.miscacharros.modelo.Cacharro;
import com.jlib.miscacharros.modelo.Tipo;

import java.util.List;


public class CacharrosBD extends generalBD implements RepositorioCacharros {

    public CacharrosBD(Context context) {
        super(context);
    }

    public static Cacharro extraeCacharro(Cursor cursor) {
        Cacharro cacharro  = new Cacharro();
        cacharro.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        cacharro.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        cacharro.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow("fabricante")));
        cacharro.setIdContacto(cursor.getInt(cursor.getColumnIndexOrThrow("idContacto")));
        cacharro.setIdTipo(cursor.getInt(cursor.getColumnIndexOrThrow("idTipo")));
        cacharro.setImagen(cursor.getString(cursor.getColumnIndexOrThrow("imagen")));
        cacharro.setArchivo(cursor.getString(cursor.getColumnIndexOrThrow("archivo")));
        cacharro.setAlta(cursor.getLong(cursor.getColumnIndexOrThrow("alta")));
        cacharro.setUid(cursor.getString(cursor.getColumnIndexOrThrow("uid")));
        if(cursor.getInt(cursor.getColumnIndexOrThrow("aviso"))==1)
            cacharro.setAviso(true);
        return cacharro;
    }

    public Cursor extraeCursor() {
        String sql = "SELECT * FROM cacharro ORDER BY name";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public Cacharro elemento(int id) {
        String sql = "SELECT * FROM cacharro WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraeCacharro(cursor);
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
    public void anade(Cacharro cacharro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cacharro.getName() );
        values.put("fabricante", cacharro.getFabricante());
        values.put("idContacto", cacharro.getIdContacto());
        values.put("idTipo", cacharro.getIdTipo());
        values.put("imagen", cacharro.getImagen());
        values.put("archivo", cacharro.getArchivo());
        values.put("alta", cacharro.getAlta());
        values.put("uid", cacharro.getUid());
        if( cacharro.isAviso())
            values.put("aviso", 1);
        else
            values.put("aviso", 0);
        values.put("momentoaviso", cacharro.getMomentoAviso());
        values.put("textoaviso", cacharro.getTextoAviso());
        db.insert("cacharro",null,values);
        db.close();
    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM cacharro "
                   + " WHERE id=" + id;
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public int tamano() {
        String sql = "SELECT COUNT(id) FROM cacharro ";
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
    public void actualiza(int id, Cacharro cacharro) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cacharro.getName() );
        values.put("fabricante", cacharro.getFabricante());
        values.put("idContacto", cacharro.getIdContacto());
        values.put("idTipo", cacharro.getIdTipo());
        values.put("imagen", cacharro.getImagen());
        values.put("archivo", cacharro.getArchivo());
        values.put("alta", cacharro.getAlta());
        values.put("uid", cacharro.getUid());
        if( cacharro.isAviso())
            values.put("aviso", 1);
        else
            values.put("aviso", 0);
        values.put("momentoaviso", cacharro.getMomentoAviso());
        values.put("textoaviso", cacharro.getTextoAviso());
        db.update("cacharro", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    @Override
    public void anadeEjemplos()
    {
        //getWritableDatabase().execSQL( "INSERT INTO contacto (id,name) VALUES (1,'Contacto 1' )" );
        //getWritableDatabase().execSQL( "INSERT INTO contacto (id,name) VALUES (2,'Establecimiento' )" );
    }

    public void limpia()
    {
        //String sql = "DROP TABLE tipo  ";
        String sql = "DELETE FROM cacharro  ";
        getWritableDatabase().execSQL(sql);
    }

    // Método para filtrar datos según el término de búsqueda

    public Cursor extraeCursorFiltrado(String searchTerm) {
        String sql = "SELECT * FROM cacharro WHERE name LIKE '%" + searchTerm + "%' or fabricante LIKE '%" + searchTerm + "%'  ORDER BY id DESC";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public Cursor extraeCursorFiltradoTipo(List<Tipo> tipos) {
        String whereIn = "";
        for ( int i = 0; i < tipos.size(); i++ )
        {
            if( i==0 )
                whereIn += "(";
            whereIn += tipos.get(i).getId();
            if( i < tipos.size()-1 )
                whereIn += ",";
            else
                whereIn += ")";
        }
        if(!whereIn.isEmpty()) {
            String sql = "SELECT * FROM cacharro WHERE idTipo IN "+whereIn+" ORDER BY id DESC";
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }else{
            String sql = "SELECT * FROM cacharro WHERE 1<>1";
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }
    }


    @Override
    public void sortName() {
    }
}
