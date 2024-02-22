package main.java.com.school.schoolmanagement.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

    public CourseInstructorManagement() {
        pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlOnlineCourse.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Online Course"));
        pnlOnSiteCourse.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "On Site Course"));

        JFrame frame = new JFrame("Course Instructor Management");
        frame.setContentPane(pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        new CourseInstructorManagement();
    }
}
