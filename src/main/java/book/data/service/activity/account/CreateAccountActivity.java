package book.data.service.activity.account;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.account.AccountManager;
import book.data.service.message.account.CreateAccountRequest;
import book.data.service.message.account.CreateAccountResponse;
import book.data.service.sqlmodel.account.Account;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.CREATE_ACCOUNT;

@Slf4j
@RestController
public class CreateAccountActivity {
    private AccountManager accountManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateAccountActivity(
            book.data.service.manager.account.AccountManager accountManager,
            FirebaseService firebaseService
    ) {
        this.accountManager = accountManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(CREATE_ACCOUNT)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody CreateAccountResponse createAccount(
            @RequestBody CreateAccountRequest createAccountRequest,
            @RequestHeader("Authorization") String authToken
    ) throws StripeException {
        log.info("createAccount: ");
        log.info(createAccountRequest.toString());
        String createdBy = firebaseService.getEmail(authToken);
        log.info("emailAddress: " + createdBy);
        Account account = accountManager.createAccount(
                createdBy, createAccountRequest.getFullName());
        log.info("created Account: ");
        log.info(account.toString());
        return CreateAccountResponse.builder().account(account).build();
    }
}
