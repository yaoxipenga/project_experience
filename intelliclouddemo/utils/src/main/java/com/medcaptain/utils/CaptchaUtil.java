package com.medcaptain.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成验证码工具类
 *
 * @author bingwen.ai
 */
public class CaptchaUtil {
    private static final char[] CAPTCHA_CHARS = new char[]{'A', '7', 'a', 'd', 'e', 'r', 'C', '5', 'E', 'F', 'H', 'J',
            'K', '8', 'L', 'M', 'N', '3', '4', '6', '9', 'P', 'R', 'T', 'U', 'W', 'X', 'Y'};

    /**
     * 随机生成验证码
     *
     * @param width         验证码图像的宽度
     * @param height        验证码图像的高度
     * @param captchaLength 验证码长度
     * @return 带有验证图片的Map
     */
    public static Map<String, Object> getCaptchaImage(int width, int height, int captchaLength) {
        Map<String, Object> returnMap = new HashMap<>(2);
        int imageWidth = width <= 0 ? 80 : width;
        int imageHeight = height <= 0 ? 30 : height;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        String verifyCode = generateCaptcha(captchaLength);
        drawCaptcha(graphics, verifyCode);
        graphics.dispose();
        returnMap.put("image", image);
        returnMap.put("verifyCode", verifyCode);
        return returnMap;
    }

    public static String generateCaptcha(int captchaLength) {
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < captchaLength; i++) {
            captcha.append(CAPTCHA_CHARS[RandomUtil.nextInt(CAPTCHA_CHARS.length)]);
        }
        return captcha.toString();
    }

    private static void drawCaptcha(Graphics graphics, String captcha) {
        for (int i = 0; i < captcha.length(); i++) {
            Font letterFont = FontUtil.getRandomFont();
            Color letterColor = getRandomColor(0, 200);
            graphics.setFont(letterFont);
            graphics.setColor(letterColor);
            String letter = captcha.substring(i, i + 1);
            int yOffset = RandomUtil.nextInt(18);
            int xOffset = RandomUtil.nextInt(-8, 8);
            graphics.drawString(letter, 10 + 18 * i + xOffset, 20 + yOffset);
        }
    }

    private static Color getRandomColor(int from, int to) {
        int minColorDensity = from > 255 ? 255 : from;
        int maxColorDensity = to > 255 ? 255 : to;
        minColorDensity = minColorDensity < 0 ? 0 : minColorDensity;
        maxColorDensity = maxColorDensity < 0 ? 0 : maxColorDensity;
        int r = RandomUtil.nextInt(minColorDensity, maxColorDensity);
        int g = RandomUtil.nextInt(minColorDensity, maxColorDensity);
        int b = RandomUtil.nextInt(minColorDensity, maxColorDensity);
        return new Color(r, g, b);
    }
}
