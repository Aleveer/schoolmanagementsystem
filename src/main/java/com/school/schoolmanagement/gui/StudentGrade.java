/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.school.schoolmanagement.gui;

import javax.swing.*;
import javax.swing.table.*;

import org.apache.commons.collections4.iterators.LoopingListIterator;

import com.school.schoolmanagement.bus.CourseBUS;
import com.school.schoolmanagement.bus.PersonBUS;
import com.school.schoolmanagement.bus.StudentGradeBUS;
import com.school.schoolmanagement.models.CourseModel;
import com.school.schoolmanagement.models.PersonModel;
import com.school.schoolmanagement.models.StudentGradeModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentGrade extends JPanel {
    List<PersonModel> personList = PersonBUS.getInstance().getAllModels();
    List<PersonModel> studentList = personList.stream()
            .filter(person -> person.getHireDate() == null)
            .filter(person -> person.getEnrollmentDate() != null)
            .collect(Collectors.toList());
    List<StudentGradeModel> studentGradeModels = StudentGradeBUS.getInstance().getAllModels();

    public StudentGrade() {
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        panelHeader = new JPanel();
        panelInfor = new JPanel();
        panelPerson = new JPanel();
        labelPersonID = new JLabel();
        txtPersonID = new JTextField();
        labelLastName = new JLabel();
        txtLastName = new JTextField();
        labelFirstName = new JLabel();
        txtFirstName = new JTextField();
        buttonAdd = new JButton();
        panelNewGrade = new JPanel();
        labelTitle = new JLabel();
        txtTitle = new JTextField();
        labelGrade = new JLabel();
        txtGrade = new JTextField();
        buttonSave = new JButton();
        scrollPane1 = new JScrollPane();
        tableGrade = new JTable();
        panelStudent = new JPanel();
        scrollPane2 = new JScrollPane();
        tableStudent = new JTable();
        panelSearch = new JPanel();
        txtSearch = new JTextField();
        buttonSearch = new JButton();

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        panelHeader.setPreferredSize(new Dimension(635, 180));
        panelHeader.setLayout(new GridLayout());

        panelInfor.setLayout(new BorderLayout());

        panelPerson.setLayout(new GridBagLayout());

        labelPersonID.setText("Person ID");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelPerson.add(labelPersonID, gridBagConstraints);

        txtPersonID.setPreferredSize(new Dimension(120, 22));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        panelPerson.add(txtPersonID, gridBagConstraints);

        labelLastName.setText("Last Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelPerson.add(labelLastName, gridBagConstraints);

        txtLastName.setPreferredSize(new Dimension(120, 22));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panelPerson.add(txtLastName, gridBagConstraints);

        labelFirstName.setText("First Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        panelPerson.add(labelFirstName, gridBagConstraints);

        txtFirstName.setPreferredSize(new Dimension(120, 22));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        panelPerson.add(txtFirstName, gridBagConstraints);

        buttonAdd.setText("Add");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        panelPerson.add(buttonAdd, gridBagConstraints);

        panelInfor.add(panelPerson, BorderLayout.WEST);

        panelNewGrade.setBorder(BorderFactory.createTitledBorder("Grade"));
        panelNewGrade.setPreferredSize(new Dimension(200, 200));
        panelNewGrade.setLayout(new GridBagLayout());

        labelTitle.setText("Title");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelNewGrade.add(labelTitle, gridBagConstraints);

        txtTitle.setPreferredSize(new Dimension(120, 22));
        txtTitle.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        panelNewGrade.add(txtTitle, gridBagConstraints);

        labelGrade.setText("Grade");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panelNewGrade.add(labelGrade, gridBagConstraints);

        txtGrade.setPreferredSize(new Dimension(120, 22));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        panelNewGrade.add(txtGrade, gridBagConstraints);

        buttonSave.setText("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                saveGrade();
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        panelNewGrade.add(buttonSave, gridBagConstraints);

        panelInfor.add(panelNewGrade, BorderLayout.CENTER);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addPerson();
            }
        });

        panelHeader.add(panelInfor);
        // TODO: Can't scroll, fix
        String[] columnNames = { "EnrollmentID", "CourseID", "StudentID", "Grade" };
        Object[][] data = new Object[studentGradeModels.size()][4];
        System.out.println(" " + studentList.size());
        for (int i = 0; i < studentGradeModels.size(); i++) {
            StudentGradeModel studentGradeModel = studentGradeModels.get(i);
            data[i][0] = studentGradeModel.getEnrollmentID();
            data[i][1] = studentGradeModel.getCourseID();
            data[i][2] = studentGradeModel.getStudentID();
            data[i][3] = studentGradeModel.getGrade();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tableGrade.setModel(model);
        tableGrade.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleRowSelection();
            }
        });
        tableGrade.setMaximumSize(new Dimension(60, 80));
        tableGrade.setPreferredSize(new Dimension(100, 80));
        scrollPane1.setViewportView(tableGrade);

        panelHeader.add(scrollPane1);

        add(panelHeader, BorderLayout.NORTH);

        panelStudent.setLayout(new BorderLayout());
        String[] columnNames1 = { "PersonID", "LastName", "FirstName", "EnrollmentDate" };
        Object[][] data1 = new Object[studentList.size()][4];
        for (int i = 0; i < studentList.size(); i++) {
            PersonModel student = studentList.get(i);
            if (student.getHireDate() == null) {
                data1[i][0] = student.getPersonID();
                data1[i][1] = student.getLastName();
                data1[i][2] = student.getFirstName();
                data1[i][3] = student.getEnrollmentDate();
            } else {
                studentList.remove(i);
            }
        }
        DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1);
        tableStudent.setModel(model1);
        tableStudent.getTableHeader().setReorderingAllowed(false);
        scrollPane2.setViewportView(tableStudent);

        panelStudent.add(scrollPane2, BorderLayout.CENTER);

        txtSearch.setPreferredSize(new Dimension(160, 22));
        panelSearch.add(txtSearch);

        buttonSearch.setText("Search");
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SearchFunc();
            }
        });
        panelSearch.add(buttonSearch);

        panelStudent.add(panelSearch, BorderLayout.SOUTH);

        add(panelStudent, BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void handleRowSelection() {
        int selectedRow = tableGrade.getSelectedRow();
        if (selectedRow != -1) {
            Object enrollmentId = tableGrade.getValueAt(selectedRow, 0);
            Object courseId = tableGrade.getValueAt(selectedRow, 1);
            Object studentId = tableGrade.getValueAt(selectedRow, 2);
            Object grade = tableGrade.getValueAt(selectedRow, 3);
            int courseID = (int) tableGrade.getValueAt(selectedRow, 1);
            CourseModel courseModel = CourseBUS.getInstance().getModelById(courseID);
            txtTitle.setText(courseModel.getTitle().toString());
            txtGrade.setText(grade.toString());
        }
    }

    private void saveGrade() {
        // TODO: Refresh Table
        int selectedRow = tableGrade.getSelectedRow();
        if (selectedRow != -1) {
            Object enrollmentId = tableGrade.getValueAt(selectedRow, 0);
            int enrollmentID = (int) enrollmentId;
            BigDecimal grade = new BigDecimal(txtGrade.getText().toString());
            StudentGradeModel studentGradeModel = StudentGradeBUS.getInstance().getModelById(enrollmentID);
            studentGradeModel.setGrade(grade);
            StudentGradeBUS.getInstance().updateModel(studentGradeModel);
            JOptionPane.showMessageDialog(null, "Saved successfully");
            StudentGradeBUS.getInstance().refresh();
        }
    }

    private void addPerson() {
        int personId = Integer.parseInt(txtPersonID.getText().toString());
        List<PersonModel> people = PersonBUS.getInstance().searchModel(String.valueOf(personId),
                new String[] { "PersonID" });
        if (people.size() > 1) {
            JOptionPane.showMessageDialog(null, "ID has existed, please use another ID.");
            return;
        }
        String lastName = txtLastName.getText().toString();
        String firstName = txtFirstName.getText().toString();
        // TODO: Add enrollment date
        PersonModel newStudent = new PersonModel(personId, lastName, firstName, null, null);
        PersonBUS.getInstance().addModel(newStudent);
        JOptionPane.showMessageDialog(null, "Added successfully");
        PersonBUS.getInstance().refresh();
        // TODO: refresh table
    }

    private void SearchFunc() {
        String searchField = txtSearch.getText().toString();
        List<PersonModel> studentList1 = new ArrayList<>(studentList);
        List<PersonModel> searchResults = studentList1.stream()
                .filter(person -> person.getLastName().toLowerCase().contains(searchField.toLowerCase()) ||
                        person.getFirstName().toLowerCase().contains(searchField.toLowerCase()) ||
                        person.getPersonID() == Integer.parseInt(searchField)
                // || person.getEnrollmentDate().equals(Date.valueOf(searchField)))
                )
                .collect(Collectors.toList());

        Object[][] data2 = new Object[searchResults.size()][4];
        for (int i = 0; i < searchResults.size(); i++) {
            PersonModel student = searchResults.get(i);
            data2[i][0] = student.getPersonID();
            data2[i][1] = student.getLastName();
            data2[i][2] = student.getFirstName();
            data2[i][3] = student.getEnrollmentDate();
        }
        String[] columnNames2 = { "PersonID", "LastName", "FirstName", "EnrollmentDate" };
        DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2);
        tableStudent.setModel(model2);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton buttonAdd;
    private JButton buttonSave;
    private JButton buttonSearch;
    private JLabel labelFirstName;
    private JLabel labelGrade;
    private JLabel labelLastName;
    private JLabel labelPersonID;
    private JLabel labelTitle;
    private JPanel panelHeader;
    private JPanel panelInfor;
    private JPanel panelNewGrade;
    private JPanel panelPerson;
    private JPanel panelSearch;
    private JPanel panelStudent;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JTable tableGrade;
    private JTable tableStudent;
    private JTextField txtFirstName;
    private JTextField txtGrade;
    private JTextField txtLastName;
    private JTextField txtPersonID;
    private JTextField txtSearch;
    private JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}
