import java.util.ArrayList;
import java.util.List;

public class Module {
    private int id;
    private static int idTracker = 1;
    private String name;
    List<Enrollment> enrollments;


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

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    double getAverageGrade() {
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

    @Override
    public String toString(){
        return (getName() + " (ID: " + getId() + ")");
    }

    public void addEnrollment(Enrollment newEnrollment){
        this.enrollments.add(newEnrollment);
    }
}
