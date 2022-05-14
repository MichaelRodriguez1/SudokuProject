package app.models.match;

import app.models.board.SudokuBoard;
import app.models.board.SudokuStatus;

public class Player{

    private int boardsCompleted;
    private PlayerStatus status;
    private String name;
    private SudokuBoard board;

    /**
     * crea un jugador con su nombre y tablero especificados
     * @param name nombre del jugador
     * @param board tablero
     */
    public Player(String name, SudokuBoard board) {
        this.name = name;
        this.board = board;
        status = PlayerStatus.WAITING_MATCH;
    }

    /**
     * crea un jugador con el nombre especificado y su tablero con todas las casillas
     * vacías
     * @param name nombre del jugador
     */
    public Player(String name) {
        this(name, new SudokuBoard());
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    /**
     * obtiene la cantidad acutal de tableros completados por el
     * jugador
     * @return cantidad de tableros completados
     */
    public synchronized int getBoardsCompleted() {
        return boardsCompleted;
    }


    public synchronized PlayerStatus getStatus(){
        return status;
    }

    public synchronized void addBoardCompleted(){
        boardsCompleted++;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }


    public synchronized void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * asigna los valores del tablero asignado a las casillas no fijas, o editables
     * del jugador y verifica el estado del tablero actual del jugador con los nuevos
     * valores
     * @param board tablero con los valores a reemplazar
     * @return estado del tablero con sus nuevos valores
     * @see SudokuStatus
     */
    public SudokuStatus checkSudoku(SudokuBoard board) {
        if (this.board == null || board == null) return null;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!this.board.get(i, j).isFix()) {
                    this.board.setValue(board.get(i,j));
                }
            }
        }
        return this.board.checkSudoku();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player){
            return ((Player) obj).name.equals(name);
        }
        return false;
    }
}
