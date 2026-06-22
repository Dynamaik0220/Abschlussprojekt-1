package abschlussprojekt;

public class MainMenu extends BaseMenu {

    private final StudentMenu studentMenu;
    private final ModuleMenu moduleMenu;

    MainMenu(Manager manager){
        super(manager);
        this.studentMenu = new StudentMenu(manager);
        this.moduleMenu = new ModuleMenu(manager);
    }
    @Override
    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("""
                    
                    ----Main Menu----
                    
                    Choose a submenu:
                    Students:   (s)tudents
                    Modules:    (m)odules
                    Exit:       (e)xit
                    """);
            String[] input = readInput();
            switch (input[0].toLowerCase()) {
                case "s":
                case "students":
                    studentMenu.start();
                    break;

                case "m":
                case "modules":
                    moduleMenu.start();
                    break;

                case "e":
                case "exit":
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown command, please use one of the displayed commands");
            }

        }
    }
}
