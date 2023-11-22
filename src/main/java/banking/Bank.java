package banking;

import java.util.*;

/**
 * The Bank implementation.
 */
public class Bank implements BankInterface {
    private LinkedHashMap<Long, Account> accounts;

    private Long accountNumber = 1L;

    public Bank() {
        this.accounts = new LinkedHashMap<Long, Account> ();
    }

    public Account getAccount(Long accountNumber) {
        return this.accounts.get(accountNumber);
    }

    public LinkedHashMap<Long, Account> getAccounts(){
        return this.accounts;
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {
        CommercialAccount comercialAccount = new CommercialAccount(company,  this.generateAccountNumber(), pin, startingDeposit);
        this.accounts.put(comercialAccount.getAccountNumber(), comercialAccount);
        return comercialAccount.getAccountNumber();
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        ConsumerAccount consumerAccount = new ConsumerAccount(person, this.generateAccountNumber(), pin, startingDeposit);
        this.accounts.put(consumerAccount.getAccountNumber(), consumerAccount);
        return consumerAccount.getAccountNumber();
    }

    public double getBalance(Long accountNumber) {
        Account account = this.getAccount(accountNumber);

        if (Objects.nonNull(account)) {
            return account.getBalance();
        }
        else {
            return -1.0;
        }
    }

    public void credit(Long accountNumber, double amount) {
        this.getAccount(accountNumber).creditAccount(amount);
    }

    public boolean debit(Long accountNumber, double amount) {
        return this.getAccount(accountNumber).debitAccount(amount);
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        return this.getAccount(accountNumber).validatePin(pin);
    }
    
    public void addAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        Account account = this.getAccount(accountNumber);
        if ( account instanceof CommercialAccount) {
            ((CommercialAccount) account).addAuthorizedUser(authorizedPerson);
        }
    }

    public boolean checkAuthorizedUser(Long accountNumber, Person authorizedPerson) {
        Account account = this.getAccount(accountNumber);
        if ( Objects.isNull(account) || account instanceof ConsumerAccount) {
            return false;
        } else {
            return ((CommercialAccount) account).isAuthorizedUser(authorizedPerson);
        }
    }

    public Map<String, Double> getAverageBalanceReport() {

        Map<String, Double> report = new HashMap<>();

        int nConsumerAccount = 0;
        double totalConsumerAccount = 0.0;
        int nCommercialAccount = 0;
        double totalCommercialAccount = 0.0;

        for (Long key : this.accounts.keySet()) {
            if ( this.getAccount(key) instanceof  ConsumerAccount ) {
                totalConsumerAccount += this.getAccount(key).getBalance();
                nConsumerAccount++;
            } else {
                totalCommercialAccount += this.getAccount(key).getBalance();
                nCommercialAccount++;
            }
        }
        report.put("ConsumerAccount", totalConsumerAccount/nConsumerAccount);
        report.put("CommercialAccount", totalCommercialAccount/nCommercialAccount);

        return report;
    }

    private Long generateAccountNumber() {
        return this.accountNumber++;
    }
}
