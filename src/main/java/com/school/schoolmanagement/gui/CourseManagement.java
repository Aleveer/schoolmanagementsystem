package com.school.schoolmanagement.gui;

import com.school.schoolmanagement.bus.*;
import com.school.schoolmanagement.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
    private JButton btnRefresh;
    private JComboBox cbDepartmentName;
    private JComboBox cbStatus;
    private JButton btnSearch;

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

        cbDepartmentName.addItem("");
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            cbDepartmentName.addItem(department.getName()+"");
        }

        cbStatus.addItem("");
        cbStatus.addItem("Online");
        cbStatus.addItem("Onsite");

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
                showListCourse();
            }
        });

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

                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa khóa học này?", "Confirm Deletion",
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

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String searchValue = txtSearch.getText().trim();
                    String[] columnNames = {"CourseID" ,"Title", "Credits", "DepartmentID"};
                    List<CourseModel> searchResults = CourseBUS.getInstance().searchModel(searchValue, columnNames);
                    showSearchResult(searchResults);
                }
            }
        });


        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tblList.getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn dòng muốn sửa", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                } else {
                    int courseID = Integer.parseInt(txtCourseId.getText());
                    String title = txtTitle.getText();
                    int credit = Integer.parseInt(txtCredits.getText());
                    String departmentName = (String) cbDepartment.getSelectedItem();
                    int departmentID = 0;
                    for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
                        if(departmentName.equals(department.getName())) {
                            departmentID = department.getDepartmentID();
                        }
                    }
                    Object status = tblList.getValueAt(index,4);
                    if(status.toString().equals("Online")) {
                        String url = txtUrl.getText();
                        CourseModel updateModel = new CourseModel(courseID,title,credit,departmentID);
                        OnlineCourseModel updateOnlineModel = new OnlineCourseModel(courseID,null,0,0,url);
                        int resultModel = CourseBUS.getInstance().updateModel(updateModel);
                        int resultOnlineModel = OnlineCourseBUS.getInstance().updateModel(updateOnlineModel);
                        if(resultModel > 0 && resultOnlineModel > 0) {
                            JOptionPane.showMessageDialog(null, "Cập nhập thành công", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        showListCourse();
                        txtUrl.setVisible(true);
                        tblList.clearSelection();
                    }else if(status.toString().equals("Onsite")) {
                        String timeText = txtTime.getText();
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                        java.util.Date parsedDate;  // Change to java.util.Date
                        try {
                            parsedDate = timeFormat.parse(timeText);
                        } catch (ParseException e1) {
                            throw new RuntimeException(e1);
                        }
                        java.sql.Time time = new java.sql.Time(parsedDate.getTime());

                        CourseModel updateModel = new CourseModel(courseID,title,credit,departmentID);
                        OnsiteCourseModel updateOnsiteCourse = new OnsiteCourseModel(courseID,null,0,0,txtLocation.getText(),txtDays.getText(),time);

                        int resultModel = CourseBUS.getInstance().updateModel(updateModel);
                        int resultOnsiteModel = OnsiteCourseBUS.getInstance().updateModel(updateOnsiteCourse);

                        if(resultModel > 0 && resultOnsiteModel > 0) {
                            JOptionPane.showMessageDialog(null, "Cập nhập thành công", "Success",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        showListCourse();
                        panel_onSite.setVisible(true);
                        tblList.clearSelection();
                    }
                }

            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = txtSearch.getText();
                String departmentName = cbDepartmentName.getSelectedItem()+"";
                String status = cbStatus.getSelectedItem()+"";

                List<CourseModel> models = CourseBUS.getInstance().searchConditions(value,departmentName,status);
                showSearchResult(models);
            }
        });

        showListCourse();
    }

    public void showListCourse() {
        CourseBUS.getInstance().refresh();
        OnlineCourseBUS.getInstance().refresh();
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
        txtSearch.setText("");
        cbDepartmentName.setSelectedItem("");
        cbStatus.setSelectedItem("");
        rdOnSite.setSelected(false);
        txtLocation.setText("");
        txtDays.setText("");
        txtTime.setText("");
        rdOnline.setSelected(false);
        lbUrl.setVisible(false);
        txtUrl.setVisible(false);
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
        for(DepartmentModel department : DepartmentBUS.getInstance().getAllModels()) {
            if(departmentName.equals(department.getName())) {
                departmentID = department.getDepartmentID();
            }
        }

        if (rdOnline.isSelected()) {
            String url = txtUrl.getText() + "";
            CourseModel newCourse = new CourseModel(CourseID, title, credits, departmentID);
            OnlineCourseModel onlineCourse = new OnlineCourseModel(CourseID, title, credits, departmentID, url);
            int resultModel = CourseBUS.getInstance().addModel(newCourse);
            int newOnlineCourse = OnlineCourseBUS.getInstance().addModel(onlineCourse);
            if (resultModel == 1 && newOnlineCourse == 1) {
                JOptionPane.showMessageDialog(null, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại");
            }
//            String url = txtUrl.getText() + "";
//            OnlineCourseModel onlineCourse = new OnlineCourseModel(CourseID, title, credits, departmentID, url);
//            int newOnlineCourse = OnlineCourseBUS.getInstance().addModel(onlineCourse);
//            if (newOnlineCourse == 1) {
//                System.out.println("Online course added");
//            }
        } else if (rdOnSite.isSelected()) {
            String timeText = txtTime.getText();

            String timeRegex = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

            if (!timeText.matches(timeRegex)) {
                JOptionPane.showMessageDialog(null, "Invalid time format");
                return;
            }

            String[] timeParts = timeText.split(":");
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);
            int seconds = Integer.parseInt(timeParts[2]);

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
                JOptionPane.showMessageDialog(null, "Invalid time values. Please use valid hour, minute, and second values.");
                return;
            }

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date parsedDate;  // Change to java.util.Date

            try {
                parsedDate = timeFormat.parse(timeText);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Convert java.util.Date to java.sql.Time
            java.sql.Time time = new java.sql.Time(parsedDate.getTime());

            CourseModel newCourse = new CourseModel(CourseID, title, credits, departmentID);
            OnsiteCourseModel onsiteCourse = new OnsiteCourseModel(CourseID, txtTitle.getText(), Integer.parseInt(txtCredits.getText()), departmentID, txtLocation.getText(), txtDays.getText(), time);
            int resultModel = CourseBUS.getInstance().addModel(newCourse);
            int newOnsiteCourse = OnsiteCourseBUS.getInstance().addModel(onsiteCourse);
            if (resultModel == 1 && newOnsiteCourse == 1) {
                JOptionPane.showMessageDialog(null, "Thêm thành công");
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại");
            }
        }
        tblList.clearSelection();
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
                for(OnlineCourseModel online : OnlineCourseBUS.getInstance().getAllModels()) {
                    if(Integer.parseInt(courseID.toString()) == online.getId()) {
                        txtUrl.setText(online.getUrl());
                        break;
                    }
                }
                rdOnline.setSelected(true);
                lbUrl.setVisible(true);
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
                lbUrl.setVisible(false);
            }
        }
    }

    public void showSearchResult(List<CourseModel> search) {
        DefaultTableModel model = (DefaultTableModel) tblList.getModel();
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



    public static void main(String[] args) {
        new CourseManagement();
    }
}
