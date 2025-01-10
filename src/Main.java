import Models.Menu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Menu menu = new Menu();

        do {
            menu.firstMenu();
        } while (menu.isSeguir());

    }
}

