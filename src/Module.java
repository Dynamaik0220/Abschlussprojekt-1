import java.util.ArrayList;
import java.util.List;

public class Module {
    private int id;
    private static int idTracker = 1;
    private String name;
    List<Enrollment> enrollmentList;


    Module(String name){
        this.name = name;
        this.id = idTracker++;
        this.enrollmentList = new ArrayList<>();
    }

    // Constructor for saved modules
    Module(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrollmentList = new ArrayList<>();

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

    @Override
    public String toString(){
        return (getName() + " (ID: " + getId() + ")");
    }
}
