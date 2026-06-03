import java.util.List;

public class Module {
    private int id;
    private static int idTracker = 1;
    private String name;

    Module(String name){
        this.name = name;
        this.id = idTracker++;
        List<Enrollment> enrollmentList;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
}
