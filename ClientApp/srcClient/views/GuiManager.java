package views;

import clientController.Actions;
import com.formdev.flatlaf.FlatIntelliJLaf;
import views.components.MyJButton;
import views.components.MyJMenuBar;
import views.components.PanelBoard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 24/08/20
 **/
public class GuiManager extends JFrame implements Texts {

    private final ActionListener listener;
    private PanelBoard panelBoard;
    private MyJButton btnCheck;
    private MyJButton btnExitMatch;
    private MyJMenuBar myJMenuBar;
    private JTextField namePlayer;
    private JLabel labelNamePlayer;
    private JLabel labelGames;
    private JTextField textGames;
    private MyJButton btnClear;

    /**
     * Contructor que inicializa el frame principal
     *
     * @param listener       Se encarga de escuchar las acciones de cada boton
     * @param color          Con el cual se le inicia los coleres aleatorios a la vista
     * @param windowListener Escucha cuando se cierra la ventana
     */
    public GuiManager(ActionListener listener, Color color, WindowListener windowListener) throws HeadlessException {
        super(TITTLE_APP);
        this.listener = listener;
        setSize(700, 520);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(windowListener);
        setLocationRelativeTo(null);
        setResizable(false);
        this.getContentPane().setBackground(color);
        init();
    }

    /**
     * Pinta el tablero con el que el jugador va a interactuar
     *
     * @param pattern patron con el cual se va a pintar el tabler
     */
    public void fillBoard(String pattern) {
        this.panelBoard.clearTotalBoard();
        this.panelBoard.fillBoard(pattern);
        this.btnCheck.setEnabled(true);
    }


    /**
     * @return tablero con el patron que el usuario completo
     */
    public String getPattern() {
        return this.panelBoard.getPattern();
    }

    /**
     * Seetea el campo que muestra cuantos tableros a completado el usuario
     *
     * @param boardsMade Tableros realizados por el usuario
     */
    public void increaseMadeBoards(int boardsMade) {
        this.textGames.setText(String.valueOf(boardsMade));
    }

    /**
     * Inicializa los atributos del frame
     * Seetea el lookAndFeel predeterminado por el sistema
     */
    private void init() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.panelBoard = new PanelBoard();
        this.btnCheck = new MyJButton(BTN_CHECK, listener, Actions.BTN_CHECK1);
        this.myJMenuBar = new MyJMenuBar(listener);
        this.labelNamePlayer = new JLabel(TEXT_LABEL_PLAYER);
        this.labelNamePlayer.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));
        this.namePlayer = new JTextField();
        this.namePlayer.setEditable(false);
        this.namePlayer.setEnabled(false);
        this.namePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        this.namePlayer.setBorder(new LineBorder(Color.BLACK, 2));
        this.labelGames = new JLabel(TEXT_LABEL_GAMES);
        labelGames.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));
        this.textGames = new JTextField();
        this.textGames.setEnabled(false);
        this.textGames.setHorizontalAlignment(SwingConstants.CENTER);
        this.textGames.setEditable(false);
        this.textGames.setBorder(new LineBorder(Color.BLACK, 2));
        this.btnCheck.setEnabled(false);
        this.btnClear = new MyJButton(TEXT_BTN_CLEAN, listener, Actions.BTN_CLEAN);
        this.btnExitMatch = new MyJButton(BTN_EXIT_MATCH, listener, Actions.EXIT_MATCH);
        fill();
    }

    /**
     * Limpa el tablero sin tener en cuenta las casillas que estan fijas
     */
    public void clearBoard() {
        this.panelBoard.clearBoard();
    }

    /**
     * Cambia el campo que muestra el alias del jugador
     *
     * @param namePlayer nombre del jugador
     */
    public void setNamePlayer(String namePlayer) {
        this.namePlayer.setText(namePlayer);
    }

    /**
     * Cambia el campo que muestra el numero de tablero completados del jugador
     *
     * @param games tableros completados por jugador
     */
    public void setTextGames(int games) {
        this.textGames.setText(String.valueOf(games));
    }

    /**
     * Ubica cada uno de los componentes que se quieren mostrar en el frame
     */
    private void fill() {
        this.setLayout(null);
        setJMenuBar(this.myJMenuBar);
        labelNamePlayer.setBounds(485, 50, 170, 35);
        namePlayer.setBounds(485, 85, 170, 35);
        labelGames.setBounds(485, 120, 170, 35);
        textGames.setBounds(485, 155, 170, 35);
        btnExitMatch.setBounds(485, 290, 170, 50);
        btnCheck.setBounds(485, 390, 170, 50);
        btnClear.setBounds(485, 340, 170, 50);
        panelBoard.setBounds(20, 20, 450, 420);
        add(btnClear);
        add(panelBoard);
        add(btnCheck);
        add(labelNamePlayer);
        add(namePlayer);
        add(labelGames);
        add(textGames);
        add(btnExitMatch);
    }

    /**
     * Limpia por completo el tablero de juego
     */
    public void clearTotalBoard() {
        setTextGames(0);
        setNamePlayer("");
        this.panelBoard.clearTotalBoard();
        setNamePlayer("");
        setTextGames(0);
    }
}
