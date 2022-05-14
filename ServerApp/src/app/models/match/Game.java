package app.models.match;

import app.Constants;
import app.models.board.SudokuBoard;
import app.models.board.SudokuBuilder;

public class Game implements Comparable<Game>{

    private final Difficulty difficulty;
    private final String pattern;

    /**
     * Crea un juego con la dificultad especificada y el patrón del tablero del juego
     * @param difficulty dificultad
     * @param pattern patrón del tablero
     * @see Difficulty
     */
    public Game(Difficulty difficulty, String pattern) {
        this.difficulty = difficulty;
        this.pattern = pattern;
    }

    /**
     * @return dificultad del juego
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * crea un tablero con el patrón del juego y todas sus
     * casillas fijas
     * @return tablero con el patrón del juego
     */
    public SudokuBoard getBoard(){
        return SudokuBuilder.buildSudoku(pattern, true);
    }

    /**
     * Crea un patron del tablero especificado
     * @param board tablero del cual se va a construir el tablero
     * @return patrón del tablero
     */
    public static synchronized String getBoardPattern(SudokuBoard board){
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = board.get(i, j).getValue();
                pattern.append(value == 0? Constants.EMPTY_STRING_PATTERN : value);
            }
        }
        return pattern.toString();
    }

    @Override
    public int compareTo(Game o) {
        return pattern.compareTo(o.pattern);
    }
}
