package book.data.service.manager.milestone;

import book.data.service.dao.tokentransaction.PaymentForTranslatedYoutubeBookDAO;
import book.data.service.model.VideoCreationMilestone;
import book.data.service.sqlmodel.payment.PaymentForTranslatedYoutubeBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VideoCreationMilestoneManager {

    private PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO;

    @Autowired
    public VideoCreationMilestoneManager(
            PaymentForTranslatedYoutubeBookDAO paymentForTranslatedYoutubeBookDAO) {
        this.paymentForTranslatedYoutubeBookDAO = paymentForTranslatedYoutubeBookDAO;
    }

    public PaymentForTranslatedYoutubeBook getMilestone(Long bookNumber, Long startChapter,
                                                        Long endChapter) {
        //log.info(
        //  "getMilestone: " + bookNumber + " : " + startChapter + " : " + endChapter);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook =
                paymentForTranslatedYoutubeBookDAO.getPaymentByBookNumberAndStartChapterAndEndChapter(
                        bookNumber, startChapter, endChapter
                );
        //log.info("paymentForTranslatedYoutubeBook: " + paymentForTranslatedYoutubeBook);
        return paymentForTranslatedYoutubeBook;
    }

    public PaymentForTranslatedYoutubeBook updateMilestone(
            VideoCreationMilestone videoCreationMilestone,
            Long bookNumber,
            Long startChapter,
            Long endChapter) {
        log.info("updateMilestones");
        log.info("bookNumber: " + bookNumber);
        log.info("startChapter: " + startChapter);
        log.info("endChapter: " + endChapter);
        log.info("videoCreationMilestone: " + videoCreationMilestone);
        paymentForTranslatedYoutubeBookDAO.updatePaymentMilestone(videoCreationMilestone,
                bookNumber, startChapter, endChapter);
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = paymentForTranslatedYoutubeBookDAO.getPaymentByBookNumberAndStartChapterAndEndChapter(
                bookNumber,
                startChapter,
                endChapter
        );
        log.info("payment for translated youtube book: " + paymentForTranslatedYoutubeBook);
        return paymentForTranslatedYoutubeBook;
    }

}
