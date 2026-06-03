import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        ConsoleMenu menu = new ConsoleMenu(manager);
        FileHandler fileHandler = new FileHandler();

        fileHandler.loadStudents(manager);
        menu.startMenu();
        fileHandler.saveStudents(manager.getStudents().values());
    }
}
