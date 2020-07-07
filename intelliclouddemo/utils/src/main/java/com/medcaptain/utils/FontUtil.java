package com.medcaptain.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.LinkedList;

/**
 * 字体工具类
 *
 * @author bingwen.ai
 */
public class FontUtil {
    private static String[] fontNames = {"broadway", "georgia", "mvboli", "perpetua", "segoe", "stencil", "times", "verdana"};

    private static Font defaultFont = null;

    private static LinkedList<Font> availableFonts = new LinkedList<>();

    static {
        loadFonts();
    }

    /**
     * 从下列备选向中随机获取一个字体
     *
     * <li>Broadway</li>
     * <li>Georgia</li>
     * <li>Mvboli</li>
     * <li>Perpetua</li>
     * <li>Segoe</li>
     * <li>Stencil</li>
     * <li>Times</li>
     * <li>Verdana</li>
     *
     * @return 字体对象
     */
    public static Font getRandomFont() {
        int fontIndex = RandomUtil.nextInt(availableFonts.size());
        Font availableFont = availableFonts.get(fontIndex);
        return availableFont != null ? availableFont : defaultFont;
    }

    private static void loadFonts() {
        String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        if (availableFontFamilyNames.length > 0) {
            defaultFont = new Font(availableFontFamilyNames[0], Font.PLAIN, 26);
        }

        for (int i = 0; i < fontNames.length; i++) {
            String chosenFontName = fontNames[i];
            try {
                InputStream fontFileStream = CaptchaUtil.class.getClassLoader().getResourceAsStream(chosenFontName + ".ttf");
                Font font = Font.createFont(Font.TRUETYPE_FONT, fontFileStream);
                availableFonts.add(font.deriveFont(Font.PLAIN, 26));
            } catch (Exception ex) {
                //TODO 记录日志
            }
        }
    }
}
