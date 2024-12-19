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
    private final int OVERDRAFT = -100;
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
    public double getCheckBalance()
        {return checkBalance;}
    //allows other classes to get the CHECKING account balance number

    public double getSavingBalance()
        {return savingBalance;}
    //allows other classes to get the SAVING account balance number

    public double getAccountNumber()
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
            checkBalance += amt;
            deposits.add(new Deposit(amt, date, CHECKING));
            //adds deposit to list
        }
        else if (account.equals(SAVING)) {
            savingBalance += amt;
            deposits.add(new Deposit(amt, date, SAVING));
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
    }

    public double withdraw(double amt, Date date, String account) {
        if (amt <= 0) {
            System.out.println("Withdrawal amount must be more than zero.");
            return amt;
            //returns error message if withdrawal amount is <= 0 and ignores the value given
        }
        if (account.equals(CHECKING)) {
            checkOverdraft(amt, CHECKING);
            checkBalance -= amt;
            withdraws.add(new Withdraw(amt, date, CHECKING));
            return amt;
            //adds withdrawal to list
        }
        else if (account.equals(SAVING)) {
            checkOverdraft(amt, SAVING);
            savingBalance -= amt;
            withdraws.add(new Withdraw(amt, date, SAVING));
            return amt;
            //adds withdrawal to list
        }
        else {
            System.out.println("Invalid account type. Please use CHECKING or SAVING");
            //returns error message if invalid account type
        }
        return amt;
        //returns updated balance of specific account
        //This method allows withdrawal from specific accounts
    }

    private boolean checkOverdraft(double amt, String account){
        if (account.equals(CHECKING)) {
            if (checkBalance - amt >= 0) {
                return true;
            } else {
                checkBalance += OVERDRAFT;
                System.out.println("OVERDRAFT");
                return false;
            }
        } else if (account.equals(SAVING)) {
            if (savingBalance - amt >= 0){
                return true;
            } else {
                savingBalance += OVERDRAFT;
                System.out.println("OVERDRAFT");
                return false;
            }
            //Checks overdraft of SAVING
        }
        else {
            System.out.println("Invalid account type for overdraft check");
            return true;
            //treats this case as an overdraft for an invalid account
        }
        //this method checks if the withdrawal request exceeds the overdraft limit
    }
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
