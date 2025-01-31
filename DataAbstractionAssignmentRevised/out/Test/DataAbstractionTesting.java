import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.*;

public class DataAbstractionTesting {

    Customer customerTesting;
    Customer customerTesting2;

    @Before
    public void setUp() {
        customerTesting = new Customer();
        customerTesting2 = new Customer();
    }
    //Requires: nothing
    //Modifies: this
    //Effect: prepares the test environment by creating Customer objects before each test

    @Test
    public void testDefaultCustomerConstructor() {
        assertEquals("Customer1", customerTesting.getName());
        assertEquals(0, customerTesting.getAccountNumber());
        assertEquals(0.0, customerTesting.getCheckBalance(), 0);
        assertEquals(0.0, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: nothing
    //Effect: verifies that the default constructor initializes the Customer object with the correct default values

    @Test
    public void testCustomerConstructor() {
        // test valid inputs
        Customer customerConstructorTestSet = new Customer("Jacob", 1, 900, 2000);
        assertEquals(1, customerConstructorTestSet.getAccountNumber());
        assertEquals(900, customerConstructorTestSet.getCheckBalance(), 0);
        assertEquals(2000, customerConstructorTestSet.getSavingBalance(), 0);

        // test negative balances (if the constructor handles them)
        Customer negativeCustomerConstructorTestSet = new Customer("Linda", 2, -100, -500);
        assertEquals(-100, negativeCustomerConstructorTestSet.getCheckBalance(), 0); // Negative balance is allowed
        assertEquals(-500, negativeCustomerConstructorTestSet.getSavingBalance(), 0); // Negative balance is allowed
    }
//Requires: nothing
//Modifies: nothing
//Effect: verifies that the custom constructor initializes the Customer object with the specified values, including negative balances

    @Test
    public void testDepositPositiveAmount() {
        customerTesting.deposit(100, new Date(), Customer.CHECKING);
        assertEquals(100, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(200, new Date(), Customer.SAVING);
        assertEquals(200, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (updates balances through deposits)
    //Effect: tests that positive deposits are correctly added to the account balances

    @Test
    public void testDepositNegativeAmount() {
        customerTesting.deposit(-100, new Date(), Customer.CHECKING);
        assertEquals(0, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(-200, new Date(), Customer.SAVING);
        assertEquals(0, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to deposit negative amounts)
    //Effect: tests that negative deposits are ignored and do not affect the balances

    @Test
    public void testDepositZeroAmount() {
        customerTesting.deposit(0, new Date(), Customer.CHECKING);
        assertEquals(0, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(0, new Date(), Customer.SAVING);
        assertEquals(0, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to deposit zero amounts)
    //Effect: tests that zero deposits are ignored and do not affect the balances

    @Test
    public void testWithdrawPositiveAmount() {
        customerTesting.deposit(500, new Date(), Customer.CHECKING);
        customerTesting.withdraw(200, new Date(), Customer.CHECKING);
        assertEquals(300, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(1000, new Date(), Customer.SAVING);
        customerTesting.withdraw(300, new Date(), Customer.SAVING);
        assertEquals(700, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (updates balances through withdrawals)
    //Effect: tests that positive withdrawals are correctly deducted from the account balances

    @Test
    public void testWithdrawNegativeAmount() {
        customerTesting.deposit(500, new Date(), Customer.CHECKING);
        customerTesting.withdraw(-200, new Date(), Customer.CHECKING);
        assertEquals(500, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(1000, new Date(), Customer.SAVING);
        customerTesting.withdraw(-300, new Date(), Customer.SAVING);
        assertEquals(1000, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to withdraw negative amounts)
    //Effect: tests that negative withdrawals are ignored and do not affect the balances

    @Test
    public void testWithdrawZeroAmount() {
        customerTesting.deposit(500, new Date(), Customer.CHECKING);
        customerTesting.withdraw(0, new Date(), Customer.CHECKING);
        assertEquals(500, customerTesting.getCheckBalance(), 0);

        customerTesting.deposit(1000, new Date(), Customer.SAVING);
        customerTesting.withdraw(0, new Date(), Customer.SAVING);
        assertEquals(1000, customerTesting.getSavingBalance(), 0);
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to withdraw zero amounts)
    //Effect: tests that zero withdrawals are ignored and do not affect the balances

    @Test
    public void testWithdrawExceedsBalance() {
        customerTesting.deposit(500, new Date(), Customer.CHECKING);
        customerTesting.withdraw(600, new Date(), Customer.CHECKING);
        assertEquals(-200, customerTesting.getCheckBalance(), 0); // Overdraft fee applied

        customerTesting.deposit(1000, new Date(), Customer.SAVING);
        customerTesting.withdraw(1100, new Date(), Customer.SAVING);
        assertEquals(-200, customerTesting.getSavingBalance(), 0); // Overdraft fee applied
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to withdraw more than the balance)
    //Effect: tests that withdrawals exceeding the balance are allowed but result in an overdraft fee

    @Test
    public void testWithdrawExceedsOverdraftLimit() {
        customerTesting.deposit(100, new Date(), Customer.CHECKING);
        customerTesting.withdraw(300, new Date(), Customer.CHECKING); // Exceeds overdraft limit
        assertEquals(100, customerTesting.getCheckBalance(), 0); // Withdrawal denied

        customerTesting.deposit(200, new Date(), Customer.SAVING);
        customerTesting.withdraw(400, new Date(), Customer.SAVING); // Exceeds overdraft limit
        assertEquals(200, customerTesting.getSavingBalance(), 0); // Withdrawal denied
    }
    //Requires: nothing
    //Modifies: customerTesting (tries to withdraw beyond the overdraft limit)
    //Effect: tests that withdrawals exceeding the overdraft limit are denied
}