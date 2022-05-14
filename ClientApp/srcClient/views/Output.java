package views;

import javax.swing.JOptionPane;

public class Output {

    public static void showMessage(String message){
        JOptionPane.showMessageDialog(null, message,"",JOptionPane.PLAIN_MESSAGE);
    }

    public static void showErrorMessage(String message){
        JOptionPane.showConfirmDialog(null, message,
                "",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(String message){
        JOptionPane.showConfirmDialog(null, message,
                "",
                JOptionPane.DEFAULT_OPTION);
    }
}
