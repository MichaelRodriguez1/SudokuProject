package app.models.board;

public class Coord{

    protected final int col, row;

    /**
     * Crea la coordenada con la
     * respectiva fila y columna
     * @param row fila
     * @param col columna
     */
    public Coord(int row, int col) {
        this.col = col;
        this.row = row;
    }
}
