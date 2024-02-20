package Model;

public class CourseInstructorModel {
    private int CourseID,PersonID;

    public CourseInstructorModel(int courseID, int personID) {
        this.CourseID = courseID;
        this.PersonID = personID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CourseInstructorModel{" +
                "CourseID=" + CourseID +
                ", PersonID=" + PersonID +
                '}';
    }
}
