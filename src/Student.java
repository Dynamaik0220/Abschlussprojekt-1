import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private static int idTracker = 1;
    private String name;
    private List<Enrollment> enrollments;

    // Constructor for new students
    Student(String name){
        this.id = idTracker++;
        this.name = name;
        this.enrollments = new ArrayList<>();
    }

    // Constructor for saved Students
    Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrollments = new ArrayList<>();

        if (id >= idTracker) {      // to prevent idTracker from resetting and overwriting students
            idTracker = id + 1;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAverageGrade() {
        double sum = 0.0;
        int gradeCount = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() != 0.0) {
                sum += e.getGrade();
                gradeCount++;
            }
        }
        if (sum == 0.0){
            return 0.0;
        }
        return (sum / gradeCount);
    }

    public List<Enrollment> getEnrollmentList() {
        return enrollments;
    }

    @Override
    public String toString(){
        return (getName() + " (ID: " + getId() + ")");
    }

    public void addEnrollment(Enrollment newEnrollment){
        this.enrollments.add(newEnrollment);
    }
}
