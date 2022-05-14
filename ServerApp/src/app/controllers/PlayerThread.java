package app.controllers;

import app.models.GameLobby;
import app.models.board.SudokuBuilder;
import app.models.board.SudokuStatus;
import app.models.match.*;
import util.ServerLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class PlayerThread extends Thread implements Action, LogMessageBox{

    public enum CloseCode{ERROR,OK, WRITE_ERROR, READ_ERROR}

    private final ServerLogger logger;
    private final Socket socket;
    private final GameLobby lobby;
    private DataInputStream input;
    private DataOutputStream output;
    private Match match;
    private Player player;

    /**
     * Crea el hilo en el por el cual se ejecuta la
     * interacción entre el cliente y el servidor
     * @param socket conexión con el cliente
     * @param lobby es el vestíbulo que contiene todos los juegos
     *              y la base de datos de los mismos
     * @see Socket
     * @see GameLobby
     */
    public PlayerThread(Socket socket, GameLobby lobby) {
        this.socket = socket;
        this.lobby = lobby;
        logger = new ServerLogger();
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            close(CloseCode.ERROR);
        }
    }

    /**
     * Escribe y manda las cadenas de texto a
     * los clientes
     * @param text orden o mensaje que se envía
     */
    public void writeUTF(String text) {
        try {
            output.writeUTF(text);
        } catch (IOException e) {
            e.printStackTrace();
            close(CloseCode.WRITE_ERROR);
        }
    }

    /**
     * lee las respuestas de los clientes
     * @return respuesta o mensaje del cliente
     */
    public String readUTF() {
        try {
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            close(CloseCode.READ_ERROR);
        }
        return null;
    }

    /**
     * cierra el socket e imprime la información
     * del codigo con el que finalizó la acción
     * @param code estado de cierre
     * @see CloseCode
     */
    public void close(CloseCode code) {
        try {
            logger.println(socket + CLOSED_TEXT + code);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * lleva a cabo la ejecución del hilo la cual está al tanto de
     * las peticiones del cliente
     * @see Thread
     */
    @Override
    public void run() {
        while (!socket.isClosed()){
            String action = readUTF();
            if (action != null) action(action);
        }
        logger.println(THREAD_PROCESS_FINISHED);
    }

    /**
     * Recibe y llama al metodo correspondiente según la
     * acción especificada
     * @param action acción o petición del cliente
     * @see Action
     */
    private void action(String action) {
        logger.println(ACTION_TEXT + action);
        switch (action){
            case SEARCH_MATCH:
                searchMatch();
                waitOpponent();
                break;
            case CHECK_BOARD:
                Player opponent = match.getOpponent(player);
                if (opponent.getStatus().equals(PlayerStatus.PLAYING)) {
                    checkSudoku();
                }else {
                    endMatch();
                }
                break;
            case EXIT:
            case EXIT_MATCH:
                exit();
                break;
        }
    }

    /**
     * Espera a que un oponente ingrese al juego o
     * a que llegue la orden de cerrar el juego por parte
     * del cliente
     */
    private void waitOpponent() {
        boolean waiting = true;
        logger.println(WAITING_OPPONENT);
        while (waiting && !socket.isClosed()){
            try {
                if (input.available() > 0 && readUTF().equals(EXIT)) {
                    waiting = false;
                    exit();
                } else {
                    waiting = match.isAvailable();
                    if (!waiting) {
                        startMatch();
                    }
                }
                TimeUnit.SECONDS.sleep(1);
                System.out.print(WAITING_PROCESS_DOT);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                waiting = false;
            }
        }
    }

    /**
     * Envia la orden y el patrón del tablero
     * actual del jugador al cliente
     */
    private void startMatch() {
        logger.println(STARTING_MATCH);
        writeUTF(START_MATCH);
        writeUTF(Game.getBoardPattern(player.getBoard()));
    }

    /**
     * inicializa el jugador del hilo y lo empareja
     * en una partida
     * @see GameLobby
     */
    private void searchMatch() {
        logger.println(GETTING_MATCH);
        String name = readUTF();
        player = new Player(name);
        Difficulty difficulty = Difficulty.get(readUTF());
        match = lobby.pairOff(player, difficulty);
        Thread.currentThread().setName(player.getName());
        logger.setName(getName());
    }

    /**
     * Recive y verifica el patron del tablero
     * para enviar la acción correspondiente a la
     * verificación del tablero
     * @see SudokuStatus
     */
    private void checkSudoku() {
        logger.println(CHECKING_MATCH);
        String pattern = readUTF();
        logger.println(pattern);
        logger.println(GETTING_STATUS);
        SudokuStatus status = player.checkSudoku(
                SudokuBuilder.buildSudoku(pattern, false)
        );
        if (status.equals(SudokuStatus.OK)){
            player.addBoardCompleted();
            sendNewBoard();
        }else {
            writeUTF(CONTINUE_MATCH);
            writeUTF(String.valueOf(status));
        }
    }

    /**
     * envia el patrón del siguiente juego disponible
     * en la partida y el estado en que se completo, o
     * dado el caso si no hay más juegos disponibles en la
     * partida informa al cliente de la finalización de
     * la partida
     */
    private void sendNewBoard() {
        if (match.setNextGame(player)){
            logger.println(NEXT_GAME_SET);
            writeUTF(CONTINUE_MATCH);
            writeUTF(String.valueOf(SudokuStatus.OK));
            logger.println(SENDING_NEW_BOARD);
            writeUTF(Game.getBoardPattern(player.getBoard()));
            writeUTF(String.valueOf(player.getBoardsCompleted()));
        }else {
            endMatch();
        }
    }

    /**
     * Envia la opción de cierre de partida por
     * y los datos acerca del la misma los cuales son
     * los tableros realizados por cada jugador y si ganó o perdió
     */
    public void endMatch(){
        logger.println(ENDING_MATCH);
        writeUTF(END_MATCH);
        Player opponent = match.getOpponent(player);
        writeUTF(String.valueOf(opponent.getBoardsCompleted()));
        writeUTF(String.valueOf(player.getBoardsCompleted()));
        if (opponent.getStatus() == PlayerStatus.HAS_LEFT) {
            logger.println(OPPONENT_HAS_LEFT);
            writeUTF(OPPONENT_IS_GONE);
        }else {
            player.setStatus(PlayerStatus.HAS_FINISHED);
            logger.println(SENDING_LAST_STATUS);
            Player winner = match.getWinner();
            writeUTF(winner.equals(player) ? WON : LOST);
        }
    }

    /**
     * Sale de la partida
     */
    private void exit(){
        logger.println(LEAVING_GAME);
        if (player.getStatus() != PlayerStatus.HAS_FINISHED) {
            match.leaveGame(player);
        }
        logger.println(REMOVING_MATCH);
        lobby.remove(match);
        logger.println(CLOSING_SOCKET);
        close(CloseCode.OK);
    }
}
