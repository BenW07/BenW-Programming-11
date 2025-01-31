import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Sets up user input scanner

        Customer customer = new Customer("John Doe", 1, 200.0, 1250.0);
        // Creates a customer object with an initial balance

        customer.deposit(400.0, new Date(122, 10, 4), Customer.CHECKING);
        customer.deposit(500.0, new Date(122, 7, 16, 10, 52, 17), Customer.SAVING);
        // Deposits made in the past

        System.out.println("Welcome, " + customer.getName());
        System.out.println("Account Number: " + customer.getAccountNumber());
        // Prints out welcome message and account number

        System.out.println("Initial Balances:");
        System.out.println("Checking: $" + customer.getCheckBalance());
        System.out.println("Saving: $" + customer.getSavingBalance());
        System.out.println();
        // Displays initial balances

        int userChoice = -1;
        // Initializes with an invalid value
        while (true) {
            System.out.println("1. Display Balances");
            System.out.println("2. Display Transactions");
            System.out.println("3. Deposit Money into Checking");
            System.out.println("4. Deposit Money into Saving");
            System.out.println("5. Withdraw Money from Checking");
            System.out.println("6. Withdraw Money from Saving");
            System.out.println("7. Exit");
            System.out.println();

            while (true) {
                if (scanner.hasNextInt()) {
                    userChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (userChoice >= 1 && userChoice <= 7) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next();
                }
            }

            switch (userChoice) {
                case 1:
                    System.out.println("Current Balances: ");
                    System.out.println("Checking: $" + customer.getCheckBalance());
                    System.out.println("Saving: $" + customer.getSavingBalance());
                    break;
                case 2:
                    System.out.println("Deposits:");
                    customer.displayDeposits();
                    System.out.println("Withdrawals:");
                    customer.displayWithdraws();
                    break;
                case 3:
                    System.out.println("Deposit amount: $");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                    double checkingDepositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Clear the newline character
                    if (checkingDepositAmount <= 0) {
                        System.out.println("Deposit amount must be greater than zero. Please enter a valid number.");
                    } else {
                        customer.deposit(checkingDepositAmount, new Date(), Customer.CHECKING);
                        System.out.println("Deposit successful. New balance is: $" + customer.getCheckBalance());
                    }
                    break;
                case 4:
                    System.out.println("Deposit amount: $");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                    double savingDepositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Clear the newline character
                    if (savingDepositAmount <= 0) {
                        System.out.println("Deposit amount must be greater than zero. Please enter a valid number.");
                    } else {
                        customer.deposit(savingDepositAmount, new Date(), Customer.SAVING);
                        System.out.println("Deposit successful. New balance is: $" + customer.getSavingBalance());
                    }
                    break;
                case 5:
                    System.out.println("Withdrawal amount: $");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                    double checkingWithdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Clear the newline character
                    if (checkingWithdrawAmount <= 0) {
                        System.out.println("Withdrawal amount must be greater than zero. Please enter a valid number.");
                    } else {
                        double withdrawnAmount = customer.withdraw(checkingWithdrawAmount, new Date(), Customer.CHECKING);
                        if (withdrawnAmount > 0) {
                            System.out.println("Withdrawal successful. New balance is: $" + customer.getCheckBalance());
                        } else {
                            System.out.println("Withdrawal failed. Please check your balance and overdraft limit.");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Withdrawal amount: $");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next();
                    }
                    double savingWithdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Clear the newline character
                    if (savingWithdrawAmount <= 0) {
                        System.out.println("Withdrawal amount must be greater than zero. Please enter a valid number.");
                    } else {
                        double withdrawnAmount = customer.withdraw(savingWithdrawAmount, new Date(), Customer.SAVING);
                        if (withdrawnAmount > 0) {
                            System.out.println("Withdrawal successful. New balance is: $" + customer.getSavingBalance());
                        } else {
                            System.out.println("Withdrawal failed. Please check your balance and overdraft limit.");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        //Requires: nothing
        //Modifies: System.out and Customer object
        //Effects: Provides interface for interacting with the banking system. Allows for deposits, withdrawals, and balance/transaction displays
    }
}