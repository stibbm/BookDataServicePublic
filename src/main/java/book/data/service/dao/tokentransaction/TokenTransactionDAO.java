package book.data.service.dao.tokentransaction;

import static matt.book.data.service.constants.Constants.TOKEN_TRANSACTION_LOCK;

import book.data.service.repository.CheckoutSessionRepository;
import book.data.service.repository.TokenTransactionRepository;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenTransactionDAO {
    private TokenTransactionRepository tokenTransactionRepository;
    private CheckoutSessionRepository checkoutSessionRepository;

    @Autowired
    public TokenTransactionDAO(
        TokenTransactionRepository tokenTransactionRepository,
        CheckoutSessionRepository checkoutSessionRepository
    ) {
        this.tokenTransactionRepository = tokenTransactionRepository;
        this.checkoutSessionRepository = checkoutSessionRepository;
    }



    public List<TokenTransaction> getTokenTransactions(String createdBy) {
        return this.tokenTransactionRepository.findTokenTransactions(createdBy);
    }

    public TokenTransaction createTokenTransaction(
        Long checkoutSessionNumber,
        Long tokensAmount,
        String createdBy,
        Long createdEpochMilli
    ) {
        // putting this here to avoid lock scenario where if in repository
        // the code gets within that method call then something else gets lock
        // trying to make payment and the payment method is then locked from writing do db
        // since the @Transaction lock is already acquired
        synchronized(TOKEN_TRANSACTION_LOCK) {
            if (checkoutSessionNumber == -1) {
                // valid case
                log.info("checkout session is -1 since it does not correspond to a checkout session");
            }
            else if (!checkoutSessionRepository
                .doesCheckoutSessionExistByCheckoutSessionNumber(checkoutSessionNumber,
                    createdBy)) {
                throw new CheckoutSessionDoesNotExistException();
            }
            TokenTransaction tokenTransaction = TokenTransaction.buildTokenTransactionWithoutId(
                checkoutSessionNumber,
                tokensAmount,
                createdEpochMilli,
                createdBy
            );
            tokenTransactionRepository.saveTokenTransaction(tokenTransaction);
            return tokenTransaction;
        }
    }
}
