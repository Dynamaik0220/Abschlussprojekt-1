import java.util.List;

public class ModuleMenu extends BaseMenu {

    public ModuleMenu(Manager manager) {
        super(manager);
    }

    @Override
    public void start(){
        boolean exit = false;
        while (!exit) {
            System.out.println("""
                ----Module Submenu----
                
                Show all modules: all
                Add module: add, Name
                View and manage module: manage, id
                Return to main menu: back
                """);
            String[] input = readInput();
            switch (input[0]) {
                case "add":                          // add Module
                    handleAddCommand(input);
                    break;

                case "all":                          // show all Modules
                    printModules();
                    break;

                case "manage":                          // enroll student
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

    private void handleManageCommand(String[] input) {
        try {
            if (input.length == 2) {
                startManageModuleMenu(input, Integer.parseInt(input[1]));
            } else {
                System.out.println("Invalid input, please use the exact format 'manage, id'");
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid input, please only use numerical IDs!");
        } catch (ModuleNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddCommand(String[] input) {
        if (input.length == 2) {
            Module newModule = manager.addModule(input[1]);
            System.out.println("Module '" + newModule.getName() + "' (ID: " + newModule.getId() + ") added.");
        } else {
            System.out.println("Invalid input, please use the exact format 'as, name'");
        }
        return;
    }

    public void startManageModuleMenu(String[] input, int moduleID) {
        boolean exitSubmenu = false;
        Module selectedModule = manager.getModuleById(moduleID);

        while (!exitSubmenu) {
            System.out.println("----Managing " + selectedModule.getName() + "----");
            System.out.println("""   
                    Show all information: info
                    Enroll student in Module: enroll, StudentID
                    Add grade: grade, StudentID, Grade
                    Return to main menu: back
                    """);
            input = sc.nextLine().split(", ");
            switch(input[0]) {
                case "info":
                    showInfo(selectedModule);
                    break;

                case "enroll":      // enroll student
                    handleEnrollCommand(input, moduleID);
                    break;

                case "grade":
                    handleGradeCommand(moduleID, input);
                    break;

                case "delete":
                    if (confirmDeletion(moduleID, selectedModule)) return;
                    break;

                case "back":
                    exitSubmenu = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    private boolean confirmDeletion(int moduleID, Module selectedModule) {
        System.out.println("Do you really want to delete Module " + selectedModule.toString() +
                "? This cannot be reversed. y/n");
        if (readInput()[0].equals("y")){
            try {
                manager.deleteModule(moduleID);
                System.out.println("Successfully deleted module " + selectedModule.toString());
                return true;
            } catch (ModuleNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion aborted");
        }
        return false;
    }

    private void handleEnrollCommand(String[] input, int moduleID) {
        if (input.length == 2) {
            try {
                Enrollment newEnrollment = manager.enrollStudent(Integer.parseInt(input[1]), moduleID);
                System.out.println(newEnrollment.getStudent().toString() + " successfully enrolled in module " + newEnrollment.getModule().getName());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs!");
            } catch (StudentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DuplicateEnrollmentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'enroll, studentID'");
        }
    }

    private void showInfo(Module selectedModule) {
        printEnrollments(selectedModule);
        if (selectedModule.getAverageGrade() == 0.0){
            System.out.println("No grades entered yet");
        }
        System.out.println("Average grade: " + selectedModule.getAverageGrade());
    }

    public void printEnrollments(Module module){
        List<Enrollment> enrollments = module.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No students are enrolled in this module");
            return;
        }
        for (Enrollment enrollment : enrollments){
            double grade = enrollment.getGrade();
            String studentName = enrollment.getStudent().toString();
            if (grade == 0.0) {
                System.out.println("Student: "+ studentName +  " - No grade yet");
            } else if (!enrollment.isPassed()){
                System.out.println("Student: "+ studentName + " - Failed with 5.0");
            } else {
                System.out.println("Student: "+ studentName + " - Passed with " + grade);
            }
        }
    }


    private void handleGradeCommand(int moduleID, String[] input) {
        if (input.length == 3) {
            try {
                int studentID = Integer.parseInt(input[1]);
                double grade = Double.parseDouble(input[2]);
                manager.setEnrollmentGrade(studentID, moduleID, grade);
                if (grade == 5.0) {

                    System.out.println("Successfully added failing grade 5.0 for Student "
                            + manager.getStudentById(studentID).toString());
                } else {
                    System.out.println("Successfully added passing grade " + grade + " for Student "
                            + manager.getStudentById(studentID).toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs and grades!");
            } catch (StudentNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InvalidGradeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'grade, StudentID, Grade'");
        }
    }

    public void printModules () {
        for (Module module : manager.getModules().values()) {
            System.out.println("- " + module.getName() + " (ID: " + module.getId() + ")");
        }
    }
}
