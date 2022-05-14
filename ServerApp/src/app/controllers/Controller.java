package app.controllers;

import app.Constants;
import app.models.GameLobby;
import app.models.GamesDB;
import app.models.match.Difficulty;
import app.models.match.Game;
import persistence.DataBaseReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static app.controllers.LogMessageBox.*;

/**
 * @author Samuel F. Ruiz
 * @since 11/09/20
 */
public class Controller {

    private ServerSocket server;
    private GameLobby lobby;

    /**
     * inicializa el controlador con el ServerSocket especificado
     * @param server socket del servidor
     * @see ServerSocket
     */
    public Controller(ServerSocket server) {
        this.server = server;
        lobby = new GameLobby();
        loadDataBase();
        initConnection();
    }

    /**
     * carga todos los sudokus del archivo CSV
     * y los almacena en el objeto de la
     * base de datos del hjuego
     * @see Game
     * @see GamesDB
     * @see DataBaseReader
     */
    private void loadDataBase() {
        System.out.println(LOADING_DATA);
        GamesDB gamesDB = new GamesDB();
        List<String[]> data = DataBaseReader.readDataBase(Constants.SUDOKUS_DATA_BASE_PATH);
        Difficulty difficulty;
        for (String[] line : data) {
            difficulty = Difficulty.valueOf(line[line.length - 2].toUpperCase());
            gamesDB.addGame(new Game(difficulty, line[0]));
        }
        lobby.setDataBase(gamesDB);
    }

    /**
     * Crea e inicializa los hilos de
     * las conexiones de juego entrantes
     * @see PlayerThread
     */
    public void initConnection() {
        try {
            while (!server.isClosed()) {
                System.out.println(WAITING_CONNECTION);
                Socket socket = server.accept();
                PlayerThread connection = new PlayerThread(socket, lobby);
                System.out.println(CONNECTED_TO_SOCKET + socket);
                connection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
