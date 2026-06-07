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
        students.remove(studentID);
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

        for (Enrollment enrollment : student.getEnrollmentList())
            if (enrollment.module.getId() == moduleID) {
                throw new DuplicateEnrollmentException("Student is already enrolled in that module.");
            }

        Enrollment newEnrollment = new Enrollment(student, module);
        student.addEnrollment(newEnrollment);
        return newEnrollment;
    }

    public void setEnrollmentGrade(int studentID, int moduleId, double grade)
            throws StudentNotFoundException, ModuleNotFoundException, InvalidGradeException {
        if ((grade >= 1.0 && grade <= 4.0) || grade == 5.0){
            Student student = getStudentById(studentID);
            if (student.getEnrollmentList().isEmpty()) {
                throw new ModuleNotFoundException("This student is not enrolled in any modules.");
            }
            for (Enrollment enrollment : student.getEnrollmentList()) {
                if (enrollment.module.getId() == moduleId){
                    enrollment.setGrade(grade);
                    if (grade == 5.0){
                        enrollment.setPassed(false);
                    } else {
                        enrollment.setPassed(true);
                    }
                    return;
                }
            }
            throw new ModuleNotFoundException("This student is not enrolled in that module.");
        } else {
            throw new InvalidGradeException("Invalid grade, please enter a grade between 1.0 and 4.0, or the grade 5.0");
        }
    }
}
