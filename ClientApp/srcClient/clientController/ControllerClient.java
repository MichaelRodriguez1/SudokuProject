package clientController;

import views.GuiManager;
import views.Output;
import views.Texts;
import views.components.MyColors;
import views.components.PanelLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 24/08/20
 **/
public class ControllerClient implements ActionListener, Actions, Texts {

    private final GuiManager guiManager;
    private final PanelLogin panelLogin;
    private static final int PORT = 23370;
    private static final String HOST = "localHost";
    private DataInputStream input;
    private DataOutputStream output;
    private Socket socket;
    private volatile boolean isWaiting;


    /**
     * Inicializa el frame del login y el frame
     * en el que se encuentra el tablero del juego
     */
    public ControllerClient() {
        this.panelLogin = new PanelLogin(this,
                MyColors.generateRandomColor(MyColors.COLOR_BASE_BACKGROUND),
                new ControllerWindowAdapter());
        this.guiManager = new GuiManager(this,
                MyColors.generateRandomColor(MyColors.COLOR_BASE_BACKGROUND),
                new ControllerWindowAdapter());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case Actions.BTN_ACCEPT_LOGIN:
                acceptLogin();
                break;
            case BTN_ABOUT:
                Output.showInfoMessage(ABOUT_APP);
                break;
            case Actions.BTN_HOW_PLAY:
                actionHowPlay();
                break;
            case Actions.BTN_CHECK1:
                actionButtonCheck();
                break;
            case Actions.EXIT_MATCH:
                this.exitMatch();
                break;
            case Actions.BTN_CLEAN:
                guiManager.clearBoard();
                break;
        }
    }

    /**
     * Este metodo se encarga de seguir en ejecucion
     * mientras el servidor no haya emparejado al jugador
     * Lee la respuesta del servidor cada segundo
     */
    private void waitMatch() {
        isWaiting = true;
        new Thread(() -> {
            while (isWaiting) {
                try {
                    if (input.available() > 0) {
                        this.initMatch();
                    }
                    TimeUnit.SECONDS.sleep(1);
                } catch (IOException | InterruptedException e) {
                    Output.showErrorMessage(ERROR_WAITING);
                }
            }
        }).start();
    }

    /**
     * Sale de la partida
     * Le escribe al servidor de que se
     * salio de la partida y cierra el socket
     */
    private void exitMatch() {
        isWaiting = false;
        if (socket.isConnected()) {
            this.writeUTF(Actions.EXIT);
            try {
                socket.close();
                panelLogin.setVisible(true);
                guiManager.dispose();
                guiManager.clearTotalBoard();
            } catch (IOException e) {
                Output.showErrorMessage(ERROR_NOT_CLOSE_MATCH);
            }
        }
    }


    /**
     * Escribe en el canal de salida la accion de
     * Verificar tablero y el tablero que el usuario desea verificar
     * Espera la respuesta del servidor la cual puede ser si el
     * tablero esta bien resulto, esta mal o faltan algunas
     * casillas por rellenar
     */
    private void actionButtonCheck() {
        writeUTF(CHECK_BOARD);
        writeUTF(guiManager.getPattern());
        String option = getInputString();
        if (option != null) {
            switch (option) {
                case CONTINUE_MATCH:
                    continueMatch();
                    break;
                case END_MATCH:
                    endMatch();
                    break;
            }
        }
    }

    /**
     * Finaliza la ejecucio de la partida diciendole
     * al jugador si perdio, gano o su oponente
     * abandono la partida
     * Muestra una ventana los resultados de la partida
     */

    private void endMatch() {
        int boardsOpponent = Integer.parseInt(getInputString());
        int boardsThatIMade = Integer.parseInt(getInputString());
        guiManager.setTextGames(boardsThatIMade);
        String answerServer = getInputString();
        guiManager.setTextGames(boardsThatIMade);
        switch (answerServer) {
            case WON:
                Output.showMessage(MESSAGE_WINNER);
                break;
            case LOST:
                Output.showMessage(MESSAGE_LOSER);
                break;
            case OPPONENT_IS_GONE:
                Output.showInfoMessage(MESSAGE_OPPONENT_LEAVE_MATCH);
                break;
        }

        Output.showMessage(BOARDS_MADE_OPPONENT
                + boardsOpponent
                + BOARDS_MADE
                + boardsThatIMade);
        this.exitMatch();
    }

    private void continueMatch() {
        String optionContinue = this.getInputString();
        switch (optionContinue) {
            case OPTION_OK:
                Output.showInfoMessage(BOARD_OK);
                guiManager.fillBoard(getInputString());
                guiManager.increaseMadeBoards(Integer.parseInt(getInputString()));
                break;
            case OPTION_WRONG:
                Output.showInfoMessage(BOARD_INCORRECT);
                break;
            case OPTION_EMPTY_VALUES:
                Output.showInfoMessage(TEXT_BOARD_INCOMPLETE);
                break;
        }
    }

    /**
     * @return String que se va a leer del canal de entrada
     */
    private String getInputString() {
        String result = null;
        try {
            result = input.readUTF();
        } catch (IOException e) {
            Output.showErrorMessage(ERROR_NOT_READ);
        }
        return result;
    }

    /**
     * Escribe un String dentro del canal de salida
     *
     * @param action El String que se va a escribir
     */
    private void writeUTF(String action) {
        try {
            output.writeUTF(action);
        } catch (IOException e) {
            Output.showErrorMessage(ERROR_NOT_WRITE);
        }
    }

    /**
     * Muestra las instrucciones cuando se presiona el boton COMO JUGAR?
     */
    private void actionHowPlay() {
        Output.showInfoMessage(INSTRUCTIONS);
    }

    /**
     * Se ejecuta cuando se presiona el boton ACEPTAR en la ventana de Login
     */
    private void acceptLogin() {
        String name = panelLogin.getName().trim();
        if (!name.isEmpty()) {
            try {
                this.socket = new Socket(HOST, PORT);
                if (socket.isConnected()) {
                    this.output = new DataOutputStream(socket.getOutputStream());
                    this.input = new DataInputStream(socket.getInputStream());
                    if (output != null) {
                        startMatch(name);
                    }
                }
            } catch (IOException e) {
                Output.showErrorMessage(SERVER_NOT_FOUND);
            }
        } else Output.showInfoMessage(MESSAGE_NAME_EMPTY);

    }


    /**
     * Inicia la partida del jugador, muestra una ventana
     * a la espera del emparejamiento de otro jugador
     * Escribe en el canal de salia que ha iniciado
     * la espera, el nombre y la difultada selecionada por el jugador
     *
     * @param name alias de como el jugador desee loguearse
     */
    private void startMatch(String name) {
        this.writeUTF(SEARCH_MATCH);
        this.writeUTF(name);
        this.writeUTF(panelLogin.getDifficultySelected());
        this.panelLogin.setVisible(false);
        this.guiManager.setVisible(true);
        this.waitMatch();
        panelLogin.showDialogWaitMatch(guiManager);
        this.guiManager.setNamePlayer(panelLogin.getName());
    }

    /**
     * Termina la espera de la ventana de esperar partida
     * setea el contador de juegos a 0 y rellena el
     * tablaro que se va a resolver por el usuario
     * Oculta la ventana de esperar partida
     */
    private synchronized void initMatch() {
        isWaiting = false;
        if (Objects.equals(getInputString(), Actions.START_MATCH)) {
            guiManager.fillBoard(getInputString());
            panelLogin.hideDialogWaitMatch();
            guiManager.setTextGames(0);
        }
    }

    private class ControllerWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            if (output != null) exitMatch();
        }
    }
}
