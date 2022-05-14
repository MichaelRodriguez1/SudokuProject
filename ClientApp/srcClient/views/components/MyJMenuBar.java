package views.components;

import clientController.Actions;
import views.Texts;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 1/09/20
 **/
public class MyJMenuBar extends JMenuBar implements Texts {

    private JMenu menuHelp;
    private JMenuItem about;
    private JMenuItem howPlay;
    private ActionListener listener;

    /**
     * Inicializa un JMenuBar personalizado
     *
     * @param listener Oyente de los botones de la barra
     */
    public MyJMenuBar(ActionListener listener) {
        this.listener = listener;
        init();

    }

    /**
     * Inicializa los componentes que van a estar dentro de la barra
     */
    private void init() {
        this.menuHelp = new JMenu(BTN_HELP);
        this.about = new JMenuItem(BTN_ABOUT_APP);
        this.howPlay = new JMenuItem(BTN_HOW_PLAY);
        this.about.addActionListener(listener);
        this.about.setActionCommand(Actions.BTN_ABOUT);
        this.howPlay.addActionListener(listener);
        howPlay.setActionCommand(Actions.BTN_HOW_PLAY);
        fill();
    }

    /**
     * Añade los componenetes que se quieren mostrar en la barra
     */
    private void fill() {
        menuHelp.add(about);
        menuHelp.add(howPlay);
        add(menuHelp);
    }
}
