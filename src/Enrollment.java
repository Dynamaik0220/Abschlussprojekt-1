public class Enrollment {
    Student student;
    Module module;
    double grade;
    boolean passed;

    Enrollment(Student student, Module module){
        this.student = student;
        this.module = module;
        this.passed = false;
    }
    public void addGrade(double grade){
        if ((grade >= 1.0 && grade <= 4.0) || grade == 5.0){
            this.grade = grade;
        } else {
            throw new InvalidGradeException("Invalid grade, please enter a grade between 1.0 and 4.0, or the grade 5.0");
        }
    }
}
