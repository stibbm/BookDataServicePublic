package book.data.service.activity.account;

import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreateAccountActivity {
    private AccountManager accountManager;
    private FirebaseService firebaseService;

    @Autowired
    public CreateAccountActivity(
            AccountManager accountManager,
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
