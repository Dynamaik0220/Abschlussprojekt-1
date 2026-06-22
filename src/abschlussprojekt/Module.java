package abschlussprojekt;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private int id;
    private static int idTracker = 101;
    private String name;
    private List<Enrollment> enrollments;


    Module(String name){
        this.name = name;
        this.id = idTracker++;
        this.enrollments = new ArrayList<>();
    }

    // Constructor for saved modules
    Module(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrollments = new ArrayList<>();

        if (id >= idTracker) {      // to prevent the idTracker from resetting and overwriting modules
            idTracker = id + 1;
        }
    }

    public int getID(){
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
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
        if (gradeCount == 0){
            return null;
        }
        return (sum / gradeCount);
    }

    @Override
    public String toString() {
        return (getName() + " (ID: " + getID() + ")");
    }

    public String toStringLong() {
        if (getAverageGrade() == null) {
            // 30 spaces for name (left alligned), 3 for id (right alligned)
            return String.format("%-30s (ID: %3s) | no grades yet", getName(), getID());
        } else {
            // 2 decimals for average grade
            return String.format("%-30s (ID: %3s) | Average grade: %.2f", getName(), getID(), getAverageGrade());
        }
    }

    public void addEnrollment(Enrollment newEnrollment){
        this.enrollments.add(newEnrollment);
    }
}
