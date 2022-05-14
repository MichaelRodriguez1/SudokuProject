package app.models.board;

public class SudokuBox {

    private int value;
    private boolean fix, ok;
    private final Coord coord;

    public SudokuBox(int value, boolean fix, Coord coord) {
        this.value = value;
        this.fix = fix;
        this.coord = coord;
        this.ok = true;
    }

    /**
     * @return valor de la casilla
     */
    public int getValue() {
        return value;
    }

    /**
     * @return si es una casilla fija o si es editable
     */
    public boolean isFix() {
        return fix;
    }

    /**
     * @return si el valor de la casilla es el correcto
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * obtiene la coordenada de la casilla la cual
     * es su fila y columna en el tablero
     * @return coordenada de la casilla
     */
    public Coord getCoord() {
        return coord;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setFix(boolean fix) {
        this.fix = fix;
    }
}
