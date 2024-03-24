package com.jlib.miscacharros.datos.util;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * clase para utilidades varias realicionadas con el manejo de datos
 */
public class Util {

    /**
     * Devuelve si una cadena es un email
     * @param email email a evaluar
     * @return si cumple el patrón de dirección de email
     */
    public static boolean validEmail(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Devuelve si una cadena es una direccion web
     * @param web email a evaluar
     * @return si cumple el patrón de dirección de email
     */
    public static boolean validWeb(String web)
    {
        Pattern pattern = Patterns.WEB_URL;
        Matcher matcher = pattern.matcher(web);
        return matcher.matches();
    }

}
