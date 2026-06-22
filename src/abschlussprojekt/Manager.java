package abschlussprojekt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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

    public Student getStudentByID(int studentID) throws StudentNotFoundException {
        Student student = students.get(studentID);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentID + " not found.");
        }
        return student;
    }

    public Module getModuleByID(int moduleID) throws ModuleNotFoundException {
        Module module = modules.get(moduleID);
        if(module == null) {
            throw new ModuleNotFoundException(("Module with ID " + moduleID)+ " not found");
        }
        return module;
    }

    public void loadStudentFromDatabase(int id, String name){
        Student savedStudent = new Student(id, name);
        students.put(savedStudent.getID(), savedStudent);
    }

    public void loadModuleFromDatabase(int id, String name){
        Module savedModule = new Module(id, name);
        modules.put(savedModule.getID(), savedModule);
    }

    public void loadEnrollmentFromDatabase(int studentID, int moduleID, Double grade){
        Student loadedStudent = getStudentByID(studentID);
        Module loadedModule = getModuleByID(moduleID);
        Enrollment savedEnrollment = new Enrollment(loadedStudent, loadedModule, grade);
        loadedStudent.addEnrollment(savedEnrollment);
        loadedModule.addEnrollment(savedEnrollment);
    }

    public Student addStudent(String name){
        Student newStudent = new Student(name);
        students.put(newStudent.getID(), newStudent);
        return newStudent;
    }

    public void deleteStudent(int studentID){
        Student studentToDelete = getStudentByID(studentID);
        for (Enrollment enrollment : studentToDelete.getEnrollments()) {
            Module module = enrollment.getModule();
            module.getEnrollments().remove(enrollment);
        }
        students.remove(studentID);
    }

    public void deleteModule(int moduleID){
        Module moduleToDelete = getModuleByID(moduleID);
        for (Enrollment enrollment : moduleToDelete.getEnrollments()) {
            Student student = enrollment.getStudent();
            student.getEnrollments().remove(enrollment);
        }
        modules.remove(moduleID);
    }

    public Module addModule(String name){
        Module newModule = new Module(name);
        modules.put(newModule.getID(), newModule);
        return newModule;
    }

    public Enrollment enrollStudent(int studentID, int moduleID)
            throws StudentNotFoundException, ModuleNotFoundException {
        Student student = getStudentByID(studentID);
        Module module = getModuleByID(moduleID);

        for (Enrollment enrollment : student.getEnrollments())
            if (enrollment.getModule().getID() == moduleID) {
                throw new DuplicateEnrollmentException("Student is already enrolled in that module.");
            }

        Enrollment newEnrollment = new Enrollment(student, module);
        student.addEnrollment(newEnrollment);
        module.addEnrollment(newEnrollment);
        return newEnrollment;
    }

    public void setEnrollmentGrade(int studentID, int moduleID, double grade)
            throws StudentNotFoundException, ModuleNotFoundException, InvalidGradeException {
        if ((grade >= 1.0 && grade <= 4.0) || grade == 5.0){
            Student student = getStudentByID(studentID);
            if (student.getEnrollments().isEmpty()) {
                throw new ModuleNotFoundException("This student is not enrolled in any modules.");
            }
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getModule().getID() == moduleID){
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

    public boolean hasGrade(int studentID, int moduleID)
        throws StudentNotFoundException, ModuleNotFoundException {
        Student student = getStudentByID(studentID);
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getModule().getID() == moduleID) {
                return enrollment.getGrade() != null;
            }
        } throw new ModuleNotFoundException("This student is not enrolled in that module.");
    }

    public void unenrollStudent(int studentID, int moduleID)
            throws StudentNotFoundException, ModuleNotFoundException {

        Student student = getStudentByID(studentID);
        Module module = getModuleByID(moduleID);
        Enrollment enrollmentToRemove = null;

        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getModule().getID() == moduleID) {
                enrollmentToRemove = enrollment;
                break;
            }
        }

        if (enrollmentToRemove != null) {
            student.getEnrollments().remove(enrollmentToRemove);
            module.getEnrollments().remove(enrollmentToRemove);
        } else {
            throw new ModuleNotFoundException("This student is not enrolled in that module");
        }
    }

    public List<Student> getStudentsSortedByGrade() {
        List<Student> list = new ArrayList<>(students.values());
        // Comparator.nullsLast to put all the nulls at the bottom of the list
        list.sort(Comparator.comparing(Student::getAverageGrade, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Student::getID));
        return list;
    }

    public List<Student> getStudentsSortedByName() {
        List<Student> list = new ArrayList<>(students.values());
        list.sort(Comparator.comparing(Student::getName).thenComparing(Student::getID));
        return list;
    }

    public List<Module> getModulesSortedByGrade() {
        List<Module> list = new ArrayList<>(modules.values());
        list.sort(Comparator.comparing(Module::getAverageGrade, Comparator.nullsLast(Comparator.naturalOrder())).thenComparing(Module::getID));
        return list;
    }

    public List<Module> getModulesSortedByName() {
        List<Module> list = new ArrayList<>(modules.values());
        list.sort(Comparator.comparing(Module::getName).thenComparing(Module::getID));
        return list;
    }
}

