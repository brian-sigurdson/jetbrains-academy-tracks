package machine;

import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeMachine {
//    private int waterPerCup = 200;
//    private int milkPerCup = 50;
//    private int beansPerCup = 15;

    private int waterAvailable;
    private int milkAvailable;
    private int beansAvailable;
    private int moneyBalance;
    private int cupsAvailable;

    private String liquidUOM = "ml";
    private String beansUOM = "g";

    private int numCupsNeeded;

    private Scanner scanner;

    public CoffeeMachine() {
        moneyBalance = 550;
        waterAvailable = 400;
        milkAvailable = 540;
        beansAvailable = 120;
        cupsAvailable = 9;

        scanner = new Scanner(System.in);
    }

    public void displayCoffeeMachineState() {
        System.out.println("The coffee machine has: ");
        System.out.printf("%d %s of water%n", waterAvailable, liquidUOM);
        System.out.printf("%d %s of milk%n", milkAvailable, liquidUOM);
        System.out.printf("%d %s of coffee beans%n", beansAvailable, beansUOM);
        System.out.printf("%d disposable cups%n", cupsAvailable);
        System.out.printf("$%d of money%n", moneyBalance);
    }

    public String getUserSelection() {
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        return scanner.next();
    }

    public void updateWaterAvailable(int changeBy) {
        waterAvailable += changeBy;
    }

    public void updateBeansAvailable(int changeBy) {
        beansAvailable += changeBy;
    }

    public void updateMilkAvailable(int changeBy) {
        milkAvailable += changeBy;
    }

    public void updateCupsAvailable(int changeBy) {
        cupsAvailable += changeBy;
    }

    public void updateMoneyBalance(int changeBy) {
        moneyBalance += changeBy;
    }

    public void processUserFill() {
        System.out.println();
        System.out.println("Write how many ml of water you want to add:");
        updateWaterAvailable(scanner.nextInt());
        System.out.println("Write how many ml of milk you want to add:");
        updateMilkAvailable(scanner.nextInt());
        System.out.println("Write how many grams of coffee beans you want to add:");
        updateBeansAvailable(scanner.nextInt());
        System.out.println("Write how many disposable cups of coffee you want to add:");
        updateCupsAvailable(scanner.nextInt());
    }

    public void processUserTake() {
        System.out.printf("I gave you $%d%n", moneyBalance);
        updateMoneyBalance(-moneyBalance);
    }

    public void processUserBuy() {
        System.out.println();
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");

        CupOfCoffee theCup;

        switch (scanner.next()) {
            case "1":
                // espresso
                theCup = this.new CupOfCoffee(250, 16, 0, "espresso", 4);
                theCup.processUserRequest();
                break;
            case "2":
                // latte
                theCup = this.new CupOfCoffee(350, 20, 75, "latte", 7);
                theCup.processUserRequest();
                break;
            case "3":
                // cappuccino
                theCup = this.new CupOfCoffee(200, 12, 100, "cappuccino", 6);
                theCup.processUserRequest();
                break;
            case "back":
                return;
            default:
                break;
        }
    }

    private class CupOfCoffee {
        private int waterNeeded;
        private int beansNeeded;
        private int milkNeeded;
        private String coffeeType;
        private int salePrice;

        private void processUserRequest() {
            if (waterAvailable < waterNeeded) {
                System.out.println("Sorry, not enough water!");
            } else if(milkAvailable < milkNeeded) {
                System.out.println("Sorry, not enough milk!");
            } else if(beansAvailable < beansNeeded) {
                System.out.println("Sorry, not enough beans!");
            } else if(cupsAvailable <= 0) {
                System.out.println("Sorry, not enough cups!");
            } else {
                System.out.println("I have enough resources, making you a coffee!");
                updateWaterAvailable(-waterNeeded);
                updateMilkAvailable(-milkNeeded);
                updateBeansAvailable(-beansNeeded);
                updateMoneyBalance(salePrice);
                updateCupsAvailable(-1);
            }
        }

        private CupOfCoffee(int waterNeeded, int beansNeeded, int milkNeeded, String coffeeType, int salePrice) {
            this.waterNeeded = waterNeeded;
            this.beansNeeded = beansNeeded;
            this.milkNeeded = milkNeeded;
            this.coffeeType = coffeeType;
            this.salePrice = salePrice;
        }
    }


    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        boolean exitNow = false;

        while (!exitNow) {
            switch (coffeeMachine.getUserSelection()) {
                case "buy":
                    coffeeMachine.processUserBuy();
                    System.out.println();
                    break;
                case "fill":
                    coffeeMachine.processUserFill();
                    System.out.println();
                    break;
                case "take":
                    System.out.println();
                    coffeeMachine.processUserTake();
                    System.out.println();
                    break;
                case "remaining":
                    System.out.println();
                    coffeeMachine.displayCoffeeMachineState();
                    System.out.println();
                    break;
                case "exit":
                    exitNow = true;
                    break;
                default:
                    break;
            }
        }

    }
}
