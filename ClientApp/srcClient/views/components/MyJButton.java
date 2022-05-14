package views.components;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 15/09/20
 **/
public class MyJButton extends JButton {
    /**
     * Inicializa un JButton con unos parametros especificos
     *
     * @param text          Texto que va a tener el boton
     * @param listener      Oyente del boton
     * @param actionCommand Comando que se le asigna al boton
     */
    public MyJButton(String text, ActionListener listener, String actionCommand) {
        super(text);
        addActionListener(listener);
        setActionCommand(actionCommand);
    }
}
