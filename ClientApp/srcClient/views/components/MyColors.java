package views.components;

import java.awt.*;
import java.util.Random;

/**
 * @author Samuel F. Ruiz
 * @since 13/09/20
 */
public class MyColors {

    public static final Color MY_RED = new Color(231, 19, 44);
    public static final Color MY_GREEN = new Color(44, 175, 16);
    public static final Color MY_WHITE = Color.BLACK;
    public static final Color COLOR_BASE_BACKGROUND = new Color(56, 141, 156);

    /**
     * Genera colores aleatorios con base a un color base
     *
     * @param mix Color base
     * @return Color aleatorio
     */
    public static Color generateRandomColor(Color mix) {
        Random random = new Random();

        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        // mix the color
        if (mix != null) {
            red = (red + mix.getRed()) / 2;
            green = (green + mix.getGreen()) / 2;
            blue = (blue + mix.getBlue()) / 2;
        }
        return new Color(red, green, blue);
    }

}
