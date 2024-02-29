package com.school.schoolmanagement.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import com.school.schoolmanagement.bus.CourseBUS;
import com.school.schoolmanagement.bus.PersonBUS;
import com.school.schoolmanagement.bus.StudentGradeBUS;
import com.school.schoolmanagement.models.CourseModel;
import com.school.schoolmanagement.models.PersonModel;
import com.school.schoolmanagement.models.StudentGradeModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        setBorder(new EmptyBorder(10, 10, 10, 10));
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
        cbTitle = new JComboBox();
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

        txtPersonID.setEnabled(false);

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
        panelNewGrade.setVisible(false);

        labelTitle.setText("Title");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panelNewGrade.add(labelTitle, gridBagConstraints);

        cbTitle.setPreferredSize(new Dimension(120, 22));
//        cbTitle.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        panelNewGrade.add(cbTitle, gridBagConstraints);
        cbTitle.addItem("");
        for(CourseModel course : CourseBUS.getInstance().getAllModels()) {
            cbTitle.addItem(course.getTitle());
        }

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
        buttonSave.addActionListener(arg0 -> {
            addGrade();
            clearForm();
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        panelNewGrade.add(buttonSave, gridBagConstraints);

        panelInfor.add(panelNewGrade, BorderLayout.CENTER);
        buttonAdd.addActionListener(arg0 -> addPerson());

//        Event listener for selecting table student and display on text box
        tableStudent.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tableStudent.getSelectedRow();
    //                Check if row is selected
                if (selectedRow != -1) {
                    handleRowPersonSelection();
                }
            }
        });

//        Event listener for change grade
        tableGrade.getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            public void editingStopped(ChangeEvent e) {
                int selectedRow = tableGrade.getSelectedRow();
                Object enrollmentID = tableGrade.getValueAt(selectedRow,0);
                Object courseID = tableGrade.getValueAt(selectedRow,1);
                Object courseName = tableGrade.getValueAt(selectedRow,2);
                Object studentID = tableGrade.getValueAt(selectedRow,3);
                Object studentName = tableGrade.getValueAt(selectedRow,4);
                Object grade = tableGrade.getValueAt(selectedRow,5);
                BigDecimal grade1 = null;
                try {
                    grade1 = new BigDecimal(grade.toString());
                } catch (NumberFormatException exception) {
                    System.err.println("Lỗi chuyển đổi số: " + exception.getMessage());
                }

                StudentGradeModel studentGrade = new StudentGradeModel(Integer.parseInt(enrollmentID.toString()),Integer.parseInt(courseID.toString()),Integer.parseInt(studentID.toString()),grade1);
                System.out.println("test: "+studentGrade);
                int result = StudentGradeBUS.getInstance().updateModel(studentGrade);
                if(result > 0) {
                    JOptionPane.showMessageDialog(null, "Save successful");
                }else {
                    JOptionPane.showMessageDialog(null, "Save failed");
                }
                showListGrade();
            }

            public void editingCanceled(ChangeEvent e) {
                System.out.println("Editing canceled");
            }
        });

        panelHeader.add(panelInfor);
        // TODO: Can't scroll, fix
        String[] columnNames = { "EnrollmentID", "CourseID", "Course Name","StudentID","Student Name", "Grade" };
        Object[][] data = new Object[studentGradeModels.size()][6];
        for (int i = 0; i < studentGradeModels.size(); i++) {
            StudentGradeModel studentGradeModel = studentGradeModels.get(i);
            data[i][0] = studentGradeModel.getEnrollmentID();
            data[i][1] = studentGradeModel.getCourseID();
            int courseID = studentGradeModel.getCourseID();
            int studentID = studentGradeModel.getStudentID();

            CourseModel course = CourseBUS.getInstance().getModelById(courseID);
            if (course != null) {
                data[i][2] = course.getTitle();
            } else {
                System.out.println("Invalid CourseID: " + courseID);
                data[i][2] = "";
            }

            data[i][3] = studentGradeModel.getStudentID();

            PersonModel person = PersonBUS.getInstance().getModelById(studentID);
            if (person != null) {
                data[i][4] = person.getFirstName()+" "+person.getLastName();
            } else {
                System.out.println("Invalid studentID: " + courseID);
                data[i][4] = "";
            }
            data[i][5] = studentGradeModel.getGrade();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép sửa cột điểm
                return column == 5;
            }
        };
        tableGrade.setModel(model);


        tableGrade.setMaximumSize(new Dimension(60, 652));
        tableGrade.setPreferredSize(new Dimension(100, 652));
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
        DefaultTableModel model1 = new DefaultTableModel(data1, columnNames1){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells not editable
            }
        };
        tableStudent.setModel(model1);
        tableStudent.getTableHeader().setReorderingAllowed(false);
        scrollPane2.setViewportView(tableStudent);

        panelStudent.add(scrollPane2, BorderLayout.CENTER);

        txtSearch.setPreferredSize(new Dimension(160, 22));
        panelSearch.add(txtSearch);

        buttonSearch.setText("Search");
        buttonSearch.addActionListener(arg0 -> searchFunc());
        panelSearch.add(buttonSearch);

        panelStudent.add(panelSearch, BorderLayout.SOUTH);

        add(panelStudent, BorderLayout.PAGE_END);

    }// </editor-fold>//GEN-END:initComponents

    public void clearForm() {
        txtPersonID.setText("");
        txtLastName.setText("");
        txtFirstName.setText("");
        cbTitle.setSelectedIndex(-1);
        txtGrade.setText("");
        showListGrade();
    }

    private void handleRowPersonSelection() {
        int selectedRow = tableStudent.getSelectedRow();
        if (selectedRow != -1) {
            Object personID = tableStudent.getValueAt(selectedRow, 0);
            Object lastName = tableStudent.getValueAt(selectedRow, 1);
            Object firstName = tableStudent.getValueAt(selectedRow, 2);

            txtPersonID.setText(personID.toString());
            txtLastName.setText(lastName.toString());
            txtFirstName.setText(firstName.toString());

            cbTitle.setSelectedIndex(-1);
            txtGrade.setText("");

            List<StudentGradeModel> filteredGrades = studentGradeModels.stream()
                    .filter(grade -> grade.getStudentID() == Integer.parseInt(personID.toString()))
                    .collect(Collectors.toList());

            // show list
            DefaultTableModel gradeModel = (DefaultTableModel) tableGrade.getModel();
            gradeModel.setRowCount(0); // Clear existing rows


            for (StudentGradeModel gradeModel1 : filteredGrades) {
                Object[] rowData = {gradeModel1.getEnrollmentID(), gradeModel1.getCourseID(),CourseBUS.getInstance().getModelById(gradeModel1.getCourseID()).getTitle(),
                        gradeModel1.getStudentID(), gradeModel1.getGrade()};
                gradeModel.addRow(rowData);
            }
        }
    }

    public void addGrade() {
        String title = cbTitle.getSelectedItem()+"";
        int courseID = 0;
        for(CourseModel course : CourseBUS.getInstance().getAllModels()) {
            if(title.equals(course.getTitle())) {
                courseID = course.getId();
            }
        }
        int studentID = Integer.parseInt(txtPersonID.getText()+"");
        BigDecimal grade = new BigDecimal(txtGrade.getText());

        StudentGradeModel newStudentGrade = new StudentGradeModel(StudentGradeBUS.getInstance().getMax(), courseID,studentID,grade);
        int result = StudentGradeBUS.getInstance().addModel(newStudentGrade);

        if(result > 0){
            JOptionPane.showMessageDialog(null,"Add grade successfully");
        }else {
            JOptionPane.showMessageDialog(null,"Add grade failed");
        }
        showListGrade();
    }

    private void addPerson() {
//        handleRowPersonSelection();
        panelNewGrade.setVisible(true);
        if (buttonAdd.getText().equals("Add")) {
            buttonAdd.setText("Close");
        } else {
            buttonAdd.setText("Add");
            panelNewGrade.setVisible(false);
        }
    }

    public void showListGrade() {
        StudentGradeBUS.getInstance().refresh();
        DefaultTableModel model_table = (DefaultTableModel) tableGrade.getModel();
        model_table.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (StudentGradeModel grade : StudentGradeBUS.getInstance().getAllModels()) {
            model_table.addRow(new Object[]{grade.getEnrollmentID(),grade.getCourseID(),CourseBUS.getInstance().getModelById(grade.getCourseID()).getTitle(),grade.getStudentID(),PersonBUS.getInstance().getModelById(grade.getStudentID()).getFirstName()+" "+PersonBUS.getInstance().getModelById(grade.getStudentID()).getLastName(),grade.getGrade()});
        }
    }

    public void showListPerson() {
        DefaultTableModel model_table = (DefaultTableModel) tableStudent.getModel();
        model_table.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
            if(person.getHireDate() == null) {
                model_table.addRow(new Object[]{person.getPersonID(),person.getLastName(),person.getFirstName(),person.getEnrollmentDate()});
            }
        }
    }



    private void searchFunc() {
        String searchField = txtSearch.getText().trim();
        if (searchField.isEmpty()) {
            showListPerson();
        } else {
            String[] columnNames = {"LastName", "FirstName", "PersonID", "EnrollmentDate"};
            List<PersonModel> studentList1 = PersonBUS.getInstance().searchModel(searchField, columnNames);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            List<PersonModel> searchResults = studentList1.stream()
                    .filter(person -> person.getLastName().toLowerCase().contains(searchField.toLowerCase()) ||
                            person.getFirstName().toLowerCase().contains(searchField.toLowerCase()) ||
                            (person.getEnrollmentDate() != null && dateFormat.format(person.getEnrollmentDate()).contains(searchField)) ||
                            person.getPersonID() == Integer.parseInt(searchField))
                    .toList();

            Object[][] data2 = new Object[searchResults.size()][4];
            for (int i = 0; i < searchResults.size(); i++) {
                if (searchResults.get(i).getHireDate() == null) {
                    PersonModel student = searchResults.get(i);
                    data2[i][0] = student.getPersonID();
                    data2[i][1] = student.getLastName();
                    data2[i][2] = student.getFirstName();
                    data2[i][3] = student.getEnrollmentDate();
                }
            }
            String[] columnNames2 = {"PersonID", "LastName", "FirstName", "EnrollmentDate"};
            DefaultTableModel model2 = new DefaultTableModel(data2, columnNames2);
            tableStudent.setModel(model2);
        }
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
    private JComboBox cbTitle;
    // End of variables declaration//GEN-END:variables
}