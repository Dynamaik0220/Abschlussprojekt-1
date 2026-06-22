package abschlussprojekt;

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
                
                Show all students by id:            all
                                  by name:          all, name
                                  by average grade: all, grade
                Add student:                        add, 'name'
                View and manage student:            (m)anage, 'id'
                Return to main menu:                (b)ack
                """);
            String[] input = readInput();
            switch (input[0].toLowerCase()) {
                case "add":
                    handleAddCommand(input);
                    break;

                case "all":
                    handleAllCommand(input);
                    break;

                case "m":
                case "manage":
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
                    for (Student student : manager.getStudentsSortedByName()) {
                        System.out.println("- " + student.toStringLong());
                    }
                    break;

                case "grade":
                    for (Student student : manager.getStudentsSortedByGrade()) {
                        System.out.println("- " + student.toStringLong());
                    }
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
                    break;
            }
        } else {
            for (Student student : manager.getStudents().values()) {
                System.out.println("- " + student.toStringLong());
            }
        }
    }

    private void handleAddCommand(String[] input) {
        if (input.length == 2) {
            Student newStudent = manager.addStudent(input[1]);
            System.out.println("Student " + newStudent.toString() +  " added.");
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
        Student selectedStudent = manager.getStudentByID(studentID);

        while (!exitSubmenu) {
            System.out.println("\n----Managing " + selectedStudent.toString() + "----");
            System.out.println("""   
                    
                    Show all information:   (i)nfo
                    Enroll in module:       (e)nroll, 'ModuleID'
                    Uneroll from module:    (u)nenroll, 'ModuleID'
                    Add grade:              (g)rade, 'ModuleID', 'Grade'
                    Delete Student:         delete
                    Return to main menu:    (b)ack
                    """);
            String[] input = readInput();
            switch(input[0]) {
                case "i":
                case "info":
                    showInfo(selectedStudent);
                    break;

                case "e":
                case "enroll":      // enroll student
                    handleEnrollCommand(studentID, input);
                    break;

                case "u":
                case "unenroll":
                    handleUnenrollCommand(studentID, input);
                    break;

                case "g":
                case "grade":
                    handleGradeCommand(studentID, input);
                    break;

                case "delete":
                    if (confirmDeletion(studentID, selectedStudent)) return;
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
                int moduleID = Integer.parseInt(input[1]);
                double grade = Double.parseDouble(input[2]);
                if (manager.hasGrade(studentID, moduleID)){
                    System.out.print("A grade has already been entered for this module. Are you sure you want to overwrite it? (y/n): ");
                    String confirmation = readInput()[0];
                    if (!confirmation.equals("y")) {
                        System.out.println("Grade overwrite cancelled.");
                        return;
                    }
                }
                manager.setEnrollmentGrade(studentID, moduleID, grade);
                if (grade == 5.0) {
                    System.out.println("Successfully added failing grade 5.0 for module "
                            + manager.getModuleByID(moduleID).toString());
                } else {
                    System.out.println("Successfully added passing grade " + grade + " for module "
                            + manager.getModuleByID(moduleID).toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs and grades!");
            } catch (ModuleNotFoundException | InvalidGradeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'grade, ModuleID, Grade'");
        }
    }

    private void handleEnrollCommand(int studentID, String[] input) {
        if (input.length == 2) {
            try {
                Enrollment newEnrollment = manager.enrollStudent(studentID, Integer.parseInt(input[1]));
                System.out.println(newEnrollment.getStudent().toString() + " successfully enrolled in module " + newEnrollment.getModule().toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs!");
            } catch (ModuleNotFoundException | DuplicateEnrollmentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'enroll, ModuleID'");
        }
    }

    private void handleUnenrollCommand(int studentID, String[] input) {
        if (input.length == 2) {
            try {
                int moduleID = Integer.parseInt(input[1]);

                if (manager.hasGrade(studentID, moduleID)){
                    System.out.print("A grade has already been entered for this student. Are you sure you want to unenroll them? (y/n): ");
                    String confirmation = readInput()[0];
                    if (!confirmation.equals("y")) {
                        System.out.println("Unenrollment cancelled.");
                        return;
                    }
                }
                manager.unenrollStudent(studentID, moduleID);
                System.out.println("Student successfully unenrolled from module " + manager.getModuleByID(moduleID).toString());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please only use numerical IDs!");
            } catch (ModuleNotFoundException | StudentNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid input, please use the exact format 'unenroll, ModuleID'");
        }
    }

    private void showInfo(Student selectedStudent) {
        System.out.println("Information regarding student " + selectedStudent.toString() + ":");
        List<Enrollment> enrollments = selectedStudent.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("This student is not enrolled in any modules.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            Double grade = enrollment.getGrade();
            String moduleName = enrollment.getModule().toString();
            if (grade == null) {
                System.out.println("- Module: " + moduleName + " - No grade yet");
            } else if (!enrollment.isPassed()) {
                System.out.println("- Module: " + moduleName + " - Failed with 5.0");
            } else {
                System.out.println("- Module: " + moduleName + " - Passed with " + grade);
            }
        }
        if (selectedStudent.getAverageGrade() == null) {
            System.out.println("No grades entered yet");
        } else {
            System.out.printf("GPA: %.2f\n", selectedStudent.getAverageGrade());
        }
    }
}
