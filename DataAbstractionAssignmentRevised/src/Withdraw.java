import java.util.Date;

public class Withdraw {
    private double amount;
    private Date date;
    private String account;

    Withdraw(double amount, Date date, String account){
        this.amount = amount;
        this.date = date;
        this.account = account;
    }
    //Requires: amount > 0, date, Checking or Saving
    //Modifies: this
    //Effect: creates a new withdraw object with specified amount, date, and account

    public String toString(){
        return("Withdrawal of: $" + amount + " Date: " + date + " From account: " + account);
        //returns the relevant information
    }
    //Require: nothing
    //Modifies: nothing
    //Effect: returns string with withdraw information
}
