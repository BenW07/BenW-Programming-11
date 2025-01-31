import java.util.Date;

public class Deposit {
    private double amount;
    private Date date;
    private String account;
    private double currentBalance;

    Deposit(double amount, Date date, String account, double currentBalance){
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.currentBalance = currentBalance;
    }
    //Requires: amount > 0, date, Checking or Saving, currentBalance
    //Modifies: this
    //Effect: creates a new deposit with specified amount, date, account, and updated currentBalance

    public String toString(){
        return("Deposit of: $" + amount + " Date: " + date + " To account: " + account + " Current balance of " + account + " is: $" + currentBalance);
        //returns the relevant information
    }
    //Require: nothing
    //Modifies: nothing
    //Effect: returns string with deposit information
}
