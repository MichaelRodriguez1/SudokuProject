package util;

import java.io.InputStream;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class StreamLoader {

    /**
     * carga un stream del recurso de la ruta ingresada
     * @param path ruta del archivo
     * @return inputStream de la ruta especificada
     * @see InputStream
     * @see StreamLoader
     */
    public static InputStream getStream(String path) {
        return StreamLoader.class.getClassLoader().getResourceAsStream(path);
    }
}
