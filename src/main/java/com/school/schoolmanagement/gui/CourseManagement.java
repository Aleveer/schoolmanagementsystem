package com.school.schoolmanagement.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import org.netbeans.lib.awtextra.*;

import com.school.schoolmanagement.bus.CourseBUS;
import com.school.schoolmanagement.bus.DepartmentBUS;
import com.school.schoolmanagement.models.CourseModel;
import com.school.schoolmanagement.models.DepartmentModel;

public class CourseManagement extends JPanel {
    public CourseManagement() {
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        panelHeader = new JPanel();
        panelInfor = new JPanel();
        labelId = new JLabel();
        textFieldId = new JTextField();
        labelTitle = new JLabel();
        textFieldTitle = new JTextField();
        labelCredits = new JLabel();
        textFieldCredit = new JTextField();
        labelDepartment = new JLabel();
        departmentComboBox = new JComboBox<>();
        panelCourse = new JPanel();
        radioOnline = new JRadioButton();
        radioOnsite = new JRadioButton();
        panelButton = new JPanel();
        buttonAdd = new JButton();
        buttonDelete = new JButton();
        buttonUpdate = new JButton();
        buttonRefresh = new JButton();
        panel = new JPanel();
        panelSearch = new JPanel();
        textFieldSearch = new JTextField();
        comboBoxDepartment = new JComboBox<>();
        comboBoxStatus = new JComboBox<>();
        buttonSearch = new JButton();
        scrollPane = new JScrollPane();
        table = new JTable();

        setLayout(new BorderLayout());

        panelHeader.setLayout(new BorderLayout());

        GridBagLayout panelInforLayout = new GridBagLayout();
        panelInforLayout.columnWidths = new int[] { 0, 10, 0 };
        panelInforLayout.rowHeights = new int[] { 0, 10, 0, 10, 0, 10, 0 };
        panelInfor.setLayout(panelInforLayout);

        labelId.setHorizontalAlignment(SwingConstants.RIGHT);
        labelId.setText("ID Course");
        labelId.setPreferredSize(new Dimension(100, 16));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        panelInfor.add(labelId, gridBagConstraints);

        textFieldId.setPreferredSize(new Dimension(220, 22));

        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.3;

        panelInfor.add(textFieldId, gridBagConstraints);

        labelTitle.setHorizontalAlignment(SwingConstants.RIGHT);
        labelTitle.setText("Title");
        labelTitle.setPreferredSize(new Dimension(100, 16));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;

        panelInfor.add(labelTitle, gridBagConstraints);

        textFieldTitle.setPreferredSize(new Dimension(220, 22));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;

        panelInfor.add(textFieldTitle, gridBagConstraints);

        labelCredits.setHorizontalAlignment(SwingConstants.RIGHT);
        labelCredits.setText("Credits");
        labelCredits.setPreferredSize(new Dimension(100, 16));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;

        panelInfor.add(labelCredits, gridBagConstraints);

        textFieldCredit.setPreferredSize(new Dimension(220, 22));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;

        panelInfor.add(textFieldCredit, gridBagConstraints);

        labelDepartment.setHorizontalAlignment(SwingConstants.RIGHT);
        labelDepartment.setText("Department");
        labelDepartment.setPreferredSize(new Dimension(100, 16));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;

        panelInfor.add(labelDepartment, gridBagConstraints);
        List<CourseModel> courseList = CourseBUS.getInstance().getAllModels();
        List<DepartmentModel> departmentList = DepartmentBUS.getInstance().getAllModels();
        String[] arr = new String[departmentList.size()];
        for (int i = 0; i < departmentList.size(); i++) {
            DepartmentModel departmentModel = departmentList.get(i);
            arr[i] = departmentModel.getName() + " id: " + departmentModel.getDepartmentID();
        }
        departmentComboBox
                .setModel(new DefaultComboBoxModel<>(arr));
        departmentComboBox.setPreferredSize(new Dimension(220, 22));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        panelInfor.add(departmentComboBox, gridBagConstraints);

        panelHeader.add(panelInfor, BorderLayout.WEST);

        panelCourse.setLayout(new AbsoluteLayout());

        radioOnline.setText("Online Course");

        panelCourse.add(radioOnline, new AbsoluteConstraints(20, 20, -1, -1));

        radioOnsite.setText("Onsite Course");

        panelCourse.add(radioOnsite, new AbsoluteConstraints(140, 20, -1, -1));

        panelHeader.add(panelCourse, BorderLayout.CENTER);

        panelButton.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));

        buttonAdd.setText("Add");
        panelButton.add(buttonAdd);
        // TODO: Unfinished, bugs are expected, not auto-refreshing table when
        // successfully adding new course.
        // TODO: Add JPanel when choosing online / onsite course (Show options for
        // inputting informations for one of those 2 tables).
        // TODO: Course must have course Instructor, when it's added, prompt user to add
        // course instructor by swapping to course instructor panel/frame.
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int courseID = Integer.parseInt(textFieldId.getText());
                    for (int i = 0; i < courseList.size(); i++) {
                        if (courseID == courseList.get(i).getId()) {
                            JOptionPane.showMessageDialog(null, "ID has existed, please use another id!");
                            return;
                        }
                    }
                    String title = textFieldTitle.getText();
                    int credits = Integer.parseInt(textFieldCredit.getText());
                    String department = departmentComboBox.getSelectedItem().toString();
                    int lastIndex = department.lastIndexOf(" ");
                    int lastNumber = Integer.parseInt(department.substring(lastIndex + 1));
                    CourseBUS.getInstance().addModel(new CourseModel(courseID, title, credits, lastNumber));
                    CourseBUS.getInstance().refresh();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonDelete.setText("Delete");
        panelButton.add(buttonDelete);
        buttonDelete.addActionListener(new ActionListener() {
            // TODO: Unfinished, bugs are expected, not auto-refreshing table when
            // successfully deleting a course.
            // TODO: Show MessageDialog when successfully deleted.
            @Override
            public void actionPerformed(ActionEvent arg0) {
                int courseID = Integer.parseInt(textFieldId.getText());
                CourseBUS.getInstance().deleteModel(courseID);
                CourseBUS.getInstance().refresh();
            }
        });
        buttonUpdate.setText("Update");
        panelButton.add(buttonUpdate);
        buttonUpdate.addActionListener(new ActionListener() {
            // TODO: Unfinished, bugs are expected, not auto-refreshing table when
            // successfully updating new course.
            // TODO: Add JPanel when choosing online / onsite course (Show options for
            // inputting informations for one of those 2 tables).
            // TODO: Course must have course Instructor, when it's updated, prompt user to
            // change course instructor by swapping to course instructor panel/frame, if
            // accepeted swap to course else return.
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int courseID = Integer.parseInt(textFieldId.getText());
                    for (int i = 0; i < courseList.size(); i++) {
                        if (i == courseList.size()) {
                            JOptionPane.showMessageDialog(null,
                                    "CourseID is not available / existing, please try again!");
                            return;
                        }
                    }
                    String title = textFieldTitle.getText();
                    int credits = Integer.parseInt(textFieldCredit.getText());
                    String department = departmentComboBox.getSelectedItem().toString();
                    int lastIndex = department.lastIndexOf(" ");
                    int lastNumber = Integer.parseInt(department.substring(lastIndex + 1));
                    CourseBUS.getInstance().updateModel(new CourseModel(courseID, title, credits, lastNumber));
                    CourseBUS.getInstance().refresh();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonRefresh.setText("Refresh");
        panelButton.add(buttonRefresh);
        // TODO: Finish the button
        buttonRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                CourseBUS.getInstance().refresh();
            }
        });
        panelHeader.add(panelButton, BorderLayout.SOUTH);

        add(panelHeader, BorderLayout.NORTH);

        panel.setLayout(new BorderLayout());

        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));

        textFieldSearch.setToolTipText("");
        textFieldSearch.setPreferredSize(new Dimension(170, 22));

        panelSearch.add(textFieldSearch);
        comboBoxDepartment
                .setModel(new DefaultComboBoxModel<>(arr));
        comboBoxDepartment.setBorder(BorderFactory.createTitledBorder("Department"));
        comboBoxDepartment.setPreferredSize(new Dimension(130, 43));

        panelSearch.add(comboBoxDepartment);

        comboBoxStatus.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        comboBoxStatus.setPreferredSize(new Dimension(120, 43));
        panelSearch.add(comboBoxStatus);

        buttonSearch.setText("Search");
        panelSearch.add(buttonSearch);
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String inputText = textFieldSearch.getText(); // Get the search text
                String departmentSelected = comboBoxDepartment.getSelectedItem().toString();
                int lastIndex = departmentSelected.lastIndexOf(" ");
                int lastNumber = Integer.parseInt(departmentSelected.substring(lastIndex + 1));

                // Search for courses based on the department ID
                List<CourseModel> courseSearchList = CourseBUS.getInstance().searchModel(String.valueOf(lastNumber),
                        new String[] { "DepartmentID" });

                // Filter the search results based on the input text
                List<CourseModel> filteredList = new ArrayList<>();
                for (CourseModel course : courseSearchList) {
                    if (inputText.isEmpty() || // If the search text is empty, add all courses
                            course.getId() == Integer.parseInt(inputText) || // Compare IDs
                            course.getTitle().contains(inputText) || // Check if title contains the input text
                            String.valueOf(course.getCredit()).contains(inputText)) { // Check if credit contains the
                                                                                      // input text
                        filteredList.add(course);
                    }
                }

                // Prepare data for the table model
                String[] columnNames = { "CourseID", "Title", "Credits", "DepartmentID" };
                Object[][] data = new Object[filteredList.size()][4];
                for (int i = 0; i < filteredList.size(); i++) {
                    CourseModel course = filteredList.get(i);
                    data[i][0] = course.getId();
                    data[i][1] = course.getTitle();
                    data[i][2] = course.getCredit();
                    data[i][3] = course.getDepartmentID();
                }

                // Set the table model
                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table.setModel(model);
            }

        });

        panel.add(panelSearch, BorderLayout.PAGE_START);
        String[] columnNames = { "CourseID", "Title", "Credits", "DepartmentID" };
        Object[][] data = new Object[courseList.size()][4];
        for (int i = 0; i < courseList.size(); i++) {
            CourseModel course = courseList.get(i);
            data[i][0] = course.getId();
            data[i][1] = course.getTitle();
            data[i][2] = course.getCredit();
            data[i][3] = course.getDepartmentID();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table.setModel(model);

        scrollPane.setViewportView(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private JButton buttonAdd;
    private JButton buttonDelete;
    private JButton buttonRefresh;
    private JButton buttonSearch;
    private JButton buttonUpdate;
    private JComboBox<String> comboBoxDepartment;
    private JComboBox<String> comboBoxStatus;
    private JComboBox<String> departmentComboBox;
    private JLabel labelCredits;
    private JLabel labelDepartment;
    private JLabel labelId;
    private JLabel labelTitle;
    private JPanel panel;
    private JPanel panelButton;
    private JPanel panelCourse;
    private JPanel panelHeader;
    private JPanel panelInfor;
    private JPanel panelSearch;
    private JRadioButton radioOnline;
    private JRadioButton radioOnsite;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextField textFieldCredit;
    private JTextField textFieldId;
    private JTextField textFieldSearch;
    private JTextField textFieldTitle;
}
