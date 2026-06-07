public class MainMenu extends BaseMenu {

    private StudentMenu studentMenu;
    private ModuleMenu moduleMenu;

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
                    Choose a submenu:
                    Students: s
                    Modules: m
                    Exit: e
                    """);
            String[] input = readInput();
            switch (input[0]) {
                case "s":
                    studentMenu.start();
                    break;
                case "m":
                    moduleMenu.start();
                    break;
                case "e":
                    exit = true;
                    break;
            }

        }
    }
}
