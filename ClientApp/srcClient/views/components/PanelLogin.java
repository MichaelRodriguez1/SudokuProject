package views.components;

import clientController.Actions;
import com.formdev.flatlaf.FlatIntelliJLaf;
import views.Texts;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 11/09/20
 **/
public class PanelLogin extends JFrame implements Texts {

    private final WindowListener windowListener;
    private final ActionListener listener;
    private JLabel labelName;
    private JTextField fieldGetName;
    private JButton btnAcceptLogin;
    private JComboBox<String> selectDifficulty;
    private JLabel labelDifficulty;
    private JLabel labelImage;
    private JDialog dialogWaitMatch;

    /**
     * Inicializa un frame personalizado
     *
     * @param listener       Oyente de acciones de los componentes
     * @param color          Color con el que se va a pintar el frame
     * @param windowListener Oyente de la ventana
     */
    public PanelLogin(ActionListener listener, Color color, WindowListener windowListener) {
        super(TITLE_LOGIN);
        this.listener = listener;
        this.getContentPane().setBackground(color);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 300);
        setLocationRelativeTo(null);
        this.setResizable(false);
        this.windowListener = windowListener;
        init();
        setVisible(true);
    }

    /**
     * Inicializa los componentes del frame
     * Agrega a los compenente el oyente
     */
    private void init() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.labelName = new JLabel(NAME_LOGIN);
        this.labelName.setHorizontalAlignment(SwingConstants.CENTER);
        this.fieldGetName = new JTextField();
        this.btnAcceptLogin = new JButton(BTN_LOGIN);
        this.btnAcceptLogin.addActionListener(listener);
        this.btnAcceptLogin.setActionCommand(Actions.BTN_ACCEPT_LOGIN);
        this.selectDifficulty = new JComboBox<>(DIFFICULTIES);
        this.labelDifficulty = new JLabel(SELECT_DIFFICULTY);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/GameIcon.jpg"));
        this.labelImage = new JLabel(imageIcon);
        this.labelImage.setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT)));
        this.labelImage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        labelImage.setSize(10, 10);
        labelDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
        this.dialogWaitMatch = new JDialog(this, "", true);
        dialogWaitMatch.setLayout(new GridLayout(1, 1, 10, 10));
        JLabel labelWaitMatch = new JLabel(WAIT_MATCH);
        labelWaitMatch.setHorizontalAlignment(SwingConstants.CENTER);
        dialogWaitMatch.setResizable(false);
        dialogWaitMatch.add(labelWaitMatch);
        dialogWaitMatch.addWindowListener(windowListener);
        this.dialogWaitMatch.setSize(250, 100);
        fill();
    }

    /**
     * Muestra el dialogo de esperar partida
     *
     * @param frame Frame del cual va a depender el Dialog
     */
    public void showDialogWaitMatch(JFrame frame) {
        this.dialogWaitMatch.setLocationRelativeTo(frame);
        dialogWaitMatch.setVisible(true);
    }

    /**
     * Oculta el dialog de esperar partida
     */
    public void hideDialogWaitMatch() {
        this.dialogWaitMatch.dispose();
    }

    /**
     * @return El nombre que el usuario diligenicio en el formulario
     */
    public String getName() {
        return this.fieldGetName.getText();
    }


    /**
     * @return obtiene la dificultad selecionada por el usuario
     */
    public String getDifficultySelected() {
        return String.valueOf(this.selectDifficulty.getSelectedItem());
    }

    /**
     * Agrega los compenentes que se quieren mostrar en el frame
     */
    private void fill() {
        Font font = new Font("TimesRoman", Font.BOLD, 15);
        this.setLayout(null);
        this.labelImage.setBounds(5, 5, 250, 250);
        add(this.labelImage);
        this.labelName.setBounds(260, 5, this.getWidth() - this.labelImage.getWidth() - 15, 30);
        this.labelName.setAlignmentX(SwingConstants.CENTER);
        this.labelName.setFont(font);
        add(this.labelName);
        this.fieldGetName.setBounds(260, 40, 270, 30);
        add(this.fieldGetName);
        this.labelDifficulty.setBounds(260, 75, 270, 30);
        this.labelDifficulty.setFont(font);
        add(this.labelDifficulty);
        this.selectDifficulty.setBounds(260, 110, 270, 30);
        add(this.selectDifficulty);
        this.btnAcceptLogin.setBounds(260, 205, 270, 50);
        add(this.btnAcceptLogin);
    }


}
