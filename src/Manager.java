import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Student> students;
    private final HashMap<Integer, Module> modules;

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

    public Student getStudentById(int studentId) throws StudentNotFoundException {
        Student student = students.get(studentId);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }
        return student;
    }

    public Module getModuleById(int moduleId) throws ModuleNotFoundException {
        Module module = modules.get(moduleId);
        if(module == null) {
            throw new ModuleNotFoundException(("Module with ID " + moduleId)+ " not found");
        }
        return module;
    }

    public void loadStudentFromDatabase(int id, String name){
        Student savedStudent = new Student(id, name);
        students.put(savedStudent.getId(), savedStudent);
    }

    public void loadModuleFromDatabase(int id, String name){
        Module savedModule = new Module(id, name);
        modules.put(savedModule.getId(), savedModule);
    }

    public void loadEnrollmentFromDatabase(int studentId, int moduleId, double grade){
        Student loadedStudent = getStudentById(studentId);
        Module loadedModule = getModuleById(moduleId);
        Enrollment savedEnrollment = new Enrollment(loadedStudent, loadedModule, grade);
        loadedStudent.addEnrollment(savedEnrollment);
        loadedModule.addEnrollment(savedEnrollment);
    }

    public Student addStudent(String name){
        Student newStudent = new Student(name);
        students.put(newStudent.getId(), newStudent);
        return newStudent;
    }

    public void deleteStudent(int studentID){
        Student studentToDelete = getStudentById(studentID);
        if (studentToDelete == null){
            throw new StudentNotFoundException("Student with ID " + studentID + " not found");
        }

        for (Enrollment enrollment : studentToDelete.getEnrollments()) {
            Module module = enrollment.getModule();
            module.getEnrollments().remove(enrollment);
        }

        students.remove(studentID);
    }

    public void deleteModule(int moduleID){
        Module moduleToDelete = getModuleById(moduleID);
        if (moduleToDelete == null){
            throw new ModuleNotFoundException("Module with ID " + moduleID + " not found");
        }

        for (Enrollment enrollment : moduleToDelete.getEnrollments()) {
            Student student = enrollment.getStudent();
            student.getEnrollments().remove(enrollment);
        }

        modules.remove(moduleID);
    }

    public Module addModule(String name){
        Module newModule = new Module(name);
        modules.put(newModule.getId(), newModule);
        return newModule;
    }

    public Enrollment enrollStudent(int studentID, int moduleID)
            throws StudentNotFoundException, ModuleNotFoundException {
        Student student = getStudentById(studentID);
        Module module = getModuleById(moduleID);

        for (Enrollment enrollment : student.getEnrollments())
            if (enrollment.getModule().getId() == moduleID) {
                throw new DuplicateEnrollmentException("Student is already enrolled in that module.");
            }

        Enrollment newEnrollment = new Enrollment(student, module);
        student.addEnrollment(newEnrollment);
        module.addEnrollment(newEnrollment);
        return newEnrollment;
    }

    public void setEnrollmentGrade(int studentID, int moduleId, double grade)
            throws StudentNotFoundException, ModuleNotFoundException, InvalidGradeException {
        if ((grade >= 1.0 && grade <= 4.0) || grade == 5.0){
            Student student = getStudentById(studentID);
            if (student.getEnrollments().isEmpty()) {
                throw new ModuleNotFoundException("This student is not enrolled in any modules.");
            }
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getModule().getId() == moduleId){
                    enrollment.setGrade(grade);
                    enrollment.setPassed(grade != 5.0);
                    return;
                }
            }
            throw new ModuleNotFoundException("This student is not enrolled in that module.");
        } else {
            throw new InvalidGradeException("Invalid grade, please enter a grade between 1.0 and 4.0, or the grade 5.0");
        }
    }
}
