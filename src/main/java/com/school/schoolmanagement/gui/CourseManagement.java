package com.school.schoolmanagement.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import org.netbeans.lib.awtextra.*;

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
        panelInforLayout.columnWidths = new int[] {0, 10, 0};
        panelInforLayout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
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

        departmentComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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

        buttonDelete.setText("Delete");
        panelButton.add(buttonDelete);

        buttonUpdate.setText("Update");
        panelButton.add(buttonUpdate);

        buttonRefresh.setText("Refresh");
        panelButton.add(buttonRefresh);

        panelHeader.add(panelButton, BorderLayout.SOUTH);

        add(panelHeader, BorderLayout.NORTH);

        panel.setLayout(new BorderLayout());

        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));

        textFieldSearch.setToolTipText("");
        textFieldSearch.setPreferredSize(new Dimension(170, 22));
        
        panelSearch.add(textFieldSearch);

        comboBoxDepartment.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxDepartment.setBorder(BorderFactory.createTitledBorder("Department"));
        comboBoxDepartment.setPreferredSize(new Dimension(130, 43));
        
        panelSearch.add(comboBoxDepartment);

        comboBoxStatus.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBoxStatus.setBorder(BorderFactory.createTitledBorder("Status"));
        comboBoxStatus.setPreferredSize(new Dimension(120, 43));
        panelSearch.add(comboBoxStatus);

        buttonSearch.setText("Search");
        panelSearch.add(buttonSearch);

        panel.add(panelSearch, BorderLayout.PAGE_START);

        table.setModel(new DefaultTableModel(
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
