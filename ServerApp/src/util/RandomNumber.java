package util;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class RandomNumber {

    /**
     * Genera la cantidad de numeros pseudo aleatorios sin repetir especificados entre
     * 0 (incluido) y el valor maximo especificado (excluido)
     * y genera un iterador con todos ellos, si la cantidad especificada es mayor
     * al numero máximo crea un iterador con los numeros entre 0 y el máximo
     * @param max valor maximo de los números a generar
     * @param quantity cantidad de numeros a generar
     * @return iterador con todos los numeros generados
     * @see Random
     */
    public static Iterator<Integer> generateWithoutRepeat(int max, int quantity){
        if (quantity > max) return IntStream.range(0,max).iterator();
        HashSet<Integer> numbers = new HashSet<>(quantity);
        Random random = new Random();
        while (quantity > 0){
            if (numbers.add(generate(max, random)))
                quantity--;
        }
        return numbers.iterator();
    }

    /**
     * genera un numero aleatorio entre 0 incluido y el
     * máximo excluido usando el objeto Random especificado
     * @param max numero maximo a generar
     * @param random objeto creador de numeros aleatorios
     * @return numero aleatorio entre 0 y el maximo
     * @see Random
     */
    private static int generate(int max, Random random){
        return random.nextInt(max);
    }
}
