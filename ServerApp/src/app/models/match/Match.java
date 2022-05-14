package app.models.match;

import app.models.board.SudokuBoard;
import util.stack.Stack;

public class Match {

    private final Difficulty difficulty;
    private final Stack<Game> games;
    private final Player host;
    private Player player2;

    /**
     * Crea una partida con los juegos la dificultad y su jugador host
     * @param games cola de juegos
     * @param difficulty dificultad de los juegos
     * @param host jugador principal
     * @see Stack
     * @see Difficulty
     * @see Player
     */
    public Match(Stack<Game> games, Difficulty difficulty, Player host) {
        this.difficulty = difficulty;
        this.games = games;
        this.host = host;
        setNextGame(this.host);
        this.host.setStatus(PlayerStatus.PLAYING);
    }

    /**
     * asigna el segundo jugador a la partida
     * @param player2 jugador 2
     */
    public synchronized void setPlayer2(Player player2) {
        this.player2 = player2;
        if (this.player2 != null) {
            setNextGame(this.player2);
            this.player2.setStatus(PlayerStatus.PLAYING);
        }
    }

    /**
     * Saca el siguiente tablero disponible en la partida y lo asigna
     * al jugador especificado, si no hay más tableros verifica si va empatado con su
     * oponente le asigna el jugador del oponente
     * @param player jugador al cual se le asignará el siguiente tablero
     * @return si se asignó un nuevo tablero al jugador
     */
    public synchronized boolean setNextGame(Player player) {
        if (hasGames()) {
            SudokuBoard board = games.pop().getBoard();
            player.setBoard(board);
            return true;
        } else {
            Player opponent = getOpponent(player);
            if (player.getStatus().equals(PlayerStatus.PLAYING)){
                if (opponent.getBoardsCompleted() == player.getBoardsCompleted()) {
                    player.setBoard(opponent.getBoard());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * si la partida contiene juegos disponibles
     * @return si la cola de juegos no está vacia
     */
    public synchronized boolean hasGames() {
        return !games.isEmpty();
    }

    /**
     * @return si la casilla del jugador 2 está disponble
     */
    public synchronized boolean isAvailable() {
        return player2 == null;
    }

    /**
     * Retorna al jugador con más tableros completados hasta el momento,
     * si van empatados retorna null
     * @return jugador con mas tableros o null
     */
    public synchronized Player getWinner() {
        int completedP1 = host.getBoardsCompleted();
        int completedP2 = player2.getBoardsCompleted();
        if (completedP1 != completedP2) {
            return completedP1 > completedP2 ? host : player2;
        }
        return null;
    }

    /**
     * segun el jugador ingresado busca que sea diferente en la partida, el cual
     * es el oponente, si el jugador especificado no se encuentra en la partida retorna null
     * @param player jugador el cual se compará con los de la partida
     * @return jugador diferente al especificado en la partida
     */
    public synchronized Player getOpponent(Player player){
        if (player.equals(host) || player.equals(player2)){
            return player.equals(host)? player2: host;
        }
        return null;
    }

    /**
     * @return dificultad de los juegos de la partida
     * @see Difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Verifica si el jugador especificado ya está en la partida
     * @param player jugador a verificar
     * @return si hay un jugador igual o no en la partida
     */
    public synchronized boolean isInThisMatch(Player player){
        if (isAvailable()){
            return host.equals(player);
        }
        return host.equals(player) || player2.equals(player);
    }

    /**
     * cambia el estado del jugador especificado a HAS_LEFT
     * @param player jugador que abandona la partida
     * @see PlayerStatus
     */
    public synchronized void leaveGame(Player player){
        if (player != null){
            if (player.equals(host) || player.equals(player2)){
                player.setStatus(PlayerStatus.HAS_LEFT);
            }
        }
    }
}
