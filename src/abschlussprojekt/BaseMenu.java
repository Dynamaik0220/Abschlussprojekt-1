package abschlussprojekt;

import java.util.Scanner;

public abstract class BaseMenu {

    protected Manager manager;
    protected static final Scanner sc = new Scanner(System.in);

    BaseMenu(Manager manager){
        this.manager = manager;
    }

    protected String[] readInput(){
        return sc.nextLine().split(", ");
    }

    public abstract void start();

}
