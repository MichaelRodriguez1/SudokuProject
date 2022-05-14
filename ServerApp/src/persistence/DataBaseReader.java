package persistence;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import util.StreamLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class DataBaseReader {

    /**
     * Lee el archivo CSV ingresado y construye un arreglo con sus diferentes campos para
     * cada una de las lineas del mismo y retorna una lista con los arreglos
     * @param dataBasePath path o ruta del archivo
     * @return lista del valor de los atributos de cada linea
     */
    public static List<String[]> readDataBase(String dataBasePath){
        List<String[]> list = new ArrayList<>();
        try (
                Reader reader = new InputStreamReader(StreamLoader.getStream(dataBasePath));
                CSVReader csvReader = new CSVReader(reader)
        ){
            String[] dataLine;
            csvReader.readNext();//ignora la cabecera
            while ((dataLine = csvReader.readNext()) != null) {

                list.add(dataLine);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return list;
    }
}
