package main.java.com.school.schoolmanagement.models;

public class CourseModel {
    private int id;
    private String title;
    private int credit,departmentID;

    public CourseModel(int id, String title, int credit, int departmentID) {
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.departmentID = departmentID;
    }

    public CourseModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return "CourseModel [id=" + id + ", title=" + title + ", credit=" + credit + ", departmentID=" + departmentID
                + "]";
    }

    public static void main(String[] args) {
        System.out.println("chao ban");
    }
}
