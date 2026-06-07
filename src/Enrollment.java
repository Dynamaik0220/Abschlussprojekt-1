public class Enrollment {
    Student student;
    Module module;
    double grade;
    boolean passed;

    // new Enrollments
    Enrollment(Student student, Module module){
        this.student = student;
        this.module = module;
        this.passed = false;
        this.grade = 0.0;       // default 0.0 to indicate no grade yet
    }

    // saved Enrollments
    Enrollment(Student student, Module module, double grade){
        this.student = student;
        this.module = module;
        this.grade = grade;
    }

    public double getGrade(){
        return grade;
    }

    public boolean isPassed(){
        return passed;
    }

    public void setPassed(boolean passed){
        this.passed = passed;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
