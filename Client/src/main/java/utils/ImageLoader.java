package utils;

import org.jetbrains.annotations.NotNull;
import ui.zui.ZMainButton;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/3/6 17 29
 * description
 */

public class ImageLoader {
    private ImageLoader() {}
    public static @NotNull ImageIcon load(String path){
        URL URL = Objects.requireNonNull(ZMainButton.class.getClassLoader().getResource(path));
        return new ImageIcon(URL);
    }

}
