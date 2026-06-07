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
                View and manage Student: manage, id
                Return to main menu: back
                """);
            String[] input = readInput();
            switch (input[0]) {
                case "add":
                    handleAddCommand(input);
                    break;

                case "all":
                    printStudents();
                    break;

                case "manage":
                    handleManageCommand(input);
                    break;

                case "back":
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    public void printStudents() {
        for (Student student : manager.getStudents().values()) {
            System.out.println("- " + student.toString());
        }
    }

    private void handleAddCommand(String[] input) {
        if (input.length == 2) {
            Student newStudent = manager.addStudent(input[1]);
            System.out.println("Student '" + newStudent.toString() +  " added.");
        } else {
            System.out.println("Invalid input, please use the exact format 'add, name'");
        }
    }

    private void handleManageCommand(String[] input) {
        try {
            if (input.length == 2) {
                startManageStudentMenu(Integer.parseInt(input[1]));
            } else {
                System.out.println("Invalid input, please use the exact format 'manage, id'");
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input, please only use numerical IDs!");
        } catch (StudentNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void startManageStudentMenu(int studentID) {
        boolean exitSubmenu = false;
        Student selectedStudent = manager.getStudentById(studentID);

        while (!exitSubmenu) {
            System.out.println("\n----Managing " + selectedStudent.toString() + "----");
            System.out.println("""   
                    
                    Show all information: info
                    Enroll in module: enroll, ModuleID
                    Add grade: grade, ModuleID, Grade
                    Delete Student: delete
                    Return to main menu: back
                    """);
            String[] input = sc.nextLine().split(", ");
            switch(input[0]) {
                case "info":
                    showInfo(selectedStudent);
                    break;

                case "enroll":      // enroll student
                    handleEnrollCommand(studentID, input);
                    break;

                case "grade":
                    handleGradeCommand(studentID, input);
                    break;

                case "delete":
                    if (confirmDeletion(studentID, selectedStudent)) return;
                    break;

                case "back":
                    exitSubmenu = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    private boolean confirmDeletion(int studentID, Student selectedStudent) {
        System.out.println("Do you really want to delete student " + selectedStudent.toString() +
                "? This cannot be reversed. y/n");
        if (readInput()[0].equals("y")){
            try {
                manager.deleteStudent(studentID);
                System.out.println("Successfully deleted student " + selectedStudent.toString());
                return true;
            } catch (StudentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion aborted");
        }
        return false;
    }

    private void handleGradeCommand(int studentID, String[] input) {
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
    }

    private void handleEnrollCommand(int studentID, String[] input) {
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
            System.out.println("Invalid input, please use the exact format 'manage, ModuleID'");
        }
    }

    private void showInfo(Student selectedStudent) {
        printEnrollments(selectedStudent);
        if (selectedStudent.getAverageGrade() == 0.0){
            System.out.println("No grades entered yet");
        }
        System.out.println("Average grade: " + selectedStudent.getAverageGrade());
    }

    public void printEnrollments(Student student){
        List<Enrollment> enrollments = student.getEnrollments();

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
