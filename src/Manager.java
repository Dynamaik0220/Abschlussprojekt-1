import java.util.HashMap;

public class Manager {
    private HashMap<Integer, Student> students;
    private HashMap<Integer, Module> modules;

    public Manager(){
        students = new HashMap<>();
        modules = new HashMap<>();
    }

    public HashMap<Integer, Student> getStudents() {
        return students;
    }

    public HashMap<Integer, Module> getModules() {
        return modules;
    }

    public void loadStudentFromDatabase(int id, String name){
        Student savedStudent = new Student(id, name);
        students.put(savedStudent.getId(), savedStudent);
    }

    public Student addStudent(String name){
        Student newStudent = new Student(name);
        students.put(newStudent.getId(), newStudent);
        return newStudent;
    }

    public Module addModule(String name){
        Module newModule = new Module(name);
        modules.put(newModule.getId(), newModule);
        return newModule;
    }

    public Enrollment enrollStudent(int studentID, int moduleID){
        Student foundStudent = students.get(studentID);
        Module foundModule = modules.get(moduleID);
        Enrollment newEnrollment = new Enrollment(foundStudent, foundModule);
        foundStudent.addEnrollment(newEnrollment);
        return newEnrollment;
    }
}
