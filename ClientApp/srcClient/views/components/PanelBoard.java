package views.components;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.stream.IntStream;

/**
 * @author Michael S. Rodriguez
 * @version 1.0
 * @since 1/09/20
 **/
public class PanelBoard extends JPanel {

    private final SudokuBoxField[] boxes;

    /**
     * Inicializa cada una de las casillas del tablero
     * Agrega KeyListiner y FocusListiner a cada una de las casillas
     */
    public PanelBoard() {
        this.boxes = new SudokuBoxField[81];
        IntStream.range(0, boxes.length).forEachOrdered(i -> {
            boxes[i] = new SudokuBoxField();
            boxes[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if (Character.isDigit(e.getKeyChar())) {
                        highlight(String.valueOf(e.getKeyChar()));
                        setBoxColor((SudokuBoxField) e.getSource(), MyColors.MY_RED);
                    }
                }
            });
            boxes[i].setEditable(false);
            boxes[i].setEnabled(false);
            boxes[i].addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    super.focusGained(e);
                    highlight(((JTextField) (e.getComponent())).getText());
                }
            });

        });
        this.setSize(450, 420);
        init();
    }

    /**
     * Subraya las casillas que tengan determinado valor
     *
     * @param value valor de las casillas que se van a subrayar
     */
    private void highlight(String value) {
        if (!value.equalsIgnoreCase("")) {
            for (SudokuBoxField box : this.boxes) {
                if (box.getText().equalsIgnoreCase(value)) {
                    setBoxColor(box, MyColors.MY_RED);
                } else {
                    setBoxColor(box, box.isEnabled() ? MyColors.MY_WHITE : MyColors.MY_GREEN);
                }
            }
        } else for (SudokuBoxField box : boxes) {
            setBoxColor(box, box.isEnabled() ? MyColors.MY_WHITE : MyColors.MY_GREEN);
        }
    }


    /**
     * Cambia el color de una determinada casilla
     *
     * @param box   casilla a la cual se le va a cambiar el color
     * @param color color con el cual se va a pintar la casilla
     */
    private void setBoxColor(SudokuBoxField box, Color color) {
        if (box.isEnabled()) {
            box.setForeground(color);
        } else {
            box.setForeground(color);
            box.setDisabledTextColor(color);
        }
    }

    /**
     * Inicializa cada unos de los compenentes de este panel
     */
    private void init() {
        GridLayout gridLayout = new GridLayout(9, 9, 1, 1);
        this.setLayout(gridLayout);
        setBorder(new LineBorder(Color.BLACK, 3));
        for (SudokuBoxField boxField : boxes) {
            boxField.setFont(new Font("Arial", Font.BOLD, 20));
            boxField.setHorizontalAlignment(JTextField.CENTER);
            this.add(boxField);
        }
    }

    /**
     * Cambiar el valor de casa casilla segun corresponda en el patron
     *
     * @param pattern patron que se va a pintar en el tablero
     */
    public void fillBoard(String pattern) {
        int bound = boxes.length;
        IntStream.range(0, bound).forEach(i -> {
            if (pattern.charAt(i) != '.') {
                boxes[i].setEnabled(false);
                boxes[i].setEditable(false);
                boxes[i].setDisabledTextColor(MyColors.MY_GREEN);
                boxes[i].setText(String.valueOf(pattern.charAt(i)));
            } else {
                boxes[i].setEnabled(true);
                boxes[i].setEditable(true);
            }
        });
    }

    /**
     * @return el patro que se encuentra diligenciado por el usuario
     */
    public String getPattern() {
        StringBuilder builder = new StringBuilder();
        for (SudokuBoxField box : boxes) {
            if (box.getText().equalsIgnoreCase("")) builder.append('.');
            else builder.append(box.getText());
        }
        return builder.toString();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(298, 0, 3, this.getSize().height);
        g.fillRect(this.getSize().height / 3 + 9, 0, 3, this.getSize().height);
        g.fillRect(0, this.getSize().height / 3, this.getWidth(), 3);
        g.fillRect(0, this.getSize().height / 3 * 2 - 3, this.getWidth(), 3);
    }

    /**
     * Limpia el tablero totalmente
     */
    public void clearTotalBoard() {
        for (SudokuBoxField sudokuBoxField : this.boxes) {
            sudokuBoxField.setEnabled(false);
            sudokuBoxField.setText("");
        }
    }

    /**
     * Limpia el tablero a excepcion de las casillas que
     * se encuentran fijas
     */
    public void clearBoard() {
        for (SudokuBoxField box : this.boxes)
            if (box.isEnabled()) box.setText("");
            else this.setBoxColor(box, MyColors.MY_GREEN);
    }
}
