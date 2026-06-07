import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private static int idTracker = 1;
    private String name;
    private List<Enrollment> enrollmentList;

    // Constructor for new students
    Student(String name){
        this.id = idTracker++;
        this.name = name;
        this.enrollmentList = new ArrayList<>();
    }

    // Constructor for saved Students
    Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrollmentList = new ArrayList<>();

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

    public List<Enrollment> getEnrollmentList() {
        return enrollmentList;
    }

    @Override
    public String toString(){
        return (getName() + " (ID: " + getId() + ")");
    }

    public void addEnrollment(Enrollment newEnrollment){
        this.enrollmentList.add(newEnrollment);
    }
}
