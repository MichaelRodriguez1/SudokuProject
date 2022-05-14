package app.models.board;

import java.util.Arrays;

public class SudokuBoard {

    private final SudokuBox[][] boxes;

    /**
     * inicializa un tablero con todas sus
     * casillas vacías
     * @see SudokuBox
     */
    public SudokuBoard() {
        boxes = new SudokuBox[9][9];
        for (int row = 0; row < boxes.length; row++) {
            for (int col = 0; col < boxes[0].length; col++) {
                boxes[row][col] = new SudokuBox(0,false,new Coord(row,col));
            }
        }
    }

    /**
     * asigna el valor a la casilla especificada y si es o no
     * una casilla fija del tablero
     * @param coord coordenada del dato
     * @param value nuevo valor de la casilla
     * @param isFix si es una casilla fija
     * @see Coord
     */
    public void setValue(Coord coord, int value, boolean isFix) {
        boxes[coord.row][coord.col].setValue(value);
        boxes[coord.row][coord.col].setFix(isFix);
    }

    /**
     * asigna la casilla especificada a la casilla
     * con la misma coordenada en el tablero, si la
     * casilla es null no cambia la casilla del tablero
     * @param box nueva casilla
     * @see SudokuBox
     */
    public void setValue(SudokuBox box) {
        if (box != null){
            setValue(box.getCoord(), box.getValue(), box.isFix());
        }
    }

    /**
     * Revisa si el tablero tiene casillas en blanco, contiene errores
     * o está bien desarrollado segun las reglas del sudoku estandar
     * @return estado del tablero
     * @see SudokuStatus
     */
    public SudokuStatus checkSudoku() {
        fullValidate();
        for (SudokuBox[] row: boxes) {
            if (Arrays.stream(row).anyMatch(box -> box.getValue() == 0)){
                return SudokuStatus.HAS_EMPTY_VALUES;
            }else if (Arrays.stream(row).anyMatch(box -> !box.isOk())){
                return SudokuStatus.WRONG;
            }
        }
        return SudokuStatus.OK;
    }

    /**
     * pasa por cada una de las casillas del tablero
     * asignado si cada casilla está en el lugar correcto o no
     */
    private void fullValidate(){
        for (SudokuBox[] row : boxes) {
            for (SudokuBox box: row) {
                //si la casilla no es fija procede a verificarla
                if(!box.isFix() && box.getValue() != 0){
                    //Evalua si el valor está en la columna, fila y sector
                    if(isAlready(box.getCoord(), box.getValue())){
                        box.setOk(false);
                    }
                }
            }
        }
    }

    /**
     * Verifica si el valor de la casilla ya está en la fila
     * columna o sector
     * @param coord coordenada del valor
     * @param value valor a verificar
     * @return si ya se encuentra o no
     */
    private boolean isAlready(Coord coord, int value){
        return isAlreadyInCol(coord, value) || isAlreadyInRow(coord, value) || isAlreadyInSector(coord, value);
    }

    /**
     * Verifica si el valor especificado se encuentra
     * en la columna de la coordenada dada
     * @param coord coordenada del valor
     * @param value valor a verficar
     * @return si ya hay otro valor similar en la columna
     */
    private boolean isAlreadyInCol(Coord coord, int value){
        for (int i = 0; i < this.boxes.length; i++) {
            if (i != coord.row && boxes[i][coord.col].getValue() == value){
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el valor especificado se encuentra
     * en la fila de la coordenada dada
     * @param coord coordenada del valor
     * @param value valor a verficar
     * @return si ya hay otro valor similar en la fila
     */
    private boolean isAlreadyInRow(Coord coord, int value){
        for (int i = 0; i < this.boxes.length; i++) {
            if(i != coord.col && boxes[coord.row][i].getValue() == value){
                return true;
            }
        }
        return false;
    }

    /**
     * verifica si el valor ya se encuentra en el sector de 3x3
     * según el sudoku estandar
     * @param coord coordenada del valor
     * @param value valor a verficar
     * @return si ya hay otro valor similar en el sector
     */
    public boolean isAlreadyInSector(Coord coord, int value){
        for (int i = (coord.row / 3)*3 ; i < (coord.row / 3)*3 + 3; i++) {
            for (int j = (coord.col / 3)*3; j < (coord.col / 3)*3 + 3; j++) {
                if(i != coord.row && j != coord.col && this.boxes[i][j].getValue() == value){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * obtiene la casilla de la columna y fila especificadas
     * @param row fila de la casilla
     * @param col columna de la casilla
     * @return casilla de la coordenada
     * @see SudokuBox
     */
    public SudokuBox get(int row, int col) {
        return boxes[row][col];
    }
}
