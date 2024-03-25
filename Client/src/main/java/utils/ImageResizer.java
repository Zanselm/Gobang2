package utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Anselm
 * @date 2024/3/5 23 24
 * description
 */

public class ImageResizer {
    public static boolean HIGH = true;
    public static boolean WIDTH = false;

    private ImageResizer() {
    }

    @Contract("_, _, _ -> new")
    public static @NotNull ImageIcon resize(@NotNull ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        image = image.getScaledInstance(width, height, Image.SCALE_FAST);
        return new ImageIcon(image);
    }

    public static @NotNull ImageIcon resize(@NotNull ImageIcon imageIcon, int size, boolean direction) {
        Image image = imageIcon.getImage();
        if (direction == HIGH) {
            double ratio = (imageIcon.getIconWidth() * 1.0 / imageIcon.getIconHeight());
            image = image.getScaledInstance((int) (size * ratio), size, Image.SCALE_FAST);
        }
        if (direction == WIDTH) {
            {
                double ratio = (imageIcon.getIconHeight() * 1.0 / imageIcon.getIconWidth());
                image = image.getScaledInstance(size, (int) (size * ratio), Image.SCALE_FAST);
            }
        }
        return new ImageIcon(image);
    }
}
