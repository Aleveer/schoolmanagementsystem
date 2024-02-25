package com.school.schoolmanagement.gui;

import com.school.schoolmanagement.bus.*;
import com.school.schoolmanagement.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CourseInstructorManagement {
    private JPanel pnlMain;
    private JPanel pnlPerson;
    private JPanel pnlOnlineCourse;
    private JPanel pnlOnSiteCourse;
    private JPanel pnlList;
    private JTextField txtPersonId;
    private JTextField txtLastName;
    private JTextField txtFirstName;
    private JLabel lbPersonId;
    private JLabel lbLastName;
    private JLabel lbFirstName;
    private JComboBox cbCouseOnlineId;
    private JTextField txtUrl;
    private JLabel lbCourseOnlineId;
    private JLabel lbUrl;
    private JComboBox cbCourseOnsiteId;
    private JTextField txtLocation;
    private JTextField txtDays;
    private JTextField txtTime;
    private JLabel lbCourseOnsiteId;
    private JLabel lbLocation;
    private JLabel lbDays;
    private JLabel lbTime;
    private JTable tbList;
    private JButton btnAssign;
    private JButton btnNotAssign;
    private JComboBox cbPersonId;
    private JPanel pnlButton;
    private JPanel pnlSearch;
    private JTextField txtSearch;
    private JButton btnSearch;

    public CourseInstructorManagement() {
        pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlOnlineCourse.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Online Course"));
        pnlOnSiteCourse.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "On Site Course"));

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

        JFrame frame = new JFrame("Course Instructor Management");
        frame.setContentPane(pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

//        combo box person ID action listener
        cbPersonId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedItem = (int) cbPersonId.getSelectedItem();

                    for (PersonModel person : PersonBUS.getInstance().getAllModels()) {
                        if (person.getPersonID() == selectedItem) {
                            txtFirstName.setText(person.getFirstName());
                            txtLastName.setText(person.getLastName());
                        }
                    }
                } catch (ClassCastException ex) {
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    ex.printStackTrace();
                }
            }
        });

        //        combo box CourseOnlineId action listener
        cbCouseOnlineId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedItem = (int) cbCouseOnlineId.getSelectedItem();

                    for (OnlineCourseModel course : OnlineCourseBUS.getInstance().getAllModels()) {
                        if (course.getId() == selectedItem) {
                            txtUrl.setText(course.getUrl());
                        }
                    }
                } catch (ClassCastException ex) {
                    txtUrl.setText("");
                    ex.printStackTrace();
                }
            }
        });

        //        combo box CourseOnsiteId action listener
        cbCourseOnsiteId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedItem = (int) cbCourseOnsiteId.getSelectedItem();

                    for (OnsiteCourseModel course : OnsiteCourseBUS.getInstance().getAllModels()) {
                        if (course.getId() == selectedItem) {
                            txtLocation.setText(course.getLocation());
                            txtDays.setText(course.getDays());
                            txtTime.setText(course.getTime().toString());
                        }
                    }
                } catch (ClassCastException ex) {
                    txtLocation.setText("");
                    txtDays.setText("");
                    txtTime.setText("");
                    ex.printStackTrace();
                }
            }
        });

//        List Selection Listener for jTable
        tbList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbList.getSelectedRow();
//                Check if row is selected
                if (selectedRow != -1) {
//                    Get courseId and personId from jTable
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
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            cbPersonId.addItem(person.getPersonID());
        }

//        Data for combo box online course
        for (CourseModel courseOnline : OnlineCourseBUS.getInstance().getAllModels()) {
            cbCouseOnlineId.addItem(courseOnline.getId());
        }

//        Data for combo box onsite course
        for (CourseModel courseOnSite : OnsiteCourseBUS.getInstance().getAllModels()) {
            cbCourseOnsiteId.addItem(courseOnSite.getId());
        }
    }

    public static void main(String[] args) {
        new CourseInstructorManagement();
    }
}
