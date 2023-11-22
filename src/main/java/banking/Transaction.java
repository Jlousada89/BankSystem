package banking;

import java.util.Objects;

/**
 * A bank transaction implementation.
 */
public class Transaction implements TransactionInterface {
    private Long accountNumber;
    private BankInterface bank;

    private int attemptedPin;

    /**
     * @param bank          The bank where the account is housed.
     * @param accountNumber The customer's account number.
     * @param attemptedPin  The PIN entered by the customer.
     * @throws Exception Account validation failed.
     */
    public Transaction(BankInterface bank, Long accountNumber, int attemptedPin) throws Exception {

        if (Objects.isNull(bank) || Objects.isNull(accountNumber) ) {
            throw new Exception();
        }

        this.bank = bank;
        this.accountNumber = accountNumber;

        if ( this.bank.getAccount(accountNumber).validatePin(attemptedPin) ) {
            this.attemptedPin = attemptedPin;
        }
        else {
            throw new Exception();
        }
    }

    public double getBalance() {
        return this.bank.getBalance(this.accountNumber);
    }

    public void credit(double amount) {
        this.bank.credit(this.accountNumber, amount);
    }

    public boolean debit(double amount) {
       return this.bank.debit(this.accountNumber, amount);
    }
}
