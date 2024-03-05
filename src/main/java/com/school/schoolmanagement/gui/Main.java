package com.school.schoolmanagement.gui;
import com.school.schoolmanagement.gui.components.MenuButton;
import com.school.schoolmanagement.gui.components.MenuPanel;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        panelMenu = new MenuPanel();
        panelMenu.setBorder(BorderFactory.createLineBorder(Color.black));
        panelMenu.setBorder(new EmptyBorder(5,5,5,5));

        panelButton = new MenuPanel();
        panelButton.setLayout(new GridLayout(3,1, 0,5));

        buttonCourse = new MenuButton("Course", activeBtn);
        buttonCourseInstructor = new MenuButton("Course Instructor", activeBtn);
        buttonStudentGrade = new MenuButton("Student Grade", activeBtn);

        buttonCourse.setBtn1(buttonCourseInstructor);
        buttonCourse.setBtn2(buttonStudentGrade);

        buttonCourseInstructor.setBtn1(buttonCourse);
        buttonCourseInstructor.setBtn2(buttonStudentGrade);

        buttonStudentGrade.setBtn1(buttonCourse);
        buttonStudentGrade.setBtn2(buttonCourseInstructor);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonCourse.addActionListener(changePanelCourse);
        panelButton.add(buttonCourse);

        buttonCourseInstructor.addActionListener(changePanelCourseInstructor);
        panelButton.add(buttonCourseInstructor);

        buttonStudentGrade.addActionListener(changePanelGrade);
        panelButton.add(buttonStudentGrade);

        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        header = new JLabel();

// Set font and text for label header
        header.setFont(new Font("sanserif", 1, 18));
        header.setForeground(new Color(255, 255, 255));
        header.setText("School System Management");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelMenu.add(header);
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(panelButton);
        panelMenu.add(Box.createVerticalGlue());

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

    private MenuButton buttonCourse;
    private MenuButton buttonCourseInstructor;
    private MenuButton buttonStudentGrade;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelMenu;
    private JLabel header;
    private final StringBuilder activeBtn = new StringBuilder("Course");

    private ActionListener changePanelCourseInstructor = arg0 -> {
        activeBtn.setLength(0);
        activeBtn.append(buttonCourseInstructor.getText());

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
        activeBtn.setLength(0);
        activeBtn.append(buttonCourse.getText());

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
        activeBtn.setLength(0);
        activeBtn.append(buttonStudentGrade.getText());

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
