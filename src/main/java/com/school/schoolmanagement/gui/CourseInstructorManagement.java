package com.school.schoolmanagement.gui;

import com.school.schoolmanagement.bus.*;
import com.school.schoolmanagement.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        tbList.setFont(new Font("Serif", Font.PLAIN, 20));
        tbList.setRowHeight(24);

        txtUrl.setEditable(false);
        txtDays.setEditable(false);
        txtLocation.setEditable(false);
        txtTime.setEditable(false);

//        Add padding for panel
        setBorder(new EmptyBorder(10, 10, 10 ,10));

        //        combo box CourseOnlineId action listener
        cbCouseOnlineId.addActionListener(e -> {
            try {
                OnlineCourseModel selectedItem = (OnlineCourseModel) cbCouseOnlineId.getSelectedItem();
                txtUrl.setText(selectedItem.getUrl());
            } catch (ClassCastException ex) {
                txtUrl.setText("");
                ex.printStackTrace();
            }
        });

        //        combo box CourseOnsiteId action listener
        cbCourseOnsiteId.addActionListener(e -> {
            try {
                OnsiteCourseModel selectedItem = (OnsiteCourseModel) cbCourseOnsiteId.getSelectedItem();

                txtLocation.setText(selectedItem.getLocation());
                txtDays.setText(selectedItem.getDays());
                txtTime.setText(selectedItem.getTime().toString());
            } catch (ClassCastException ex) {
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
                    for (int i = 1; i < cbPersonId.getItemCount(); i++) {
                        PersonModel temp = (PersonModel) cbPersonId.getItemAt(i);
                        if (temp.getPersonID() == personId) {
                            cbPersonId.setSelectedIndex(i);
                            break;
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
                                OnlineCourseModel temp = (OnlineCourseModel) cbCouseOnlineId.getItemAt(i);
                                if (temp.getId() == course.getId()) {
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
                                OnsiteCourseModel temp = (OnsiteCourseModel) cbCourseOnsiteId.getItemAt(i);
                                if (temp.getId() == course.getId()) {
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
                PersonModel person = (PersonModel) cbPersonId.getSelectedItem();
                int perSonId = person.getPersonID();
//                Chech if user choose a course
                if (cbCourseOnsiteId.getSelectedIndex() == 0 && cbCouseOnlineId.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "Please choose a course", "Attention",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (cbCourseOnsiteId.getSelectedIndex() != 0 && cbCouseOnlineId.getSelectedIndex() != 0) {
                    OnlineCourseModel onlineCourse = (OnlineCourseModel) cbCouseOnlineId.getSelectedItem();
                    OnsiteCourseModel onsiteCourse = (OnsiteCourseModel) cbCourseOnsiteId.getSelectedItem();
                    int resultOnline = CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(onlineCourse.getId(), perSonId));
                    int resultOnSite = CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(onsiteCourse.getId(), perSonId));

                    if (resultOnline == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully for online course");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed for online course");
                    }
                    if (resultOnSite == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully for onsite course");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed for onsite course");
                    }
                } else if (cbCourseOnsiteId.getSelectedIndex() != 0) {
                    OnsiteCourseModel onsiteCourse = (OnsiteCourseModel) cbCouseOnlineId.getSelectedItem();
                    if (CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(onsiteCourse.getId(), perSonId)) == 1) {
                        JOptionPane.showMessageDialog(null, "Add successfully");
                        tbList.clearSelection();
                        showListCourseInstructor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Add failed");
                    }
                } else {
                    OnlineCourseModel onlineCourse = (OnlineCourseModel) cbCouseOnlineId.getSelectedItem();
                    if (CourseInstructorBUS.getInstance().addModel(new CourseInstructorModel(onlineCourse.getId(), perSonId)) == 1) {
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

        showListCourseInstructor();
        loadDataComboBox();

        cbPersonId.setSelectedIndex(0);
        cbCourseOnsiteId.setSelectedIndex(0);
        cbCouseOnlineId.setSelectedIndex(0);
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
            cbPersonId.addItem(person);
        }

//        Data for combo box online course
        for (OnlineCourseModel courseOnline : OnlineCourseBUS.getInstance().getAllModels()) {
            cbCouseOnlineId.addItem(courseOnline);
        }

//        Data for combo box onsite course
        for (OnsiteCourseModel courseOnSite : OnsiteCourseBUS.getInstance().getAllModels()) {
            cbCourseOnsiteId.addItem(courseOnSite);
        }
    }

    private void initComponents() {

        panelHeader = new JPanel();
        panelPerson = new JPanel();
        panelSearch = new JPanel();
        labelPersonID = new JLabel();
        cbPersonId = new JComboBox<>();
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

        labelPersonID.setText("Person");
        labelPersonID.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelPerson.add(labelPersonID);

        panelPerson.add(cbPersonId);
        cbPersonId.setPreferredSize(new Dimension(220, 28));
        cbPersonId.setFont(new Font("Serif", Font.PLAIN, 18));

        panelHeader.add(panelPerson);

        panelOnlineCourse.setBorder(BorderFactory.createTitledBorder("Online Course"));
        panelOnlineCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelCourseID.setText("Course online title");
        labelCourseID.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnlineCourse.add(labelCourseID);

        panelOnlineCourse.add(cbCouseOnlineId);
        cbCouseOnlineId.setPreferredSize(new Dimension(220, 28));
        cbCouseOnlineId.setFont(new Font("Serif", Font.PLAIN, 18));

        labelURL.setText("URL");
        labelURL.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnlineCourse.add(labelURL);

        txtUrl.setPreferredSize(new Dimension(200, 22));
        txtUrl.setFont(new Font("Serif", Font.PLAIN, 18));
        panelOnlineCourse.add(txtUrl);

        panelHeader.add(panelOnlineCourse);

        panelOnsiteCourse.setBorder(BorderFactory.createTitledBorder("Onsite Course"));
        panelOnsiteCourse.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelOnsiteCourseID.setText("Course onsite title");
        labelOnsiteCourseID.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnsiteCourse.add(labelOnsiteCourseID);

        panelOnsiteCourse.add(cbCourseOnsiteId);
        cbCourseOnsiteId.setPreferredSize(new Dimension(220, 28));
        cbCourseOnsiteId.setFont(new Font("Serif", Font.PLAIN, 18));

        labelLocation.setText("Location");
        labelLocation.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnsiteCourse.add(labelLocation);

        txtLocation.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtLocation);
        txtLocation.setFont(new Font("Serif", Font.PLAIN, 18));

        labelDay.setText("Days");
        labelDay.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnsiteCourse.add(labelDay);

        txtDays.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtDays);
        txtDays.setFont(new Font("Serif", Font.PLAIN, 18));

        labelTime.setText("Time");
        labelTime.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 22));
        panelOnsiteCourse.add(labelTime);

        txtTime.setPreferredSize(new Dimension(140, 22));
        panelOnsiteCourse.add(txtTime);
        txtTime.setFont(new Font("Serif", Font.PLAIN, 18));

        panelHeader.add(panelOnsiteCourse);

        btnAssign.setText("Assign");
        panelButton.add(btnAssign);
        btnAssign.setFont(new Font("Serif", Font.BOLD, 18));

        buttonNoAssign.setText("No Assign");
        buttonNoAssign.setToolTipText("");
        buttonNoAssign.setFont(new Font("Serif", Font.BOLD, 18));
        panelButton.add(buttonNoAssign);

        panelHeader.add(panelButton);

//        Add search text field and button search
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT));
        txtSearch.setPreferredSize(new Dimension(200, 28));
        btnSearch.setText("Search");
        btnSearch.setFont(new Font("Serif", Font.BOLD, 18));
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
    private JComboBox<Object> cbCouseOnlineId;
    private JComboBox<Object> cbCourseOnsiteId;
    private JComboBox<Object> cbPersonId;
    private JPanel panelHeader;
    private JPanel panelPerson;
    private JPanel panelSearch;
    private JPanel panelOnlineCourse;
    private JPanel panelOnsiteCourse;
    private JLabel labelCourseID;
    private JLabel labelDay;
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
    private JTextField txtLocation;
    private JTextField txtTime;
    private JTextField txtUrl;
}
