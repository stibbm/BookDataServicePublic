package book.data.service.activity.tokentransaction;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.GET_TOKEN_TRANSACTIONS;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.tokentransaction.TokenTransactionManager;
import book.data.service.message.tokentransaction.GetTokenTransactionsResponse;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetTokenTransactionsActivity {
    private TokenTransactionManager tokenTransactionManager;
    private FirebaseService firebaseService;

    @Autowired
    public GetTokenTransactionsActivity(
        TokenTransactionManager tokenTransactionManager,
        FirebaseService firebaseService
    ) {
        this.tokenTransactionManager = tokenTransactionManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(GET_TOKEN_TRANSACTIONS)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody GetTokenTransactionsResponse getTokenTransactions(
        @RequestHeader("Authorization") String authToken
    ) {
        String email = firebaseService.getEmail(authToken);
        List<TokenTransaction> tokenTransactionList = tokenTransactionManager
            .getTokenTransactions(email);
        GetTokenTransactionsResponse getTokenTransactionsResponse = GetTokenTransactionsResponse
            .builder().tokenTransactionList(tokenTransactionList).build();
        return getTokenTransactionsResponse;
    }
}
