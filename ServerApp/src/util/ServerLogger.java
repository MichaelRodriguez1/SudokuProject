package util;

import java.time.LocalDateTime;

/**
 * @author Samuel F. Ruiz
 * @since 18/09/20
 */
public class ServerLogger {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Imprime un texto con el nombre del objeto y la hora del sistema,
     * luego el texto ingresado por consola
     * @param text texto a imprimir
     */
    public void println(String text){
        System.out.println(name +" "+LocalDateTime.now() + ": ");
        System.out.println(text);
    }
}
