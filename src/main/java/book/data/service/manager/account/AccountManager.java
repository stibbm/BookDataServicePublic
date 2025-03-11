package book.data.service.manager.account;

import book.data.service.clientwrapper.StripeClientWrapper;
import book.data.service.repository.AccountDAO;
import book.data.service.service.time.TimeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountManager {
    private AccountDAO accountDAO;
    private TimeService timeService;
    private StripeClientWrapper stripeClientWrapper;

    @Autowired
    public AccountManager(
        AccountDAO accountDAO,
        TimeService timeService,
        StripeClientWrapper stripeClientWrapper
    ) {
        this.accountDAO = accountDAO;
        this.timeService = timeService;
        this.stripeClientWrapper = stripeClientWrapper;
    }

    public Account getAccountByEmailAddress(String emailAddress) {
        return accountDAO.getAccountByEmailAddress(emailAddress);
    }

    public Account getAccountByStripeCustomerId(String customerId) {
        return accountDAO.getAccountByStripeCustomerId(customerId);
    }

    public Account createAccount(
        String emailAddress,
        String fullName
    ) throws StripeException {
        Customer customer = stripeClientWrapper.createCustomer(
            emailAddress,
            fullName
        );
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        String customerId = customer.getId();
        boolean doesAccountExist = accountDAO.doesAccountExistByEmailAddress(emailAddress);
        if (!doesAccountExist) {
            return accountDAO.createAccount(
                customerId,
                emailAddress,
                fullName,
                ACCOUNT_UNVERIFIED,
                createdEpochMilli
            );
        }
        else {
            throw new AccountAlreadyExistsException();
        }
    }
}
