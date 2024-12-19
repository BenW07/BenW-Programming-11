import org.junit.Before;
import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;
//assertEquals is helpful for making sure the expected and actual outcomes are the same within a given range (delta)

public class DataAbstractionTesting {

    Customer customerTesting;
    Customer customerTesting2;

    @Before
    public void setUp() {
        customerTesting = new Customer();
        customerTesting2 = new Customer();
    }

    @Test
    public void testDefaultCustomerConstructor() {
        // Tests if default customer constructor works correctly
    }

    @Test
    public void testCustomerConstructor() {
        Customer customerConstructorTestSet = new Customer("Jacob", 1, 900, 2000);
        assertEquals(customerConstructorTestSet.getAccountNumber(), 1);
        assertEquals(customerConstructorTestSet.getCheckBalance(), 900, 0);
        assertEquals(customerConstructorTestSet.getSavingBalance(), 2000, 0);
        // Tests if the constructor sets the values correctly from the custom parameters given

        Customer negativeCustomerConstructorTestSet = new Customer("Linda", 2, -100, -500);
        assertEquals(negativeCustomerConstructorTestSet.getCheckBalance(), 0, 0);
        assertEquals(negativeCustomerConstructorTestSet.getSavingBalance(), 0, 0);
        //Tests if negative deposits are ignored
    }

    @Test
    public void depositWithdrawTesting() {
        customerTesting.deposit(450, new Date(), Customer.CHECKING);
        customerTesting.deposit(999, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 450, 0);
        assertEquals(customerTesting.getSavingBalance(), 999, 0);
        //Tests if deposits work correctly

        customerTesting.withdraw(200, new Date(), Customer.CHECKING);
        customerTesting.withdraw(400, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 250, 0);
        assertEquals(customerTesting.getSavingBalance(), 599, 0);
        //Tests if the withdraw method is working correctly

        customerTesting.deposit(522.5, new Date(), Customer.CHECKING);
        customerTesting.deposit(300.8, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 772.5, 0);
        assertEquals(customerTesting.getSavingBalance(), 899.8, 0);
        //Tests if decimal deposits work

        customerTesting.withdraw(22.5, new Date(), Customer.CHECKING);
        customerTesting.withdraw(244.8, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 750, 0);
        assertEquals(customerTesting.getSavingBalance(), 655, 0);
        //Tests if withdrawals with decimals work correctly

        customerTesting.deposit(-2233, new Date(), Customer.CHECKING);
        customerTesting.deposit(-1111, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 750, 0);
        assertEquals(customerTesting.getSavingBalance(), 655, 0);
        //Tests if negative deposits get ignored

        customerTesting.withdraw(-912, new Date(), Customer.CHECKING);
        customerTesting.withdraw(-44, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getCheckBalance(), 750, 0);
        assertEquals(customerTesting.getSavingBalance(), 655, 0);
        //Tests if negative withdrawals get ignored
    }

    @Test
    public void overdraftTest() {
        customerTesting.deposit(10000, new Date(), Customer.CHECKING);
        customerTesting.withdraw(11000, new Date(), Customer.CHECKING);
        assertEquals(customerTesting.getCheckBalance(), -1200, 0);
        //Tests overdraft fee for CHECKING account

        customerTesting.deposit(5000, new Date(), Customer.SAVING);
        customerTesting.withdraw(6000, new Date(), Customer.SAVING);
        assertEquals(customerTesting.getSavingBalance(), -1200, 0);
        //Tests SAVING account overdraft fee

        customerTesting2.deposit(200.50, new Date(), Customer.CHECKING);
        customerTesting2.withdraw(300.30, new Date(), Customer.CHECKING);
        assertEquals(customerTesting2.getCheckBalance(), -299.8, 0.01);
        //Tests overdraft fee with decimals for CHECKING account

        customerTesting2.deposit(2345.11, new Date(), Customer.SAVING);
        customerTesting2.withdraw(3453.09, new Date(), Customer.SAVING);
        assertEquals(customerTesting2.getSavingBalance(), -1307.98, 0.01);
        //Tests overdraft fee with decimals for SAVING account
        //Delta 0.01 meaning variation between expected and actual amount can be one hundredth off
    }
}