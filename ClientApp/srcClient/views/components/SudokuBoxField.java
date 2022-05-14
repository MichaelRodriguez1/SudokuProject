package views.components;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Samuel F. Ruiz
 * @since 13/09/20
 */
public class SudokuBoxField extends JTextField {

    private static final SudokuBoxAdapter KEY_ADAPTER = new SudokuBoxAdapter();

    /**
     * Iniciaizar un TextField perzonalizado
     */
    public SudokuBoxField() {
        setSettings();
    }

    public void setSettings() {
        this.setTransferHandler(null);//Evita el copy paste
        this.addKeyListener(KEY_ADAPTER);
    }

    private static class SudokuBoxAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            char keyChar = e.getKeyChar();
            SudokuBoxField field = (SudokuBoxField) e.getSource();
            if (Character.isDigit(keyChar)) {
                if (!field.getText().isEmpty()) {
                    field.setText("");
                }
                if (keyChar == '0') e.consume();
            } else e.consume();
        }
    }
}
