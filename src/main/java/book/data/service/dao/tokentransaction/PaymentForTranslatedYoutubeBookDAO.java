package book.data.service.dao.tokentransaction;

import book.data.service.exception.BookDoesNotExistException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.model.VideoCreationMilestone;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.PaymentForTranslatedYoutubeBookRepository;
import book.data.service.repository.TokenTransactionRepository;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentForTranslatedYoutubeBookDAO {

    private PaymentForTranslatedYoutubeBookRepository paymentForTranslatedYoutubeBookRepository;
    private TokenTransactionRepository tokenTransactionRepository;
    private BookRepository bookRepository;
    private ChapterRepository chapterRepository;

    @Autowired
    public PaymentForTranslatedYoutubeBookDAO(
        PaymentForTranslatedYoutubeBookRepository paymentForTranslatedYoutubeBookRepository,
        TokenTransactionRepository tokenTransactionRepository,
        BookRepository bookRepository,
        ChapterRepository chapterRepository) {
        this.paymentForTranslatedYoutubeBookRepository = paymentForTranslatedYoutubeBookRepository;
        this.tokenTransactionRepository = tokenTransactionRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
    }

    public List<PaymentForTranslatedYoutubeBook> getPayments(String createdBy) {
        return paymentForTranslatedYoutubeBookRepository.findPayments(createdBy);
    }

    public PaymentForTranslatedYoutubeBook getPaymentByBookNumberAndStartChapterAndEndChapter(
        Long bookNumber,
        Long startChapter,
        Long endChapter
    ) {
        if (!this.paymentForTranslatedYoutubeBookRepository.doesPaymentForTranslatedYoutubeBookExist(
            bookNumber, startChapter, endChapter
        )) {
            throw new PaymentForTranslatedYoutubeBookDoesNotExistException();
        }
        return paymentForTranslatedYoutubeBookRepository
            .findPaymentByBookNumberAndStartChapterAndEndChapter(
                bookNumber,
                startChapter,
                endChapter);
    }

    public PaymentForTranslatedYoutubeBook getPaymentByBookNumberAndStartChapterAndEndChapterAndCreatedBy(
        Long bookNumber,
        Long startChapter,
        Long endChapter,
        String createdBy
    ) {
        return paymentForTranslatedYoutubeBookRepository
            .findPaymentByBookNameAndStartChapterAndEndChapterAndCreatedBy(
                bookNumber,
                startChapter,
                endChapter,
                createdBy
            );
    }

    public boolean doesPaymentForTranslatedYoutubeBookExist(
        Long bookNumber,
        Long startChapter,
        Long endChapter
    ) {
        return paymentForTranslatedYoutubeBookRepository
            .doesPaymentForTranslatedYoutubeBookExist(bookNumber, startChapter, endChapter);
    }

    public PaymentForTranslatedYoutubeBook updatePaymentTranslatedYoutubeBookStatus(
        String newTranslatedYoutubeBookStatus,
        Long bookNumber,
        Long startChapter,
        Long endChapter
    ) {
        log.info("Updating payment youtubeBook status to {} for bookNumber: {}, startChapter: {}, endChapter: {}",
            newTranslatedYoutubeBookStatus, bookNumber, startChapter, endChapter);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook =
            paymentForTranslatedYoutubeBookRepository.findPaymentByBookNumberAndStartChapterAndEndChapter(
                bookNumber, startChapter, endChapter);
        paymentForTranslatedYoutubeBookRepository.updateTranslatedYoutubeBookStatus(
            newTranslatedYoutubeBookStatus,
            bookNumber,
            startChapter,
            endChapter
        );
        paymentForTranslatedYoutubeBook.setTranslatedYoutubeBookStatus(newTranslatedYoutubeBookStatus);
        log.info("paymentForTranslatedBook: " + paymentForTranslatedYoutubeBook);
        log.info("paymentForTranslatedBook status: " + paymentForTranslatedYoutubeBook.getTranslatedYoutubeBookStatus());
        log.info("newTranslatedYoutubeBookStatus: " + newTranslatedYoutubeBookStatus);
        if (paymentForTranslatedYoutubeBook.getTranslatedYoutubeBookStatus().equals(newTranslatedYoutubeBookStatus)) {
            return paymentForTranslatedYoutubeBook;
        } else {
            throw new RuntimeException("failed to update payment youtubeBook status to " + newTranslatedYoutubeBookStatus);
        }
    }

    public void updatePaymentMilestone(
        VideoCreationMilestone videoCreationMilestone,
        Long bookNumber,
        Long startNumber,
        Long endNumber) {
        if (!paymentForTranslatedYoutubeBookRepository.doesPaymentForTranslatedYoutubeBookExist(
            bookNumber,
            startNumber,
            endNumber))
        {
            throw new RuntimeException("failed to update payment youtubeBook status to " + bookNumber + " because the book does not exist");
        }
        paymentForTranslatedYoutubeBookRepository.updatePaymentMilestone(videoCreationMilestone,
            bookNumber, startNumber, endNumber);
    }

    public PaymentForTranslatedYoutubeBook createPayment(
        String dedupeId,
        String translatedYoutubeBookStatus,
        VideoCreationMilestone videoCreationMilestone,
        Long tokenTransactionNumber,
        Long bookNumber,
        Long startChapter,
        Long endChapter,
        String createdBy,
        Long createdEpochMilli
    ) {
        if (paymentForTranslatedYoutubeBookRepository.doesPaymentForTranslatedYoutubeBookExist(
            bookNumber,
            startChapter,
            endChapter))
        {
            throw new PaymentForTranslatedYoutubeBookAlreadyExistsException();
        }
        if (!tokenTransactionRepository.doesTokenTransactionExist(tokenTransactionNumber, createdBy)) {
            throw new TokenTransactionDoesNotExistException();
        }
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        for (Long i=startChapter;i<=endChapter; i++) {
            if (!chapterRepository.doesChapterExistWithOnlyBookNumberAndChapterNumber(bookNumber, i)) {
                throw new ChapterDoesNotExistException();
            }
        }
        PaymentForTranslatedYoutubeBook payment =
            PaymentForTranslatedYoutubeBook.buildPaymentForTranslatedYoutubeBookWithoutNumber(
                dedupeId,
                translatedYoutubeBookStatus,
                videoCreationMilestone,
                tokenTransactionNumber,
                bookNumber,
                startChapter,
                endChapter,
                createdBy,
                createdEpochMilli);
        paymentForTranslatedYoutubeBookRepository.savePayment(payment);
        payment = paymentForTranslatedYoutubeBookRepository
            .findPaymentByBookNameAndStartChapterAndEndChapterAndCreatedBy(
                bookNumber,
                startChapter,
                endChapter,
                createdBy);
        return payment;
    }
}
