import java.io.File;
import java.util.Scanner;

public class ConsoleMenu {
    private Scanner sc;
    private Manager manager;

    ConsoleMenu(Manager manager) {
        this.manager = manager;
        this.sc = new Scanner(System.in);
    }

    public void startMenu() {
        //noinspection InfiniteLoopStatement
        boolean stop = false;
        while (!stop) {
            System.out.println("""
                    Choose a submenu:
                    Students: s
                    Modules: m
                    """);
            String[] input = sc.nextLine().split(", ");
            switch (input[0]){
                case "s":
                    openStudentMenu(input);
                    break;
                case "m":
            }

            switch (input[0]) {
                case "am":                          // add module
                    if (input.length == 2) {
                        Module newModule = manager.addModule(input[1]);
                        System.out.println("Module '" + newModule.getName() + "' (ID: " + newModule.getId() + ") added");
                        break;
                    } else {
                        System.out.println("Invalid input, please use the exact format 'am, name'");
                    }
                case "sm":
                    printModules();
                    break;
                case "exit":
                    stop = true;
                    break;
                default:
                    System.out.println("Invalid command, please try again");
                    break;
            }
        }
    }
    public void openStudentMenu(String[] input) {
        boolean exitSubmenu = false;
        while (!exitSubmenu) {
            System.out.println("""
                ----Student Submenu----
                
                Show all students: all
                Add student: add, Name
                View and edit Student: edit, id
                Return to main menu: back
                """);
            input = sc.nextLine().split(", ");
            switch (input[0]) {
                case "add":                          // add student
                    if (input.length == 2) {
                        Student newStudent = manager.addStudent(input[1]);
                        System.out.println("Student '" + newStudent.getName() + "' (ID: " + newStudent.getId() + ") added.");
                    } else {
                        System.out.println("Invalid input, please use the exact format 'as, name'");
                    }
                    break;
                case "all":                          // show all students
                    printStudents();
                    break;
                case "edit":                          // enroll student
                    if (input.length == 2) {
                        openEditStudentMenu(input, Integer.parseInt(input[1]));
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, id'");
                    }
                    break;
                case "back":
                    exitSubmenu = true;
                    break;
            }
        }
    }

    public void openEditStudentMenu(String[] input, int studentID) {
        boolean exitSubmenu = false;

        while (!exitSubmenu) {
            System.out.println("----Editing " + manager.getStudents().get(studentID).getName() + "----");
            System.out.println("""   
                    Enroll in Module: enroll, ModuleID
                    Return to main menu: back
                    """);
            input = sc.nextLine().split(", ");
            switch(input[0]) {
                case "enroll":      // enroll student
                    if (input.length == 2) {
                        try {
                            Enrollment newEnrollment = manager.enrollStudent(studentID, Integer.parseInt(input[1]));
                            System.out.println(newEnrollment.student.getName() + " successfully enrolled in module " + newEnrollment.module.getName());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Input error, please only use numerical IDs!");
                        }
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, ModuleID'");
                    }
                    break;
                case "break":
                    exitSubmenu = true;
                    break;
            }
        }
    }
    public void printStudents() {
        for (Student student : manager.getStudents().values()) {
            System.out.println("- " + student.getName() + " (ID: " + student.getId() + ")");
        }
    }

    public void printModules () {
        for (Module module : manager.getModules().values()) {
            System.out.println("- " + module.getName() + " (ID: " + module.getId() + ")");
        }
    }
}
