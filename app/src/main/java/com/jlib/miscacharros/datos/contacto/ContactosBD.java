package com.jlib.miscacharros.datos.contacto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jlib.miscacharros.datos.generalBD;
import com.jlib.miscacharros.modelo.Contacto;
import com.jlib.miscacharros.modelo.GeoPunto;

public class ContactosBD extends generalBD implements RepositorioContactos {

    Context contexto;

    public ContactosBD(Context context) {
        super(context);
    }

    public static Contacto extraeContacto(Cursor cursor) {
        Contacto contacto = new Contacto();
        contacto.setId(cursor.getInt(0));
        contacto.setName(cursor.getString(1));
        contacto.setAddress(cursor.getString(2));
        contacto.setPostalCode(cursor.getString(3));
        contacto.setCity(cursor.getString(4));
        contacto.setState(cursor.getString(5));
        contacto.setCountry(cursor.getString(6));
        contacto.setTelephone(cursor.getString(7));
        contacto.setEmail(cursor.getString(8));
        contacto.setWeb(cursor.getString(9));
        contacto.setGeopunto(new GeoPunto(cursor.getDouble(10),cursor.getDouble(11)));
        return contacto;
    }

    public Cursor extraeCursor() {
        String sql = "SELECT * FROM contacto ORDER BY name";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public Contacto elemento(int id) {
        String sql = "SELECT * FROM contacto WHERE id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        try {
            if (cursor.moveToNext())
                return extraeContacto(cursor);
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
    public void anade(Contacto contacto) {
        //String sql = "INSERT INTO contacto (name,address) VALUES ('" + contacto.getName() + "','" + contacto.getAddress() + "')";
        //getWritableDatabase().execSQL(sql);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contacto.getName() );
        values.put("address", contacto.getAddress());
        values.put("telef",contacto.getTelephone());
        values.put("web",contacto.getWeb());
        values.put("email",contacto.getEmail());
        values.put("latitud",contacto.getGeopunto().getLatitud());
        values.put("longitud",contacto.getGeopunto().getLongitud());
        db.insert("contacto",null,values);
        db.close();
    }

    @Override
    public void borrar(int id) {
        String sql = "DELETE FROM contacto "
                   + " WHERE id=" + id;
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public int tamano() {
        String sql = "SELECT COUNT(id) FROM contacto ";
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
    public void actualiza(int id, Contacto contacto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", contacto.getName() );
        values.put("address", contacto.getAddress());
        values.put("telef",contacto.getTelephone());
        values.put("web",contacto.getWeb());
        values.put("email",contacto.getEmail());
        values.put("latitud",contacto.getGeopunto().getLatitud());
        values.put("longitud",contacto.getGeopunto().getLongitud());
        db.update("contacto", values, "id = ?", new String[]{String.valueOf(id)});
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
        String sql = "DELETE FROM contacto  ";
        getWritableDatabase().execSQL(sql);
    }

    // Método para filtrar datos según el término de búsqueda

    public Cursor extraeCursorFiltrado(String searchTerm) {
        String sql = "SELECT * FROM contacto WHERE name LIKE '%" + searchTerm + "%' or address LIKE '%\" + searchTerm + \"%'  ORDER BY name";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void sortName() {
    }
}
