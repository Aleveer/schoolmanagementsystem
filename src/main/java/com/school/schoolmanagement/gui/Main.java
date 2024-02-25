package com.school.schoolmanagement.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

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

        panelButton = new javax.swing.JPanel();
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

        buttonStudentGrade.setText("Studen Grade");
        panelButton.add(buttonStudentGrade);

        CourseManagement courseManagement = new CourseManagement();
        getContentPane().add(courseManagement, BorderLayout.CENTER);
        getContentPane().add(panelButton, java.awt.BorderLayout.PAGE_END);

        pack();
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

    private ActionListener changePanelCourseInstructor = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            CourseInstructorManagement courseInstructorManagement = new CourseInstructorManagement(); 
            Container contentPane = Main.getInstance().getContentPane();
            Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            Main.getInstance().remove(centerComponent);
            Main.getInstance().revalidate();
            Main.getInstance().repaint();

            Main.getInstance().getContentPane().add(courseInstructorManagement, BorderLayout.CENTER);
            Main.getInstance().revalidate();
            Main.getInstance().repaint();
        }
    };
    private ActionListener changePanelCourse = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            CourseManagement courseManagement = new CourseManagement();
            Container contentPane = Main.getInstance().getContentPane();
            Component centerComponent = ((BorderLayout) contentPane.getLayout()).getLayoutComponent(BorderLayout.CENTER);
            Main.getInstance().remove(centerComponent);
            Main.getInstance().revalidate();
            Main.getInstance().repaint();

            Main.getInstance().getContentPane().add(courseManagement, BorderLayout.CENTER);
            Main.getInstance().revalidate();
            Main.getInstance().repaint();
        }
    };
    
}
