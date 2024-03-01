package com.school.schoolmanagement.gui;
import java.awt.Color;

public class GlobalColor {
    // Biến toàn cục
    private static final Color primaryColor = Color.decode("#40E0D0");
    private static final Color complementaryColor = Color.decode("#40E0E0");
    private static final Color textColor = Color.decode("#FFFFFF");
    private static final Color buttonColor = Color.decode("#000000");

    // Phương thức để lấy màu
    public static Color getPrimaryColor() {
        return primaryColor;
    }
    public static Color getComplementaryColor() {
        return complementaryColor;
    }
    public static Color getTextColor() {
        return textColor;
    }
    public static Color getButtonColor() {
        return buttonColor;
    }
}

