package com.school.schoolmanagement.gui.components;

import javax.swing.*;
import java.awt.*;

public class MenuButton extends JButton {
    private final StringBuilder activeBtn;
    private final String btnText;
    private JButton btn1;
    private JButton btn2;

    public void setBtn1(JButton btn1) {
        this.btn1 = btn1;
    }

    public void setBtn2(JButton btn2) {
        this.btn2 = btn2;
    }

    public MenuButton(String text, StringBuilder activeBtn) {
        super(text);
        btnText = text;
        this.activeBtn = activeBtn;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        setFont(new Font("sanserif", 1, 12));
        setForeground(new Color(255,255,255));

        this.addActionListener(e -> {
            repaint();
            btn1.repaint();
            btn2.repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (activeBtn.toString().equals(btnText)) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(new Color(255, 0, 0, 50));
        }
        g.fillRect(0, 0, getSize().width, getSize().height);
        super.paintComponent(g);
        g.dispose();
    }
}
