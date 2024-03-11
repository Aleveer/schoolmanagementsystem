package com.school.schoolmanagement.gui.components;

import javax.swing.*;
import java.awt.*;

public class FormButton extends JButton {
    public FormButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFont(new Font("sanserif", 1, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Create a Graphics2D object from the Graphics object
        Graphics2D g2d = (Graphics2D) g.create();

        // If the button is being hovered over, change the color to light gray
        if (getModel().isRollover()) {
            g2d.setColor(Color.LIGHT_GRAY);
        } else {
            // Otherwise, set the background color to black
            g2d.setColor(Color.BLACK);
        }

        // Draw a rounded rectangle with the chosen color
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Set the text color to white
        setForeground(Color.WHITE);

        // Call the parent class's paintComponent method to paint the other components of the button
        super.paintComponent(g2d);

        // Dispose of the Graphics2D object to free up system resources
        g2d.dispose();
    }


}
