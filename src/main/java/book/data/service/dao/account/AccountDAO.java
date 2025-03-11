package book.data.service.repository;


import lombok.extern.slf4j.Slf4j;
import book.data.service.repository.AccountRepository;
import book.data.service.sqlmodel.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountDAO {
    private AccountRepository accountRepository;

    @Autowired
    public AccountDAO(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountByEmailAddress(String emailAddress) {
        return accountRepository.findAccountByEmailAddress(emailAddress);
    }

    public Account getAccountByStripeCustomerId(String customerId) {
        return accountRepository.findAccountByStripeCustomerId(customerId);
    }

    public boolean doesAccountExistByEmailAddress(String emailAddress) {
        return this.accountRepository.doesAccountExistByEmailAddress(emailAddress);
    }

    public Account createAccount(
        String stripeCustomerId,
        String emailAddress,
        String fullName,
        Boolean emailAddressVerified,
        Long createdEpochMilli
    ) {
        log.info("Create account");
        log.info("email address: " + emailAddress);
        Account account = Account.createAccountWithoutId(
            stripeCustomerId,
            emailAddress,
            fullName,
            emailAddressVerified,
            createdEpochMilli
        );
        log.info("Account: ");
        log.info(account.toString());
        accountRepository.saveAccount(account);
        account = accountRepository.findAccountByEmailAddress(emailAddress);
        return account;
    }
}
