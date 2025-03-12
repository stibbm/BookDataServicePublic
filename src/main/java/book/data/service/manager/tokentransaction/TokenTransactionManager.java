package book.data.service.manager.tokentransaction;

import book.data.service.dao.tokentransaction.PaymentForTranslatedYoutubeBookDAO;
import book.data.service.dao.tokentransaction.TokenTransactionDAO;
import book.data.service.exception.payment.PaymentForTranslatedYoutubeBookAlreadyExistsException;
import book.data.service.exception.tokentransaction.InsufficientTokenFundsException;
import book.data.service.model.TranslatedYoutubeBookStatus;
import book.data.service.model.VideoCreationMilestone;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import book.data.service.sqlmodel.transaction.TokenTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;

import static book.data.service.constants.Constants.TOKEN_TRANSACTION_LOCK;

@Slf4j
@Service
public class TokenTransactionManager {

    private TokenTransactionDAO tokenTransactionDAO;
    private TimeService timeService;
    private PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO;

    @Autowired
    public TokenTransactionManager(
            TokenTransactionDAO tokenTransactionDAO,
            TimeService timeService,
            PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO
    ) {
        this.tokenTransactionDAO = tokenTransactionDAO;
        this.timeService = timeService;
        this.paymentForTranslatedYoutubeBookDAO = paymentForTranslatedYoutubeBookDAO;
    }

    public List<TokenTransaction> getTokenTransactions(
            String emailAddress
    ) {
        List<TokenTransaction> tokenTransactionList = this.tokenTransactionDAO.getTokenTransactions(
                emailAddress);
        return tokenTransactionList;
    }

    public AbstractMap.SimpleEntry<TokenTransaction, PaymentForTranslatedYoutubeBook> attemptTokenPaymentForTranslateYoutubeBook(
            String dedupeToken,
            Long bookNumber,
            Long startChapter,
            Long endChapter,
            Long tokensAmount,
            String createdBy) {
        log.info("attemptTokenPaymentForTranslateYoutubeBook called");
        if (tokensAmount >= 0) {
            throw new IllegalArgumentException(
                    "tokensAmount must be less than 0 when making a payment");
        }
        synchronized (TOKEN_TRANSACTION_LOCK) {
            log.info("in synchronizedLock");
            if (paymentForTranslatedYoutubeBookDAO.doesPaymentForTranslatedYoutubeBookExist(
                    bookNumber, startChapter, endChapter
            )) {
                throw new PaymentForTranslatedYoutubeBookAlreadyExistsException();
            }
            Long checkoutSessionNumber = -1L; // there is no session number for token payment
            Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
            List<TokenTransaction> tokenTransactionList = tokenTransactionDAO.getTokenTransactions(
                    createdBy);
            Long netTokens = tokenTransactionList.stream()
                    .mapToLong(TokenTransaction::getTokensAmount).sum();
            log.info("net tokens: " + netTokens);
            Long tokenAmountAbs = Math.abs(tokensAmount);
            log.info("tokenAmountAbs: " + tokenAmountAbs);
            if (netTokens >= tokenAmountAbs) {
                TokenTransaction tokenTransaction = tokenTransactionDAO.createTokenTransaction(
                        checkoutSessionNumber,
                        tokensAmount,
                        createdBy,
                        createdEpochMilli
                );
                if (tokenTransaction == null) {
                    throw new RuntimeException("tokenTransaction creation failed");
                }
                PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook =
                        paymentForTranslatedYoutubeBookDAO.createPayment(
                                dedupeToken,
                                TranslatedYoutubeBookStatus.PAID_FOR.toString(),
                                VideoCreationMilestone.STARTED,
                                tokenTransaction.getTokenTransactionNumber(),
                                bookNumber,
                                startChapter,
                                endChapter,
                                createdBy,
                                createdEpochMilli);
                if (paymentForTranslatedYoutubeBook == null) {
                    throw new RuntimeException("paymentForTranslatedYoutubeBook creation failed");
                }
                AbstractMap.SimpleEntry<TokenTransaction, PaymentForTranslatedYoutubeBook> transactionInfo =
                        new AbstractMap.SimpleEntry<>(tokenTransaction,
                                paymentForTranslatedYoutubeBook);
                return transactionInfo;
            } else {
                throw new InsufficientTokenFundsException();
            }
        }
    }

    public TokenTransaction createTokenTransaction(
            Long checkoutSessionNumber,
            Long tokensAmount,
            String createdBy
    ) {
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        TokenTransaction tokenTransaction = tokenTransactionDAO.createTokenTransaction(
                checkoutSessionNumber,
                tokensAmount,
                createdBy,
                createdEpochMilli
        );
        return tokenTransaction;
    }
}

