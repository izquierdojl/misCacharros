package com.jlib.miscacharros.datos.contacto;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.jlib.miscacharros.modelo.Contacto;

public class ContactosBD extends SQLiteOpenHelper implements RepositorioContactos {

    Context contexto;

    public ContactosBD(Context context) {
        super(context, "misCacharros", null, 1);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE contacto("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT, "
                + " address TEXT, "
                + " postalCode TEXT, "
                + " city TEXT, "
                + " state TEXT, "
                + " country TEXT, "
                + " telef TEXT, "
                + " email TEXT, "
                + " web TEXT, "
                + " latitud DOUBLE, "
                + " longitud DOUBLE ) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // aquí irían los alter table en caso de modificar la tabla, por ejemplo, para una nueva versión
    }

    public static Contacto extraeContacto(Cursor cursor) {
        Contacto contacto = new Contacto();
        contacto.setId(cursor.getInt(0));
        contacto.setName(cursor.getString(1));
        contacto.setAddress(cursor.getString(2));
        return contacto;
    }

    public Cursor extraeCursor() {
        String sql = "SELECT * FROM contacto ORDER BY id";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public Contacto elemento(int id) {
        String sql = "SELECT * FROM contactos WHERE id=" + id;
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
        String sql = "INSERT INTO contacto (nombre,address) VALUES ('" + contacto.getName() + "'," + contacto.getAddress() + ")";
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
    public void actualiza(int id, Contacto contacto) {
        String sql = "UPDATE tipo SET "
                + " nombre= '" + contacto.getName() + "', "
                + " address= " + contacto.getAddress() + "' "
                + " WHERE id = " + id ;
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public void anadeEjemplos() {

        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (1,'Cocina y Electrodoméstidos',3 )" );
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (2,'Entretenimiento',5 )" );
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (3,'Móviles y Accesorios',4 )" );
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (4,'Hogar',2 )" );
        getWritableDatabase().execSQL( "INSERT INTO tipo VALUES (5,'Varios',1 )" );

    }

    public void limpia()
    {
        //String sql = "DROP TABLE tipo  ";
        String sql = "DELETE FROM contactos  ";
        getWritableDatabase().execSQL(sql);
    }

    @Override
    public void sortName() {
    }
}
