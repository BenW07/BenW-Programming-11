import org.junit.Ignore;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Place Holder", 123456, 200.0, 1250.0);
        //Creates a customer object with an initial balance

        System.out.println("Initial Balances:");
        System.out.println("Checking: $" + customer.getCheckBalance());
        System.out.println("Saving: $" + customer.getSavingBalance());
        System.out.println();
        //Displays initial balances

        System.out.println("Depositing money:");
        customer.deposit(400.0, new Date(122, 10, 4), Customer.CHECKING);
        customer.deposit(500.0, new Date(122, 7, 16, 10, 52, 17), Customer.SAVING);
        customer.displayDeposits();
            System.out.println("Current Checking balance: $" + customer.getCheckBalance());
            System.out.println("Current Saving balance: $" + customer.getSavingBalance());
            //displays updated CHECKING and SAVING balance
        System.out.println();
        //Deposit money into SAVING and CHECKING accounts

        System.out.println("Withdrawing money:");
        customer.withdraw(200.0, new Date(), Customer.CHECKING);
        customer.withdraw(400.0, new Date(), Customer.SAVING);
        customer.displayWithdraws();
            System.out.println("Checking balance after withdrawal: $" + customer.getCheckBalance());
            System.out.println("Saving balance after withdrawal: $" + customer.getSavingBalance());
            //displays updated CHECKING and SAVING balance
        System.out.println();
        //Withdraw money from CHECKING and SAVING accounts

        System.out.println("Final Balances");
        System.out.println("Checking: $" + customer.getCheckBalance());
        System.out.println("Savings: $" + customer.getSavingBalance());
        //displays final balance of CHECKING and SAVING

    }
}
