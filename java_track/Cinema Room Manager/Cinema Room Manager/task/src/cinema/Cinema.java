package cinema;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Scanner;

public class Cinema {

    public static int calcNumFrontHalfRows(int numRows) {
        if (numRows % 2 == 0) {
            // even number of rows
            return numRows / 2;
        } else {
            // odd number of rows
            return (numRows - 1) / 2;
        }
    }

    public static int calcBackHalfTicketPrice(int numRows, int seatsPerRow) {
        if (numRows * seatsPerRow <= 60){
            return 10;
        } else {
            return 8;
        }
    }

    public static double calcTotalPotentialRevenue(int numRows, int seatsPerRow, int frontHalfTicketPrice) {
        int totalSeats = numRows * seatsPerRow;
        double frontHalfPotentialRevenue = Cinema.calcNumFrontHalfRows(numRows) * seatsPerRow * frontHalfTicketPrice;
        double backHalfPotentialRevenue =
                (numRows - Cinema.calcNumFrontHalfRows(numRows))
                        * seatsPerRow * Cinema.calcBackHalfTicketPrice(numRows, seatsPerRow);
        return frontHalfPotentialRevenue + backHalfPotentialRevenue;
    }

    public static void printSeating(String[][] matrix, int numRows, int seatsPerRow) {
        // print seating
        StringBuilder header = new StringBuilder("  ");
        for (int seat = 1; seat <= seatsPerRow; seat++){
            header.append(seat + " ");
        }

        System.out.println("Cinema:");
        System.out.println(header);

        for (int row = 0; row < numRows; row++) {
            System.out.print( (row + 1) + " ");

            for (int seat = 0; seat < seatsPerRow; seat++) {
                System.out.print(matrix[row][seat] + " ");
            }
            System.out.println();
        }
    }

    public static String[][] initializeMatrix(int numRows, int seatsPerRow) {
        String[][] matrix = new String[numRows][seatsPerRow];

        for (int row = 0; row < numRows; row++) {
            for (int seat = 0; seat < seatsPerRow; seat++) {
                matrix[row][seat] = "S";
            }
        }
        return matrix;
    }

    public static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static int[] buyATicket(String[][] matrix, Scanner scanner, int numRows, int seatsPerRow, int frontHalfTicketPrice) {

        System.out.println();
        int ticketPrice;
        int rowNum;
        int seatNum;

        scanner.nextLine();

        while (true) {
            boolean validChoice = false;
            boolean validSeat = false;
            boolean seatTaken = true;

            System.out.println("Enter a row number: ");
            rowNum = scanner.nextInt();

            System.out.println("Enter a seat number in that row: ");
            seatNum = scanner.nextInt();

            // is seat choice valid?
            if (Cinema.isValidSeat(rowNum, seatNum, numRows, seatsPerRow)) {
                validSeat = true;
            } else {
                System.out.println();
                System.out.println("Wrong input!");
                System.out.println();
                validSeat = false;
                continue;
            }

            // is seat taken?
            if (Cinema.isSeatTaken(matrix, rowNum, seatNum)) {
                System.out.println();
                System.out.println("That ticket has already been purchased!");
                System.out.println();
                seatTaken = true;
                continue;
            } else {
                seatTaken = false;
            }

            if(validSeat && !seatTaken) {
                break;
            } else {
                seatTaken = true;
                validSeat = false;
            }

        }

        if (rowNum <= calcNumFrontHalfRows(numRows) ){
            // it is in the front, which is $10 always
            ticketPrice = frontHalfTicketPrice;
        } else {
            ticketPrice = calcBackHalfTicketPrice(numRows, seatsPerRow);
        }

        System.out.println();
        System.out.println("Ticket price: $" + ticketPrice);

        return new int[] {rowNum, seatNum, ticketPrice};

    }

    public static boolean isSeatTaken(String[][] matrix, int rowNum, int seatNum) {
        if (matrix[rowNum - 1][seatNum - 1] == "B") {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidSeat(int rowNum, int seatNum, int numRows, int seatsPerRow){

        if (rowNum <= 0 || rowNum > numRows || seatNum <= 0 || seatNum > seatsPerRow) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean setSeat(String[][] matrix, int[] chosenSeat) {
        int row = chosenSeat[0] - 1;
        int seat = chosenSeat[1] - 1;
        String desiredSeat = matrix[row][seat];

        if ("S".equals(desiredSeat)){
            matrix[row][seat] = "B";
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int numRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");

        int frontHalfTicketPrice =10;
        int seatsPerRow = scanner.nextInt();
        int totalSeatsAvailable = numRows * seatsPerRow;
        int seatsPurchased = 0;
        int totalRevenue = 0;
        double totalPotentialRevenue = calcTotalPotentialRevenue(numRows, seatsPerRow, frontHalfTicketPrice);

        System.out.println();
        String[][] matrix = Cinema.initializeMatrix(numRows, seatsPerRow);

        int userSelection = -1;
        while (userSelection != 0) {
            printMenu();
            switch (scanner.nextInt()) {
                case 1:
                    // show seats
                    System.out.println();
                    Cinema.printSeating(matrix, numRows, seatsPerRow);
                    System.out.println();
                    break;
                case 2:
                    // buy ticket
                    int[] chosenSeat = Cinema.buyATicket(matrix, scanner, numRows, seatsPerRow, frontHalfTicketPrice);
                    if (Cinema.setSeat(matrix, chosenSeat)) {
                        seatsPurchased++;
                        totalRevenue += chosenSeat[2];
                    } else {
                        System.out.println("That ticket has already been purchased!");
                    }
                    System.out.println();
                    break;
                case 3:
                    // stats
                    System.out.println();
                    System.out.println("Number of purchased tickets: " + seatsPurchased);
                    System.out.printf("Percentage: %.2f", (doubprile) (100 * seatsPurchased) / totalSeatsAvailable);
                    System.out.print("%");
                    System.out.println();
                    System.out.println("Current income: $" + totalRevenue);
                    System.out.println("Total income: $" + (int) totalPotentialRevenue);
                    System.out.println();
                    break;
                case 0:
                    // exit
                    userSelection = 0;
                    break;
                default:
                    // do nothing
                    break;
            }
            
        }
    }
}