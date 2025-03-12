package book.data.service.activity.account;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.account.AccountManager;
import book.data.service.message.account.GetAccountResponse;
import book.data.service.sqlmodel.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_ACCOUNT;

@Slf4j
@RestController
public class GetAccountActivity {
    private AccountManager accountManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetAccountActivity(
            AccountManager accountManager,
            FirebaseService firebaseService
    ) {
        this.accountManager = accountManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_ACCOUNT)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetAccountResponse getAccount(
            @RequestHeader("Authorization") String authToken
    ) {
        log.info("getAccount: ");
        String email = firebaseService.getEmail(authToken);
        Account account = accountManager.getAccountByEmailAddress(email);
        log.info("account: ");
        log.info(account.toString());
        return GetAccountResponse.builder().account(account).build();
    }
}
