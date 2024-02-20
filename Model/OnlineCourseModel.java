package Model;

public class OnlineCourseModel extends CourseModel{
    private String url;


    public OnlineCourseModel(int id, String title, int credit, int departmentID, String url) {
        super(id, title, credit, departmentID);
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OnlineCourseModel{" +
                "url='" + url + '\'' +
                '}';
    }
}
