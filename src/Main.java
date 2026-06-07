import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        MainMenu mainMenu = new MainMenu(manager);
        FileHandler fileHandler = new FileHandler();

        fileHandler.loadStudents(manager);
        fileHandler.loadModules(manager);
        fileHandler.loadEnrollments(manager);
        mainMenu.start();
        fileHandler.saveStudents(manager.getStudents().values());
        fileHandler.saveModules(manager.getModules().values());
        fileHandler.saveEnrollments(manager.getStudents().values());
    }
}
