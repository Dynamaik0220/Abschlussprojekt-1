package abschlussprojekt;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final int id;
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

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAverageGrade() {
        double sum = 0.0;
        int gradeCount = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() != null) {
                sum += e.getGrade();
                gradeCount++;
            }
        }
        if (gradeCount == 0) {
            return null;
        }
        return (sum / gradeCount);
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    @Override
    public String toString(){
        return (getName() + " (ID: " + getID() + ")");
    }
    public String toStringLong() {
        if (getAverageGrade() == null) {
            // 20 spaces for name (left alligned), 3 for id (right alligned)
            return String.format("%-20s (ID: %3s) | no grades yet", getName(), getID());
        } else {
           // 1 decimal for GPA
            return String.format("%-20s (ID: %3s) | GPA: %.1f", getName(), getID(), getAverageGrade());
        }
    }

    public void addEnrollment(Enrollment newEnrollment){
        this.enrollments.add(newEnrollment);
    }
}
