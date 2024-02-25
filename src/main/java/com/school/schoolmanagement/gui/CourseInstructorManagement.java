package com.school.schoolmanagement.gui;

import java.awt.*;

import javax.swing.*;

public class CourseInstructorManagement extends JPanel {

    public CourseInstructorManagement() {
        initComponents();
    }

    private void initComponents() {

        panelHeader = new JPanel();
        panelPerson = new JPanel();
        labelPersonID = new JLabel();
        comboBoxPersonID = new JComboBox<>();
        labelLastName = new JLabel();
        textFieldLastName = new JTextField();
        labelFirstName = new JLabel();
        textFieldFirstName = new JTextField();
        panelOnlineCourse = new JPanel();
        labelCourseID = new JLabel();
        comboBoxCourseID = new JComboBox<>();
        labelURL = new JLabel();
        textFieldURL = new JTextField();
        panelOnsiteCourse = new JPanel();
        labelOnsiteCourseID = new JLabel();
        comboBoxOnsiteCourseID = new JComboBox<>();
        labelLocation = new JLabel();
        textFieldLocation = new JTextField();
        labelDay = new JLabel();
        textFieldDay = new JTextField();
        labelTime = new JLabel();
        textFieldTime = new JTextField();
        panelButton = new JPanel();
        buttonAssign = new JButton();
        buttonNoAssign = new JButton();
        scrollPane = new JScrollPane();
        table = new JTable();

        setLayout(new BorderLayout());

        panelHeader.setLayout(new GridLayout(4, 0, 0, 10));

        panelPerson.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelPerson.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelPersonID.setText("Person ID");
        panelPerson.add(labelPersonID);

        comboBoxPersonID.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelPerson.add(comboBoxPersonID);

        labelLastName.setText("Last Name");
        panelPerson.add(labelLastName);

        textFieldLastName.setPreferredSize(new Dimension(180, 22));
        panelPerson.add(textFieldLastName);

        labelFirstName.setText("First Name");
        panelPerson.add(labelFirstName);

        textFieldFirstName.setPreferredSize(new Dimension(180, 22));
        panelPerson.add(textFieldFirstName);

        panelHeader.add(panelPerson);

        panelOnlineCourse.setBorder(BorderFactory.createTitledBorder("Online Course"));
        panelOnlineCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelCourseID.setText("Course ID");
        panelOnlineCourse.add(labelCourseID);

        comboBoxCourseID.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelOnlineCourse.add(comboBoxCourseID);

        labelURL.setText("URL");
        panelOnlineCourse.add(labelURL);

        textFieldURL.setPreferredSize(new Dimension(200, 22));
        panelOnlineCourse.add(textFieldURL);

        panelHeader.add(panelOnlineCourse);

        panelOnsiteCourse.setBorder(BorderFactory.createTitledBorder("Onsite Course"));
        panelOnsiteCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelOnsiteCourseID.setText("Course ID");
        panelOnsiteCourse.add(labelOnsiteCourseID);

        comboBoxOnsiteCourseID.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelOnsiteCourse.add(comboBoxOnsiteCourseID);

        labelLocation.setText("Location");
        panelOnsiteCourse.add(labelLocation);

        textFieldLocation.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(textFieldLocation);

        labelDay.setText("Days");
        panelOnsiteCourse.add(labelDay);

        textFieldDay.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(textFieldDay);

        labelTime.setText("Time");
        panelOnsiteCourse.add(labelTime);

        textFieldTime.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(textFieldTime);

        panelHeader.add(panelOnsiteCourse);

        buttonAssign.setText("Assign");
        panelButton.add(buttonAssign);

        buttonNoAssign.setText("No Assign");
        buttonNoAssign.setToolTipText("");
        panelButton.add(buttonNoAssign);

        panelHeader.add(panelButton);

        add(panelHeader, BorderLayout.PAGE_START);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPane.setViewportView(table);

        add(scrollPane, BorderLayout.CENTER);
    }


    private JButton buttonAssign;
    private JButton buttonNoAssign;
    private JComboBox<String> comboBoxCourseID;
    private JComboBox<String> comboBoxOnsiteCourseID;
    private JComboBox<String> comboBoxPersonID;
    private JPanel panelHeader;
    private JPanel panelPerson;
    private JPanel panelOnlineCourse;
    private JPanel panelOnsiteCourse;
    private JLabel labelCourseID;
    private JLabel labelDay;
    private JLabel labelFirstName;
    private JLabel labelLastName;
    private JLabel labelLocation;
    private JLabel labelOnsiteCourseID;
    private JLabel labelPersonID;
    private JLabel labelTime;
    private JLabel labelURL;
    private JPanel panelButton;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextField textFieldDay;
    private JTextField textFieldFirstName;
    private JTextField textFieldLastName;
    private JTextField textFieldLocation;
    private JTextField textFieldTime;
    private JTextField textFieldURL;
}
