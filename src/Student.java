import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private static int idTracker = 1;
    private String name;
    List<Enrollment> enrollmentList;

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

        if (id >= idTracker) {      // to prevent the idTracker from resetting
            idTracker = id + 1;
        }
    }

    public void printEnrollmentList(){
        for (Enrollment enrollment : enrollmentList){
            System.out.println(enrollment.module.getName());
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



    public void addEnrollment(Enrollment newEnrollment){
        this.enrollmentList.add(newEnrollment);
    }
}
