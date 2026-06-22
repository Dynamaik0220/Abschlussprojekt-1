package abschlussprojekt;

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
                
                Show all modules by id:     all
                                 by name:   all, name
                                 by grade:  all, grade
                Add module:                 add, 'Name'
                View and manage module:     (m)anage, 'id'
                Return to main menu:        (b)ack
                """);
            String[] input = readInput();
            switch (input[0].toLowerCase()) {
                case "add":                          // add Module
                    handleAddCommand(input);
                    break;

                case "all":                          // show all Modules
                    handleAllCommand(input);
                    break;

                case "m":
                case "manage":                          // enroll student
                    handleManageCommand(input);
                    break;

                case "b":
                case "back":
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    private void handleAllCommand(String[] input) {
        if (input.length == 2) {
            switch (input[1].toLowerCase()) {
                case "name":
                    for (Module module : manager.getModulesSortedByName()) {
                        System.out.println("- " + module.toStringLong());
                    }
                    break;

                case "grade":
                    for (Module module : manager.getModulesSortedByGrade()) {
                        System.out.println("- " + module.toStringLong());
                    }
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
                    break;
            }
        } else {
            for (Module module : manager.getModules().values()) {
                System.out.println("- " + module.toStringLong());
            }
        }
    }

    private void handleManageCommand(String[] input) {
        try {
            if (input.length == 2) {
                startManageModuleMenu(Integer.parseInt(input[1]));
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
            System.out.println("Module '" + newModule.getName() + "' (ID: " + newModule.getID() + ") added.");
        } else {
            System.out.println("Invalid input, please use the exact format 'add, name'");
        }
    }

    public void startManageModuleMenu(int moduleID) {
        boolean exitSubmenu = false;
        Module selectedModule = manager.getModuleByID(moduleID);

        while (!exitSubmenu) {
            System.out.println("\n----Managing " + selectedModule.toString() + "----");
            System.out.println("""   
                    
                    Show all information:           (i)nfo
                    Enroll student in module:       (e)nroll, 'StudentID'
                    Unenroll student from module:   (u)nenroll, 'StudentID'
                    Add grade:                      (g)rade, 'StudentID', 'Grade'
                    Delete module:                  delete
                    Return to main menu:            (b)ack
                    """);
            String[] input = readInput();
            switch(input[0]) {
                case "i":
                case "info":
                    showInfo(selectedModule);
                    break;

                case "e":
                case "enroll":
                    handleEnrollCommand(input, moduleID);
                    break;

                case "u":
                case "unenroll":
                    handleUnenrollCommand(input, moduleID);
                    break;

                case "g":
                case "grade":
                    handleGradeCommand(moduleID, input);
                    break;

                case "delete":
                    if (confirmDeletion(moduleID, selectedModule)) return;
                    break;

                case "b":
                case "back":
                    exitSubmenu = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    private boolean confirmDeletion(int moduleID, Module selectedModule) {
        System.out.println("Do you really want to delete module " + selectedModule.toString() +
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
                System.out.println(newEnrollment.getStudent().toString() + " successfully enrolled in module " + newEnrollment.getModule().toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs!");
            } catch (StudentNotFoundException | DuplicateEnrollmentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'enroll, studentID'");
        }
    }

    private void showInfo(Module selectedModule) {
        System.out.println("Information regarding module " + selectedModule.toString() + ":");
        List<Enrollment> enrollments = selectedModule.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No students are enrolled in this module");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            Double grade = enrollment.getGrade();
            String studentName = enrollment.getStudent().toString();
            if (grade == null) {
                System.out.println("- Student: " + studentName + " - No grade yet");
            } else if (!enrollment.isPassed()) {
                System.out.println("- Student: " + studentName + " - Failed with 5.0");
            } else {
                System.out.println("- Student: " + studentName + " - Passed with " + grade);
            }
        }

        if (selectedModule.getAverageGrade() == null) {
            System.out.println("No grades entered yet");
        } else {
            System.out.printf("Average grade: %.2f\n" , selectedModule.getAverageGrade());
        }
    }

    private void handleGradeCommand(int moduleID, String[] input) {
        if (input.length == 3) {
            try {
                int studentID = Integer.parseInt(input[1]);
                double grade = Double.parseDouble(input[2]);
                if (manager.hasGrade(studentID, moduleID)){
                    System.out.print("A grade has already been entered for this student. Are you sure you want to overwrite it? (y/n): ");
                    String confirmation = readInput()[0];
                    if (!confirmation.equals("y")) {
                        System.out.println("Grade overwrite cancelled.");
                        return;
                    }
                }
                manager.setEnrollmentGrade(studentID, moduleID, grade);
                if (grade == 5.0) {
                    System.out.println("Successfully added failing grade 5.0 for student "
                            + manager.getStudentByID(studentID).toString());
                } else {
                    System.out.println("Successfully added passing grade " + grade + " for student "
                            + manager.getStudentByID(studentID).toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs and grades!");
            } catch (StudentNotFoundException | InvalidGradeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'grade, StudentID, Grade'");
        }
    }

    private void handleUnenrollCommand(String[] input, int moduleID) {
        if (input.length == 2) {
            try {
                int studentID = Integer.parseInt(input[1]);

                if (manager.hasGrade(studentID, moduleID)){
                    System.out.print("A grade has already been entered for this student. Are you sure you want to unenroll them? (y/n): ");
                    String confirmation = readInput()[0];
                    if (!confirmation.equals("y")) {
                        System.out.println("Unenrollment cancelled.");
                        return;
                    }
                }
                manager.unenrollStudent(studentID, moduleID);
                System.out.println("Student "+ manager.getStudentByID(studentID).toString() + " successfully unenrolled from module.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs!");
            } catch (StudentNotFoundException | ModuleNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'unenroll, studentID'");
        }
    }
}
