package app.models.board;

import app.Constants;
import util.StringIterator;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class SudokuBuilder {

    /**
     * Construye un tablero con los valores del patrón ingresado, si se
     * especifica que son fijos todos los valores diferentes al cero o coaracteres no
     * numéricos del patrón seran fijos en el tablero.
     * @param pattern patron del tablero a crear
     * @param fix si las casillas introducidas son fijas o no
     * @return Un tablero en base al patrón ingresado
     * @see SudokuBoard
     */
    public static SudokuBoard buildSudoku(String pattern, boolean fix){
        if (pattern != null && pattern.length() == Constants.PATTERN_LENGTH) {
            SudokuBoard board = new SudokuBoard();
            StringIterator iterator = new StringIterator(pattern);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int boxValue = toInt(iterator.next());
                    board.setValue(new Coord(i,j), boxValue, boxValue != 0 && fix);
                }
            }
            return board;
        }
        return null;
    }

    /**
     * convierte el valor del caracter al valor numérico
     * @param character caractor a convertir
     * @return valor del caracter si no es numerico retorna 0
     */
    private static int toInt(char character){
        if(Character.isDigit(character)){
            return character - '0';
        }
        return 0;
    }
}
