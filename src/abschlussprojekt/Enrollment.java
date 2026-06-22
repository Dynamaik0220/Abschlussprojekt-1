package abschlussprojekt;

public class Enrollment {
    private Student student;
    private Module module;
    private Double grade;       // Wrapper is nullable
    private boolean passed;

    // new Enrollments
    Enrollment(Student student, Module module){
        this.student = student;
        this.module = module;
        this.passed = false;
    }

    // saved Enrollments
    Enrollment(Student student, Module module, Double grade){
        this.student = student;
        this.module = module;
        this.grade = grade;
        this.passed = (grade != null && grade <= 4.0 && grade >= 1.0);
    }

    public Double getGrade(){
        return grade;
    }

    public Student getStudent() {
        return student;
    }

    public Module getModule() {
        return module;
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
