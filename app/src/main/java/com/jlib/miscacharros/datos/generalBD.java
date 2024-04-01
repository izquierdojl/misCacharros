package com.jlib.miscacharros.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class generalBD extends SQLiteOpenHelper {

    public generalBD(Context context) {
        super(context, "misCacharros", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       String sql = "CREATE TABLE tipo("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " nombre TEXT, "
                + " prioridad INTEGER, "
                + " color INTEGER )";
       db.execSQL(sql);

       sql = "CREATE TABLE contacto("
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

        sql = "CREATE TABLE cacharro ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " name TEXT, "
                + " fabricante TEXT, "
                + " idContacto INTEGER, "
                + " idTipo INTEGER, "
                + " imagen TEXT, "
                + " archivo TEXT, "
                + " uid TEXT, "
                + " alta LONG, "
                + " aviso BOOLEAN, "
                + " momentoaviso LONG,"
                + " textoaviso TEXT,"
                + " FOREIGN KEY (idTipo) REFERENCES tipo(id) ON DELETE RESTRICT, "
                + " FOREIGN KEY (idContacto) REFERENCES contacto(id) ON DELETE RESTRICT )" ;

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // poner aquí las operaciones para actualizar
    }
}
