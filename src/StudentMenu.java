import java.util.List;

public class StudentMenu extends BaseMenu{

    public StudentMenu(Manager manager) {
        super(manager);
    }

    @Override
    public void start(){
        boolean exit = false;
        while (!exit) {
            System.out.println("""
               
                ----Student Submenu----
                
                Show all students: all
                Add student: add, Name
                View and edit Student: edit, id
                Return to main menu: back
                """);
            String[] input = readInput();
            switch (input[0]) {
                case "add":                          // add student
                    if (input.length == 2) {
                        Student newStudent = manager.addStudent(input[1]);
                        System.out.println("Student '" + newStudent.toString() +  " added.");
                    } else {
                        System.out.println("Invalid input, please use the exact format 'as, name'");
                    }
                    break;
                case "all":                          // show all students
                    printStudents();
                    break;
                case "edit":                          // enroll student
                    if (input.length == 2) {
                        startEditStudentMenu(input, Integer.parseInt(input[1]));
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, id'");
                    }
                    break;
                case "back":
                    exit = true;
                    break;
            }
        }
    }

    public void startEditStudentMenu(String[] input, int studentID) {
        boolean exitSubmenu = false;
        Student selectedStudent = manager.getStudentById(studentID);

        while (!exitSubmenu) {
            System.out.println("\n----Editing " + selectedStudent.toString() + "----");
            System.out.println("""   
                    
                    Show all information: info 
                    Enroll in module: enroll, ModuleID
                    Add grade: grade, ModuleID, Grade
                    Return to main menu: back
                    """);
            input = sc.nextLine().split(", ");
            switch(input[0]) {
                case "info":
                    printEnrollments(selectedStudent);
                    break;
                case "enroll":      // enroll student
                    if (input.length == 2) {
                        try {
                            Enrollment newEnrollment = manager.enrollStudent(studentID, Integer.parseInt(input[1]));
                            System.out.println(newEnrollment.student.toString() + " successfully enrolled in module " + newEnrollment.module.getName());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please only use numerical IDs!");
                        } catch (ModuleNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (DuplicateEnrollmentException e){
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, ModuleID'");
                    }
                    break;
                case "grade":
                    if (input.length == 3) {
                        try {
                            int moduleId = Integer.parseInt(input[1]);
                            double grade = Double.parseDouble(input[2]);
                            manager.setEnrollmentGrade(studentID, moduleId, grade);
                            if (grade == 5.0) {

                                System.out.println("Successfully added failing grade 5.0 for module "
                                        + manager.getModuleById(moduleId).toString());
                            } else {
                                System.out.println("Successfully added passing grade " + grade + " for module "
                                        + manager.getModuleById(moduleId).toString());
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input, please only use numerical IDs and grades!");
                        } catch (ModuleNotFoundException e) {
                            System.out.println(e.getMessage());
                        } catch (InvalidGradeException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid input, please use the exact format 'grade, ModuleID, Grade'");
                    }
                    break;
                case "back":
                    exitSubmenu = true;
                    break;
            }
        }
    }

    public void printStudents() {
        for (Student student : manager.getStudents().values()) {
            System.out.println("- " + student.toString());
        }
    }

    public void printEnrollments(Student student){
        List<Enrollment> enrollments = student.getEnrollmentList();

        if (enrollments.isEmpty()) {
            System.out.println("This student is not enrolled in any modules.");
            return;
        }
        for (Enrollment enrollment : enrollments){
            double grade = enrollment.getGrade();
            int id = enrollment.module.getId();
            if (grade == 0.0) {
                System.out.println("Module: "+ enrollment.module.getName() + " (ID: " + id +  ") - No grade yet");
            } else if (!enrollment.isPassed()){
                System.out.println("Module: "+ enrollment.module.getName() + " (ID: " + id +  ") - Failed with 5.0");
            } else {
                System.out.println("Module: "+ enrollment.module.getName() + " (ID: " + id +  ") - Passed with " + grade);
            }
        }
    }
}
