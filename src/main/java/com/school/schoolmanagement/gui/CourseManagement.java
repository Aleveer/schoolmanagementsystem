package com.school.schoolmanagement.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import com.formdev.flatlaf.json.ParseException;
import com.school.schoolmanagement.bus.CourseBUS;
import com.school.schoolmanagement.bus.DepartmentBUS;
import com.school.schoolmanagement.bus.OnlineCourseBUS;
import com.school.schoolmanagement.bus.OnsiteCourseBUS;
import com.school.schoolmanagement.models.CourseModel;
import com.school.schoolmanagement.models.DepartmentModel;
import com.school.schoolmanagement.models.OnlineCourseModel;
import com.school.schoolmanagement.models.OnsiteCourseModel;
import org.netbeans.lib.awtextra.*;

public class CourseManagement extends JPanel {
    public CourseManagement() {
        initComponents();
    }
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radioOnline);
        btnGroup.add(radioOnsite);

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

        departmentComboBox.addItem("");
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            departmentComboBox.addItem(department.getName()+"");
        }
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

        comboBoxDepartment.setBorder(BorderFactory.createTitledBorder("Department"));
        comboBoxDepartment.setPreferredSize(new Dimension(130, 43));
        
        panelSearch.add(comboBoxDepartment);

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

        labelCredits.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();  // Ignore non-digits
                }
            }
        });

        table.setModel(new DefaultTableModel(
                                 new Object[][] {
                                 },
                                 new String[] {
                                         "CourseID", "Title", "Credits", "DepartmentID", "Status"
                                 }) {
                             @Override
                             public boolean isCellEditable(int row, int column) {
                                 return column == getColumnCount();
                             }
                         }

        );

        radioOnline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioOnline.isSelected()) {
                    // Hiển thị các thành phần của A
//                    lbUrl.setVisible(true);
//                    txtUrl.setVisible(true);
//                    txtLocation.setText("");
//                    txtDays.setText("");
//                    txtTime.setText("");
//                    // Ẩn các thành phần của B
//                    panel_onSite.setVisible(false);
                }
            }
        });

        radioOnsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioOnsite.isSelected()) {
//                    // Hiển thị các thành phần của B
//                    panel_onSite.setVisible(true);
//                    txtUrl.setText("");
//                    // Ẩn các thành phần của A
//                    lbUrl.setVisible(false);
//                    txtUrl.setVisible(false);
                }
            }
        });

        comboBoxDepartment.addItem("");
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            comboBoxDepartment.addItem(department.getName()+"");
        }

        comboBoxStatus.addItem("");
        comboBoxStatus.addItem("Online");
        comboBoxStatus.addItem("Onsite");

        buttonRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                clearForm();
                btnGroup.clearSelection();
                showListCourse();
            }
        });

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please choose a course for deletion", "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(null, "Do you want to delete this course?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    int courseID = (int) table.getModel().getValueAt(selectedRow, 0);
                    deleteCourse(courseID);
                    showListCourse();
                }
//                clearForm();
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleRowSelection();
            }
        });

        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchValue = textFieldSearch.getText().trim();
                    String[] columnNames = {"CourseID" ,"Title", "Credits", "DepartmentID"};
                    List<CourseModel> searchResults = CourseBUS.getInstance().searchModel(searchValue, columnNames);
                    showSearchResult(searchResults);
                }
            }
        });


        buttonUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = table.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Please choose a course for update", "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    int courseID = Integer.parseInt(textFieldId.getText());
                    String title = textFieldTitle.getText();
                    int credit = Integer.parseInt(textFieldCredit.getText());
                    String departmentName = (String) comboBoxDepartment.getSelectedItem();
                    int departmentID = 0;
                    for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
                        if(departmentName.equals(department.getName())) {
                            departmentID = department.getDepartmentID();
                        }
                    }
                    Object status = table.getValueAt(index,4);
                    if(status.toString().equals("Online")) {
//                        String url = txtUrl.getText();
                        CourseModel updateModel = new CourseModel(courseID,title,credit,departmentID);
//                        OnlineCourseModel updateOnlineModel = new OnlineCourseModel(courseID,null,0,0,url);
//                        int resultModel = CourseBUS.getInstance().updateModel(updateModel);
//                        int resultOnlineModel = OnlineCourseBUS.getInstance().updateModel(updateOnlineModel);
//                        if(resultModel > 0 && resultOnlineModel > 0) {
//                            JOptionPane.showMessageDialog(null, "Update successfully", "Success",
//                                    JOptionPane.INFORMATION_MESSAGE);
//                        }
                        showListCourse();
//                        txtUrl.setVisible(true);
                        table.clearSelection();
                    }else if(status.toString().equals("Onsite")) {
//                        String timeText = txtTime.getText();
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                        java.util.Date parsedDate;  // Change to java.util.Date
                        try {
//                            parsedDate = timeFormat.parse(timeText);
                        } catch (ParseException e1) {
                            throw new RuntimeException(e1);
                        }
//                        java.sql.Time time = new java.sql.Time(parsedDate.getTime());

//                        CourseModel updateModel = new CourseModel(courseID,title,credit,departmentID);
//                        OnsiteCourseModel updateOnsiteCourse = new OnsiteCourseModel(courseID,null,0,0,txtLocation.getText(),txtDays.getText(),time);
//
//                        int resultModel = CourseBUS.getInstance().updateModel(updateModel);
//                        int resultOnsiteModel = OnsiteCourseBUS.getInstance().updateModel(updateOnsiteCourse);
//
//                        if(resultModel > 0 && resultOnsiteModel > 0) {
//                            JOptionPane.showMessageDialog(null, "Update successfully", "Success",
//                                    JOptionPane.INFORMATION_MESSAGE);
//                        }

                        showListCourse();
//                        panel_onSite.setVisible(true);
                        table.clearSelection();
                    }
                }
//                clearForm();
            }
        });

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = textFieldSearch.getText();
                String departmentName = comboBoxDepartment.getSelectedItem()+"";
                String status = comboBoxStatus.getSelectedItem()+"";

                List<CourseModel> models = CourseBUS.getInstance().searchConditions(value,departmentName,status);
                showSearchResult(models);
            }
        });

        showListCourse();
    }

    public void showListCourse() {
        CourseBUS.getInstance().refresh();
        OnlineCourseBUS.getInstance().refresh();
        DefaultTableModel model_table = (DefaultTableModel) table.getModel();
        model_table.setRowCount(0);


        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (CourseModel course : CourseBUS.getInstance().getAllModels()) {
            boolean check = false;

            for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
                if(course.getDepartmentID() == department.getDepartmentID()) { // get name
                    for(OnlineCourseModel online : OnlineCourseBUS.getInstance().getAllModels()) {
                        if(course.getId() == online.getId()) {
                            model_table.addRow(new Object[]{course.getId(), course.getTitle(), course.getCredit(), department.getName(), "Online"});
                            check = true;
                        }
                    }

                    if(!check) {
                        model_table.addRow(new Object[]{course.getId(), course.getTitle(), course.getCredit(), department.getName(), "Onsite"});
                    }
                }
            }
        }
    }

//    public void clearForm() {
//        textFieldId.setText("");
//        textFieldTitle.setText("");
//        textFieldCredit.setText("");
//        comboBoxDepartment.setSelectedIndex(-1);
//        txtUrl.setText("");
//        txtLocation.setText("");
//        txtDays.setText("");
//        txtTime.setText("");
//        textFieldSearch.setText("");
//        comboBoxDepartment.setSelectedItem("");
//        comboBoxStatus.setSelectedItem("");
//        txtLocation.setText("");
//        txtDays.setText("");
//        txtTime.setText("");
//        lbUrl.setVisible(false);
//        txtUrl.setVisible(false);
//        radioOnsite.setSelected(false);
//        lbLocation.setVisible(false);
//        lbTime.setVisible(false);
//        lbDays.setVisible(false);
//        panel_onSite.setVisible(false);
//    }

    public void addCourse() {
        if (!radioOnline.isSelected() && !radioOnsite.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please choose course type (Online, onsite)");
            return;
        }

        int CourseID = randomCourseID();
        String title = textFieldTitle.getText() + "";
        int credits = Integer.parseInt(textFieldCredit.getText() + "");
        String departmentName = comboBoxDepartment.getSelectedItem() + "";
        int departmentID = 0;
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            if(departmentName.equals(department.getName())) {
                departmentID = department.getDepartmentID();
            }
        }

        if (radioOnline.isSelected()) {
//            String url = txtUrl.getText() + "";
//            CourseModel newCourse = new CourseModel(CourseID, title, credits, departmentID);
//            OnlineCourseModel onlineCourse = new OnlineCourseModel(CourseID, title, credits, departmentID, url);
//            int resultModel = CourseBUS.getInstance().addModel(newCourse);
//            int newOnlineCourse = OnlineCourseBUS.getInstance().addModel(onlineCourse);
//            if (resultModel == 1 && newOnlineCourse == 1) {
//                JOptionPane.showMessageDialog(null, "Add successfully");
//            } else {
//                JOptionPane.showMessageDialog(null, "Add failed");
//            }
        } else if (radioOnsite.isSelected()) {
//            String timeText = txtTime.getText();
//
//            String timeRegex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
//
//            if (!timeText.matches(timeRegex)) {
//                JOptionPane.showMessageDialog(null, "Invalid time format");
//                return;
//            }
//
//            String[] timeParts = timeText.split(":");
//            int hours = Integer.parseInt(timeParts[0]);
//            int minutes = Integer.parseInt(timeParts[1]);
//            int seconds = Integer.parseInt(timeParts[2]);
//
//            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
//                JOptionPane.showMessageDialog(null, "Invalid time values. Please use valid hour, minute, and second values.");
//                return;
//            }
//
//            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
//            java.util.Date parsedDate;  // Change to java.util.Date
//
//            try {
//                parsedDate = timeFormat.parse(timeText);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Convert java.util.Date to java.sql.Time
//            java.sql.Time time = new java.sql.Time(parsedDate.getTime());
//
//            CourseModel newCourse = new CourseModel(CourseID, title, credits, departmentID);
//            OnsiteCourseModel onsiteCourse = new OnsiteCourseModel(CourseID, textFieldTitle.getText(), Integer.parseInt(textFieldCredit.getText()), departmentID, txtLocation.getText(), txtDays.getText(), time);
//            int resultModel = CourseBUS.getInstance().addModel(newCourse);
//            int newOnsiteCourse = OnsiteCourseBUS.getInstance().addModel(onsiteCourse);
//            if (resultModel == 1 && newOnsiteCourse == 1) {
//                JOptionPane.showMessageDialog(null, "Add successfully");
//            } else {
//                JOptionPane.showMessageDialog(null, "Add failed");
//            }
        }
        table.clearSelection();
//        clearForm();
        showListCourse();
    }

    public void deleteCourse(int courseID) {
        try {
            int deletedRow = CourseBUS.getInstance().deleteModel(courseID);
            if (deletedRow > 0) {
                JOptionPane.showMessageDialog(null, "Delete successfully");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete failed");
        }
    }

    public void handleRowSelection() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1) {
            Object courseID = table.getValueAt(selectedRow,0);
            Object title = table.getValueAt(selectedRow,1);
            Object credit = table.getValueAt(selectedRow,2);
            Object departmentName = table.getValueAt(selectedRow,3);
            Object status = table.getValueAt(selectedRow,4);

            textFieldId.setText(courseID.toString());
            textFieldTitle.setText(title.toString());
            textFieldCredit.setText(credit.toString());
            comboBoxDepartment.setSelectedItem(departmentName.toString());

//            if(status.toString().equals("Online")) {
//                for(OnlineCourseModel online : OnlineCourseBUS.getInstance().getAllModels()) {
//                    if(Integer.parseInt(courseID.toString()) == online.getId()) {
////                        txtUrl.setText(online.getUrl());
//                        break;
//                    }
//                }
//                radioOnline.setSelected(true);
//                lbUrl.setVisible(true);
//                txtUrl.setVisible(true);
//                radioOnsite.setSelected(false);
//                panel_onSite.setVisible(false);
//            }else if(status.toString().equals("Onsite")) {
//                radioOnsite.setSelected(true);
//                for(OnsiteCourseModel onsite : OnsiteCourseBUS.getInstance().getAllModels()) {
//                    if(Integer.parseInt(courseID.toString()) == onsite.getId()) {
//                        txtLocation.setText(onsite.getLocation());
//                        txtDays.setText(onsite.getDays());
//                        txtTime.setText(onsite.getTime()+"");
//                    }
//                }
//                lbLocation.setVisible(true);
//                lbDays.setVisible(true);
//                lbTime.setVisible(true);
//                panel_onSite.setVisible(true);
//                radioOnline.setSelected(false);
//                txtUrl.setVisible(false);
//                lbUrl.setVisible(false);
//            }
        }
    }

    public int randomCourseID() {
        Random rand = new Random();
        return rand.nextInt(9999) + 1;
    }
    public void showSearchResult(List<CourseModel> search) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (CourseModel course : search) {
            DepartmentModel department = DepartmentBUS.getInstance().getModelById(course.getDepartmentID());
            OnlineCourseModel online = OnlineCourseBUS.getInstance().getModelById(course.getId());

            boolean isOnline = (online != null && !online.getUrl().isEmpty());

            model.addRow(new Object[]{
                    course.getId(),
                    course.getTitle(),
                    course.getCredit(),
                    (department != null) ? department.getName() : null,
                    isOnline ? "Online" : "Onsite"
            });
        }
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
