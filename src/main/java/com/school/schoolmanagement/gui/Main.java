package com.school.schoolmanagement.gui;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main extends javax.swing.JFrame {
    private static Main instance;

    public static Main getInstance() {
    if (instance == null) {
      instance = new Main();
    }
    return instance;
  }

  public static void setInstance(Main instance) {
    Main.instance = instance;
  }

    public Main() {
        initComponents();
    }
    private void initComponents() {
        panelMenu = new JPanel();
        panelMenu.setBorder(BorderFactory.createLineBorder(Color.black));

        panelButton = new javax.swing.JPanel();
        panelButton.setLayout(new GridLayout(3,1, 0,5));

        buttonCourse = new javax.swing.JButton();
        buttonCourseInstructor = new javax.swing.JButton();
        buttonStudentGrade = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonCourse.setText("Course");
        buttonCourse.addActionListener(changePanelCourse);
        panelButton.add(buttonCourse);

        buttonCourseInstructor.setText("Course Instructor");
        buttonCourseInstructor.addActionListener(changePanelCourseInstructor);
        panelButton.add(buttonCourseInstructor);

        buttonStudentGrade.setText("Student Grade");
        buttonStudentGrade.addActionListener(changePanelGrade);
        panelButton.add(buttonStudentGrade);

        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(panelButton);
        panelMenu.add(Box.createVerticalGlue());

        //        Set width and height of button
        for (Object btn : panelButton.getComponents()) {
            if (btn instanceof JButton) {
                ((JButton) btn).setPreferredSize(new Dimension(150,10));
                ((JButton) btn).setBackground(GlobalColor.getPrimaryColor());
                ((JButton) btn).setContentAreaFilled(false);
                ((JButton) btn).setOpaque(true);
            }
        }

        // Set color for panel menu
        panelMenu.setBackground(GlobalColor.getComplementaryColor());

        CourseManagement courseManagement = new CourseManagement();
        setLayout(new BorderLayout());
        add(panelMenu, BorderLayout.WEST);
        add(courseManagement, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
    public static void main(String args[]) {
        
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
            Main.getInstance().setVisible(true);
    }

    private javax.swing.JButton buttonCourse;
    private javax.swing.JButton buttonCourseInstructor;
    private javax.swing.JButton buttonStudentGrade;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelMenu;

    private ActionListener changePanelCourseInstructor = arg0 -> {
        CourseInstructorManagement courseInstructorManagement = new CourseInstructorManagement();
        Container contentPane = Main.getInstance().getContentPane();
        Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        Main.getInstance().remove(centerComponent);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();

        Main.getInstance().getContentPane().add(courseInstructorManagement, BorderLayout.CENTER);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();
    };
    private ActionListener changePanelCourse = arg0 -> {
        CourseManagement courseManagement = new CourseManagement();
        Container contentPane = Main.getInstance().getContentPane();
        Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        Main.getInstance().remove(centerComponent);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();

        Main.getInstance().getContentPane().add(courseManagement, BorderLayout.CENTER);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();
    };
    private ActionListener changePanelGrade = arg0 -> {
        StudentGrade studentGrade = new StudentGrade();
        Container contentPane = Main.getInstance().getContentPane();
        Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        Main.getInstance().remove(centerComponent);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();

        Main.getInstance().getContentPane().add(studentGrade, BorderLayout.CENTER);
        Main.getInstance().revalidate();
        Main.getInstance().repaint();
    };
    
}
