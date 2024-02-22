package src.main.java.com.school.schoolmanagement.gui;

import src.main.java.com.school.schoolmanagement.bus.*;
import src.main.java.com.school.schoolmanagement.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CourseManagement {
    private JPanel pnlMain;
    private JPanel pnlList;
    private JPanel pnlInfor;
    private JPanel pnlSearch;
    private JPanel pnlGeneralInfor;
    private JTextField txtCourseId;
    private JTextField txtTitle;
    private JTextField txtCredits;
    private JComboBox cbDepartment;
    private JPanel pnlSpecificInfor;
    private JRadioButton rdOnline;
    private JRadioButton rdOnSite;
    private JTextField txtUrl;
    private JTextField txtLocation;
    private JTextField txtDays;
    private JTextField txtTime;
    private JLabel lbCourseId;
    private JLabel lbTitle;
    private JLabel lbCredits;
    private JLabel lbDepartment;
    private JLabel lbUrl;
    private JLabel lbLocation;
    private JLabel lbDays;
    private JLabel lbTime;
    private JTextField txtSearch;
    private JTable tblList;
    private JPanel pnlButton;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnUpdate;
    private JPanel panel_onSite;

    public CourseManagement() {
        pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rdOnline);
        btnGroup.add(rdOnSite);

        lbUrl.setVisible(false);
        txtUrl.setVisible(false);
        panel_onSite.setVisible(false);

        JFrame frame = new JFrame("Course Management");
        frame.setContentPane(pnlMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        tblList.setModel(new DefaultTableModel(
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

        rdOnline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdOnline.isSelected()) {
                    // Hiển thị các thành phần của A
                    lbUrl.setVisible(true);
                    txtUrl.setVisible(true);
                    txtLocation.setText("");
                    txtDays.setText("");
                    txtTime.setText("");
                    // Ẩn các thành phần của B
                    panel_onSite.setVisible(false);
                }
            }
        });

        rdOnSite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rdOnSite.isSelected()) {
                    // Hiển thị các thành phần của B
                    panel_onSite.setVisible(true);
                    txtUrl.setText("");
                    // Ẩn các thành phần của A
                    lbUrl.setVisible(false);
                    txtUrl.setVisible(false);
                }
            }
        });

        cbDepartment.addItem("");
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            cbDepartment.addItem(department.getName()+"");
        }

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblList.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn xóa", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhân viên này?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    int courseID = (int) tblList.getModel().getValueAt(selectedRow, 0);
                    deleteCourse(courseID);
                    showListCourse();
                }
            }
        });

        tblList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleRowSelection();
            }
        });


        showListCourse();
    }

    public void showListCourse() {
        CourseBUS.getInstance().refresh();
        DefaultTableModel model_table = (DefaultTableModel) tblList.getModel();
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

    public void clearForm() {
        txtCourseId.setText("");
        txtTitle.setText("");
        txtCredits.setText("");
        cbDepartment.setSelectedIndex(-1);
        txtUrl.setText("");
        txtLocation.setText("");
        txtDays.setText("");
        txtTime.setText("");
    }

    public void addCourse() {
        if (!rdOnline.isSelected() && !rdOnSite.isSelected()) {
            JOptionPane.showMessageDialog(null, "Chọn hình thức khóa học(Online,onsite)");
            return;
        }

        int CourseID = Integer.parseInt(txtCourseId.getText() + "");
        String title = txtTitle.getText() + "";
        int credits = Integer.parseInt(txtCredits.getText() + "");
        String departmentName = cbDepartment.getSelectedItem() + "";
        int departmentID = 0;
        for (DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            if (departmentName.equals(department.getName())) {
                departmentID = department.getDepartmentID();
                CourseModel newCourse = new CourseModel(CourseID, title, credits, departmentID);
                int result = CourseBUS.getInstance().addModel(newCourse);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại");
                }
            }
        }

        if (rdOnline.isSelected()) {
            String url = txtUrl.getText() + "";
            OnlineCourseModel onlineCourse = new OnlineCourseModel(CourseID, title, credits, departmentID, url);
            System.out.println("test cai coi: " + onlineCourse);
            int newOnlineCourse = OnlineCourseBUS.getInstance().addModel(onlineCourse);
            if (newOnlineCourse == 1) {
                System.out.println("Online course added");
            }
        } else if (rdOnSite.isSelected()) {
            String timeText = txtTime.getText();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            java.util.Date parsedDate;  // Change to java.util.Date

            try {
                parsedDate = timeFormat.parse(timeText);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Convert java.util.Date to java.sql.Time
            java.sql.Time time = new java.sql.Time(parsedDate.getTime());

            OnsiteCourseModel onsiteCourse = new OnsiteCourseModel(CourseID, txtTitle.getText(), Integer.parseInt(txtCredits.getText()), departmentID, txtLocation.getText(), txtDays.getText(), time);
            int newOnsiteCourse = OnsiteCourseBUS.getInstance().addModel(onsiteCourse);
            if (newOnsiteCourse == 1) {
                System.out.println("Onsite course added");
            }
        }
        clearForm();
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
        int selectedRow = tblList.getSelectedRow();
        if(selectedRow != -1) {
            Object courseID = tblList.getValueAt(selectedRow,0);
            Object title = tblList.getValueAt(selectedRow,1);
            Object credit = tblList.getValueAt(selectedRow,2);
            Object departmentName = tblList.getValueAt(selectedRow,3);
            Object status = tblList.getValueAt(selectedRow,4);

            txtCourseId.setText(courseID.toString());
            txtTitle.setText(title.toString());
            txtCredits.setText(credit.toString());
            cbDepartment.setSelectedItem(departmentName.toString());

            if(status.toString().equals("Online")) {
                rdOnline.setSelected(true);
                for(OnlineCourseModel online : OnlineCourseBUS.getInstance().getAllModels()) {
                    if(Integer.parseInt(courseID.toString()) == online.getId()) {
                        txtUrl.setText(online.getUrl());
                        break;
                    }
                }
                txtUrl.setVisible(true);
                rdOnSite.setSelected(false);
                panel_onSite.setVisible(false);
            }else if(status.toString().equals("Onsite")) {
                rdOnSite.setSelected(true);
                for(OnsiteCourseModel onsite : OnsiteCourseBUS.getInstance().getAllModels()) {
                    if(Integer.parseInt(courseID.toString()) == onsite.getId()) {
                        txtLocation.setText(onsite.getLocation());
                        txtDays.setText(onsite.getDays());
                        txtTime.setText(onsite.getTime()+"");
                    }
                }
                panel_onSite.setVisible(true);
                rdOnline.setSelected(false);
                txtUrl.setVisible(false);
            }
        }
    }

        public static void main(String[] args) {
        new CourseManagement();
    }
}
