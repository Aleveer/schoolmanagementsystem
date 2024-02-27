package com.school.schoolmanagement.gui;
import java.awt.Color;

public class GlobalColor {
    // Biến toàn cục
    private static final Color primaryColor = Color.decode("#40E0D0");
    private static final Color complementaryColor = Color.decode("#40E0E0");
    private static final Color contrastColor = Color.decode("#E0E040");

    // Phương thức để lấy màu
    public static Color getPrimaryColor() {
        return primaryColor;
    }
    public static Color getComplementaryColor() {
        return complementaryColor;
    }
    public static Color getContrastColor() {
        return contrastColor;
    }
}

