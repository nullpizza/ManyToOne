package pac;

import java.util.Scanner;

public class UI {
    private static final Scanner sc = new Scanner(System.in);
    private static int userUiChoice = -1;
    private static final UI ui = new UI();


    public void loop() {
        ResidentManager rmg = new ResidentManager();
        UI ui = new UI();
        while (userUiChoice != 0) {
            ui.mainMenu();
            switch (userUiChoice) {
                case 1:
                    ui.personManager();
                    break;
                case 2:
                    ui.addressManager();
                    break;
                case 3:
                    rmg.idInput();
                    break;
            }
        }
    }


    private void mainMenu() {
        System.out.println("+--------------------------------------+");
        System.out.println("|          Residence Manager           |");
        System.out.println("+--------------------------------------+");
        System.out.println("| 1. Person Manager                    |");
        System.out.println("+ 2. Address Manager                   +");
        System.out.println("| 3. Modification                      |");
        System.out.println("+ 0. Exit                              +");
        System.out.println("|--------------------------------------|");
        System.out.print("\nENTER YOUR CHOICE: ");
        userUiChoice = sc.nextInt();
        System.out.println("\n");
    }

    private void personManager() {
        ResidentManager residentManager = new ResidentManager();
        System.out.println("+---------------------------------+");
        System.out.println("|          Person Manager         |");
        System.out.println("+---------------------------------+");
        System.out.println("| 1. Create Person                |");
        System.out.println("| 0. Return to main menu          |");
        System.out.println("+---------------------------------+");
        System.out.print("\nENTER YOUR CHOICE: ");
        int personChoice = sc.nextInt();
        System.out.println("\n");

        if (personChoice == 1) {
            sc.nextLine();
            residentManager.createPerson();
        } else if (userUiChoice == 0) {
            ui.mainMenu();
        } else {
            System.out.println("\ninvalid choice!");
        }
    }


    private void addressManager() {
        ResidentManager residentManager = new ResidentManager();
        System.out.println("|-----------------------------------------------|");
        System.out.println("+           [Address Manager]                   +");
        System.out.println("|-----------------------------------------------+");
        System.out.println("+ 1. Create Address                             |");
        System.out.println("| 0. return to main menu                        +");
        System.out.println("+-----------------------------------------------|");
        System.out.print("\nENTER YOUR CHOICE: ");
        int addressChoice = sc.nextInt();

        if (addressChoice == 1) {
            sc.nextLine();
            System.out.print("STREET NAME: ");
            String streetName = sc.nextLine();

            System.out.print("\nPost Code: ");
            int postCode = sc.nextInt();
            sc.nextLine();

            System.out.print("\nCITY: ");
            String city = sc.nextLine();

            residentManager.createAddress(streetName, postCode, city);
        } else if (addressChoice == 0) {
            ui.mainMenu();
        }
    }
}
