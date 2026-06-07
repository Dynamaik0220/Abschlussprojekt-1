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
                View and edit module: edit, id
                Return to main menu: back
                """);
            String[] input = readInput();
            switch (input[0]) {
                case "add":                          // add Module
                    if (input.length == 2) {
                        Module newModule = manager.addModule(input[1]);
                        System.out.println("Module '" + newModule.getName() + "' (ID: " + newModule.getId() + ") added.");
                    } else {
                        System.out.println("Invalid input, please use the exact format 'as, name'");
                    }
                    break;

                case "all":                          // show all Modules
                    printModules();
                    break;

                case "edit":                          // enroll student
                    if (input.length == 2) {
                        startEditModuleMenu(input, Integer.parseInt(input[1]));
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, id'");
                    }
                    break;

                case "back":
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }

    public void startEditModuleMenu(String[] input, int moduleID) {
        boolean exitSubmenu = false;

        while (!exitSubmenu) {
            System.out.println("----Editing " + manager.getModules().get(moduleID).getName() + "----");
            System.out.println("""   
                    Enroll student in Module: enroll, StudentID
                    Return to main menu: back
                    """);
            input = sc.nextLine().split(", ");
            switch(input[0]) {
                case "enroll":      // enroll student
                    if (input.length == 2) {
                        try {
                            Enrollment newEnrollment = manager.enrollStudent(Integer.parseInt(input[1]), moduleID);
                            System.out.println(newEnrollment.student.toString() + " successfully enrolled in module " + newEnrollment.module.getName());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Input error, please only use numerical IDs!");
                        } catch (Exception e) {
                            System.out.println("Invalid input: " + e);
                        }
                    } else {
                        System.out.println("Invalid input, please use the exact format 'edit, ModuleID'");
                    }
                    break;

                case "back":
                    exitSubmenu = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }
        }
    }
    public void printModules () {
        for (Module module : manager.getModules().values()) {
            System.out.println("- " + module.getName() + " (ID: " + module.getId() + ")");
        }
    }
}
