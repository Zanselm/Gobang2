package utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Anselm
 * @date 2024/2/8 12 14
 * description 加载字体
 */
public class FontLoader {
    static Font font;

    static {
        try {
            InputStream inputStream = Objects.requireNonNull(FontLoader.class.getClassLoader().getResourceAsStream("font/fei_mo_shou_shu.ttf"));
            Font f = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            font = f.deriveFont(30f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    /**
     * @return 返回一个默认大小为30的字体
     */
    public static Font getFont() {
        return font;
    }

    /**
     * @param size 字体大小
     * @return 返回指定大小的字体
     */
    public static Font getFont(float size) {
        return font.deriveFont(size);
    }
}
