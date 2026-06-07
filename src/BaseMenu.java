import java.util.Scanner;

public abstract class BaseMenu {

    protected Manager manager;
    protected Scanner sc;

    BaseMenu(Manager manager){
        this.manager = manager;
        this.sc = new Scanner(System.in);
    }

    protected String[] readInput(){
        return sc.nextLine().split(", ");
    }

    public abstract void start();

}
