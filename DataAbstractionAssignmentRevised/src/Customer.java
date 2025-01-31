import javax.security.sasl.SaslClient;
import java.util.ArrayList;
import java.util.Date;

public class Customer {
    private int accountNumber;
    private ArrayList<Deposit> deposits;
    private ArrayList<Withdraw> withdraws;
    private double checkBalance;
    private double savingBalance;
    private double savingRate;
    private String name;
    public static final String CHECKING = "Checking";
    public static final String SAVING = "Saving";
    private final int overdraftLimit = -100;
    private final double overdraftFee = 100.0;
    //overdraft limit of both accounts

    Customer(){
        this.name = "Customer1";
        this.accountNumber = 0;
        this.deposits = new ArrayList<>();
        this.withdraws = new ArrayList<>();
        this.checkBalance = 0.0;
        this.savingBalance = 0.0;
        //Default constructor with default values for name, account number, etc.
    }
    Customer(String name, int accountNumber, double checkDeposit, double savingDeposit){
        this.name = name;
        this.accountNumber = accountNumber;
        this.deposits = new ArrayList<>();
        this.withdraws = new ArrayList<>();
        this.checkBalance = checkDeposit;
        this.savingBalance = savingDeposit;
        this.savingRate = 0.01;
        //Constructor with specific values. Sets initial values
    }
    //Requires: checkDeposit >= 0, savingDeposit >= 0
    //Modifies: this
    //Effect: creates a new customer object with the specified name, account number, and initial balance
    public double getCheckBalance()
        {return checkBalance;}
    //allows other classes to get the CHECKING account balance number

    public double getSavingBalance()
        {return savingBalance;}
    //allows other classes to get the SAVING account balance number

    public int getAccountNumber()
        {return accountNumber;}
    //allows other classes to get the account number

    public String getName()
        {return name;}
    //allows other classes to get the customer name

    public double deposit(double amt, Date date, String account){
        if (amt <= 0) {
            System.out.println("Deposit amount must be more than zero.");
            return amt;
            //if amount of money trying to be deposited is 0 or less it will return an error message and ignore the given value
        }

        if (account.equals(CHECKING)) {
            double newBalance = checkBalance + amt;
            //calculates the new balance
            checkBalance = newBalance;
            //updates the current balance
            deposits.add(new Deposit(amt, date, CHECKING, newBalance));
            //adds deposit to list
        }
        else if (account.equals(SAVING)) {
            double newBalance = savingBalance + amt;
            //calculates the new balance
            savingBalance = newBalance;
            //updates current balance
            deposits.add(new Deposit(amt, date, SAVING, newBalance));
            //adds deposit to list
        }
        else {
            System.out.println("Invalid account type. Please use CHECKING or SAVING");
            return amt;
            //returns error message if invalid account
        }
        return account.equals(CHECKING) ? checkBalance : savingBalance;
        //returns the updated balance for specific account
        //This method allows for depositing money into a specific account

        //Requires: amt > 0, date, checking or saving
        //Modifies: this
        //Effect: deposits specified amount into the specified account, then returns updated balance
    }

    public double withdraw(double amt, Date date, String account) {
        if (amt <= 0) {
            System.out.println("Withdrawal amount must be more than zero.");
            return amt;
            //returns error message if withdrawal amount is <= 0 and ignores the value given
        }
        if (account.equals(CHECKING)) {
            if (checkBalance - amt >= overdraftLimit) {
                checkBalance -= amt;
                if (checkBalance < 0) {
                    checkBalance -= overdraftFee;
                    System.out.println("Overdraft occurred. A fee of $" + overdraftFee + " has been applied.");
                }
                withdraws.add(new Withdraw(amt, date, CHECKING));
                return amt;
            } else {
                System.out.println("Withdrawal denied, exceeds overdraft limit");
                return 0;
                //adds withdrawal to list
            }
        } else if (account.equals(SAVING)) {
            if (savingBalance - amt >= overdraftLimit) {
                savingBalance -= amt;
                if (savingBalance < 0) {
                    savingBalance -= overdraftFee;
                    System.out.println("Overdraft occurred. A fee of $" + overdraftFee + " has been applied.");
                }
            }
            withdraws.add(new Withdraw(amt, date, SAVING));
            return amt;
        } else {
            System.out.println("Withdrawal denied, exceeds overdraft limit.");
            return 0;
            //adds withdrawal to list
        }
    }
        //Requires: amt > 0, date, checking or saving
        //Modifies: this
        //Effect: withdraws specified amount from specified account, applies overdraft fee when applicable and returns withdrawn amount



    //do not modify
    public void displayDeposits(){
        for(Deposit d : deposits){
            System.out.println(d);
        }
        //Requires: nothing
        //Modifies: nothing
        //Effect: displays all deposits made by customer
    }
    //do not modify
    public void displayWithdraws(){
        for(Withdraw w : withdraws){
            System.out.println(w);
        }
        //Requires: nothing
        //Modifies: nothing
        //Effect: displays all withdraws made by customer
    }

}
