package com.school.schoolmanagement.gui;

import com.school.schoolmanagement.bus.*;
import com.school.schoolmanagement.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CourseInstructorManagement extends JPanel {

    public CourseInstructorManagement() {
        initComponents();

        //        Set Column for jTable
        tbList.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {"CourseID",
                                        "Title",
                                        "PersonID",
                                        "LastName",
                                        "FirstName"
                                }) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return column == getColumnCount();
                            }
                        }
        );

        txtFirstName.setEditable(false);
        txtLastName.setEditable(false);
        txtUrl.setEditable(false);
        txtDays.setEditable(false);
        txtLocation.setEditable(false);
        txtTime.setEditable(false);

        showListCourseInstructor();
        loadDataComboBox();

        cbPersonId.setSelectedIndex(0);
        cbCourseOnsiteId.setSelectedIndex(0);
        cbCouseOnlineId.setSelectedIndex(0);

        //        combo box person ID action listener
        cbPersonId.addActionListener(e -> {
            try {
                int selectedItem = Integer.parseInt(cbPersonId.getSelectedItem().toString());

                for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
                    if (person.getPersonID() == selectedItem) {
                        txtFirstName.setText(person.getFirstName());
                        txtLastName.setText(person.getLastName());
                    }
                }
            } catch (NumberFormatException ex) {
                txtFirstName.setText("");
                txtLastName.setText("");
                ex.printStackTrace();
            }
        });

        //        combo box CourseOnlineId action listener
        cbCouseOnlineId.addActionListener(e -> {
            try {
                int selectedItem = Integer.parseInt(cbCouseOnlineId.getSelectedItem().toString());

                for (OnlineCourseModel course : OnlineCourseBUS.getInstance().getAllModels()) {
                    if (course.getId() == selectedItem) {
                        txtUrl.setText(course.getUrl());
                    }
                }
            } catch (NumberFormatException ex) {
                txtUrl.setText("");
                ex.printStackTrace();
            }
        });

        //        combo box CourseOnsiteId action listener
        cbCourseOnsiteId.addActionListener(e -> {
            try {
                int selectedItem = Integer.parseInt(cbCourseOnsiteId.getSelectedItem().toString());

                for (OnsiteCourseModel course : OnsiteCourseBUS.getInstance().getAllModels()) {
                    if (course.getId() == selectedItem) {
                        txtLocation.setText(course.getLocation());
                        txtDays.setText(course.getDays());
                        txtTime.setText(course.getTime().toString());
                    }
                }
            } catch (NumberFormatException ex) {
                txtLocation.setText("");
                txtDays.setText("");
                txtTime.setText("");
                ex.printStackTrace();
            }
        });

//        List Selection Listener for jtbList
        tbList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbList.getSelectedRow();
//                Check if row is selected
                if (selectedRow != -1) {
//                    Get courseId and personId from jtbList
                    int courseId = (int)tbList.getValueAt(selectedRow, 0);
                    int personId = (int)tbList.getValueAt(selectedRow, 2);

//                    Display person detail into text field
                    for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
                        if (person.getPersonID() == personId) {
                            txtFirstName.setText(person.getFirstName());
                            txtLastName.setText(person.getLastName());
//                            Set combo box selected index correspond to selected record
                            for (int i = 1; i < cbPersonId.getItemCount(); i++) {
                                if (Integer.parseInt(cbPersonId.getItemAt(i).toString()) == person.getPersonID()) {
                                    cbPersonId.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }
                    }

//                    Display online course detail into text field
                    for (OnlineCourseModel course : OnlineCourseBUS.getInstance().getAllModels()) {
                        if (course.getId() == courseId) {
                            txtUrl.setText(course.getUrl());
                            cbCourseOnsiteId.setSelectedIndex(0);
                            txtLocation.setText("");
                            txtDays.setText("");
                            txtTime.setText("");
//                            Set combo box selected index correspond to selected record
                            for (int i = 1; i < cbCouseOnlineId.getItemCount(); i++) {
                                if (Integer.parseInt(cbCouseOnlineId.getItemAt(i).toString()) == course.getId()) {
                                    cbCouseOnlineId.setSelectedIndex(i);
                                    break;
                                }
                            }
                            return;
                        }
                    }
//                    Display onsite course detail into text field
                    for (OnsiteCourseModel course : OnsiteCourseBUS.getInstance().getAllModels()) {
                        if (course.getId() == courseId) {
                            txtLocation.setText(course.getLocation());
                            txtDays.setText(course.getDays());
                            txtTime.setText(course.getTime().toString());
                            txtUrl.setText("");
                            cbCouseOnlineId.setSelectedIndex(0);
//                            Set combo box selected index correspond to selected record
                            for (int i = 1; i < cbCourseOnsiteId.getItemCount(); i++) {
                                if (Integer.parseInt(cbCourseOnsiteId.getItemAt(i).toString()) == course.getId()) {
                                    cbCourseOnsiteId.setSelectedIndex(i);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        });

//      Click enter do search
        txtSearch.addActionListener(e -> {
            btnSearch.doClick();
        });
//        Button Search listener
        btnSearch.addActionListener(e -> {
            String search = txtSearch.getText().trim();
            CourseInstructorBUS.getInstance().refresh();
            DefaultTableModel model_table = (DefaultTableModel) tbList.getModel();
            model_table.setRowCount(0);

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (CourseInstructorModel ci : CourseInstructorBUS.getInstance().getAllModels()) {
//            Find first name and last name
                for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
                    if (person.getPersonID() == ci.getPersonID()) {
//                    Find title from course id
                        for (CourseModel course : CourseBUS.getInstance().getAllModels()) {
                            if (course.getId() == ci.getCourseID()) {
//                                    Get all detail infor for CourseInstructor
                                String joinResult = String.format("%d %s %d %s %s",
                                        ci.getCourseID(),
                                        course.getTitle(),
                                        ci.getPersonID(),
                                        person.getFirstName(),
                                        person.getLastName());
//                                    Check if search text field match with the detail infor
                                if (joinResult.toLowerCase().contains(search.toLowerCase())) {
                                    model_table.addRow(new Object[]{
                                            ci.getCourseID(),
                                            course.getTitle(),
                                            ci.getPersonID(),
                                            person.getFirstName(),
                                            person.getLastName()
                                    });
                                }
                            }
                        }
                    }
                }
            }
        });

//        Assign Button listener
        btnAssign.addActionListener(e -> {
//            Check if user choose an instructor
            if (cbPersonId.getSelectedIndex() != 0) {
                int perSonId = Integer.parseInt(Objects.requireNonNull(cbPersonId.getSelectedItem()).toString());
//                Chech if user choose a course
                if (cbCourseOnsiteId.getSelectedIndex() == 0 && cbCouseOnlineId.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please choose a course", "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (cbCourseOnsiteId.getSelectedIndex() != 0 && cbCouseOnlineId.getSelectedIndex() != 0) {
                    int courseOnlineId = Integer.parseInt(Objects.requireNonNull(cbCouseOnlineId.getSelectedItem()).toString());
                    int courseOnSiteId = Integer.parseInt(Objects.requireNonNull(cbCourseOnsiteId.getSelectedItem()).toString());
                    int resultOnline = CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(courseOnlineId, perSonId));
                    int resultOnSite = CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(courseOnSiteId, perSonId));

                    if (resultOnline == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully for online course");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed");
                    }
                    if (resultOnSite == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully for onsite course");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed");
                    }
                } else if (cbCourseOnsiteId.getSelectedIndex() != 0) {
                    int courseOnSiteId = Integer.parseInt(Objects.requireNonNull(cbCourseOnsiteId.getSelectedItem()).toString());
                    if (CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(courseOnSiteId, perSonId)) == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed");
                    }
                } else {
                    int courseOnlineId = Integer.parseInt(Objects.requireNonNull(cbCouseOnlineId.getSelectedItem()).toString());
                    if (CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(courseOnlineId, perSonId)) == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please choose an instructor", "Attention",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //    Not assign button listener
        buttonNoAssign.addActionListener(e -> {
            int selectedRow = tbList.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please choose an instructor", "Attention",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int courseId = Integer.parseInt(tbList.getValueAt(selectedRow, 0).toString());
            int personId = Integer.parseInt(tbList.getValueAt(selectedRow, 2).toString());
            if (CourseInstructorBUS.getInstance().deleteModel(courseId, personId) == 1) {
                JOptionPane.showMessageDialog(null, "Delete successfully");
                tbList.clearSelection();
                showListCourseInstructor();
            } else {
                JOptionPane.showMessageDialog(null, "Delete failed");
            }
        });
    }

    private void showListCourseInstructor() {
        CourseInstructorBUS.getInstance().refresh();
        DefaultTableModel model_table = (DefaultTableModel) tbList.getModel();
        model_table.setRowCount(0);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (CourseInstructorModel ci : CourseInstructorBUS.getInstance().getAllModels()) {
//            Find first name and last name
            for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
                if (person.getPersonID() == ci.getPersonID()) {
//                    Find title from course id
                    for (CourseModel course : CourseBUS.getInstance().getAllModels()) {
                        if (course.getId() == ci.getCourseID()) {
                            model_table.addRow(new Object[]{
                                    ci.getCourseID(),
                                    course.getTitle(),
                                    ci.getPersonID(),
                                    person.getFirstName(),
                                    person.getLastName()
                            });
                        }
                    }
                }
            }
        }

    }

    private void loadDataComboBox() {
        cbPersonId.addItem("");
        cbCouseOnlineId.addItem("");
        cbCourseOnsiteId.addItem("");
//        Data for combo box person
        for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
            cbPersonId.addItem(String.valueOf(person.getPersonID()));
        }

//        Data for combo box online course
        for (CourseModel courseOnline : OnlineCourseBUS.getInstance().getAllModels()) {
            cbCouseOnlineId.addItem(String.valueOf(courseOnline.getId()));
        }

//        Data for combo box onsite course
        for (CourseModel courseOnSite : OnsiteCourseBUS.getInstance().getAllModels()) {
            cbCourseOnsiteId.addItem(String.valueOf(courseOnSite.getId()));
        }
    }
    
    private void initComponents() {

        panelHeader = new JPanel();
        panelPerson = new JPanel();
        panelSearch = new JPanel();
        labelPersonID = new JLabel();
        cbPersonId = new JComboBox<>();
        labelLastName = new JLabel();
        txtLastName = new JTextField();
        labelFirstName = new JLabel();
        txtFirstName = new JTextField();
        panelOnlineCourse = new JPanel();
        labelCourseID = new JLabel();
        cbCouseOnlineId = new JComboBox<>();
        labelURL = new JLabel();
        txtUrl = new JTextField();
        panelOnsiteCourse = new JPanel();
        labelOnsiteCourseID = new JLabel();
        cbCourseOnsiteId = new JComboBox<>();
        labelLocation = new JLabel();
        txtLocation = new JTextField();
        labelDay = new JLabel();
        txtDays = new JTextField();
        labelTime = new JLabel();
        txtTime = new JTextField();
        panelButton = new JPanel();
        btnAssign = new JButton();
        btnSearch = new JButton();
        txtSearch = new JTextField();
        buttonNoAssign = new JButton();
        scrollPane = new JScrollPane();
        tbList = new JTable();

        setLayout(new BorderLayout());

        panelHeader.setLayout(new GridLayout(5, 0, 0, 10));

        panelPerson.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelPerson.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelPersonID.setText("Person ID");
        panelPerson.add(labelPersonID);
        
        panelPerson.add(cbPersonId);

        labelLastName.setText("Last Name");
        panelPerson.add(labelLastName);

        txtLastName.setPreferredSize(new Dimension(180, 22));
        panelPerson.add(txtLastName);

        labelFirstName.setText("First Name");
        panelPerson.add(labelFirstName);

        txtFirstName.setPreferredSize(new Dimension(180, 22));
        panelPerson.add(txtFirstName);

        panelHeader.add(panelPerson);

        panelOnlineCourse.setBorder(BorderFactory.createTitledBorder("Online Course"));
        panelOnlineCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelCourseID.setText("Course ID");
        panelOnlineCourse.add(labelCourseID);
        
        panelOnlineCourse.add(cbCouseOnlineId);

        labelURL.setText("URL");
        panelOnlineCourse.add(labelURL);

        txtUrl.setPreferredSize(new Dimension(200, 22));
        panelOnlineCourse.add(txtUrl);

        panelHeader.add(panelOnlineCourse);

        panelOnsiteCourse.setBorder(BorderFactory.createTitledBorder("Onsite Course"));
        panelOnsiteCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelOnsiteCourseID.setText("Course ID");
        panelOnsiteCourse.add(labelOnsiteCourseID);
        
        panelOnsiteCourse.add(cbCourseOnsiteId);

        labelLocation.setText("Location");
        panelOnsiteCourse.add(labelLocation);

        txtLocation.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtLocation);

        labelDay.setText("Days");
        panelOnsiteCourse.add(labelDay);

        txtDays.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtDays);

        labelTime.setText("Time");
        panelOnsiteCourse.add(labelTime);

        txtTime.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtTime);

        panelHeader.add(panelOnsiteCourse);

        btnAssign.setText("Assign");
        panelButton.add(btnAssign);

        buttonNoAssign.setText("No Assign");
        buttonNoAssign.setToolTipText("");
        panelButton.add(buttonNoAssign);

        panelHeader.add(panelButton);

//        Add search text field and button search
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
        txtSearch.setPreferredSize(new Dimension(180, 22));
        btnSearch.setText("Search");
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);
        panelHeader.add(panelSearch);

        add(panelHeader, BorderLayout.PAGE_START);

        scrollPane.setViewportView(tbList);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton btnSearch;
    private JButton btnAssign;
    private JButton buttonNoAssign;
    private JComboBox<String> cbCouseOnlineId;
    private JComboBox<String> cbCourseOnsiteId;
    private JComboBox<String> cbPersonId;
    private JPanel panelHeader;
    private JPanel panelPerson;
    private JPanel panelSearch;
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
    private JTable tbList;
    private JTextField txtSearch;
    private JTextField txtDays;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtLocation;
    private JTextField txtTime;
    private JTextField txtUrl;
}
