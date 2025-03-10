package book.data.service.sqlmodel.payment;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "PaymentForTranslatedYoutubeBook")
@Table(name = "paymentForTranslatedYoutubeBook", uniqueConstraints = {
    @UniqueConstraint(
        columnNames={"dedupeId"}
    ),
    @UniqueConstraint(
        columnNames = {"bookNumber", "startChapter", "endChapter"}
    )
})
@Setter
@Getter
public class PaymentForTranslatedYoutubeBook implements Serializable {
    // have client generate uuid and send it along with the request. It can act as a deduplication token
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentNumber;

    @Column(nullable = false)
    private String dedupeId;

    @Column(nullable=false)
    private String translatedYoutubeBookStatus;

    @Enumerated(EnumType.STRING)
    private VideoCreationMilestone videoCreationMilestone;

    @Column(nullable = false)
    private Long tokenTransactionNumber;

    @Column(nullable = false)
    private Long bookNumber;

    @Column(nullable = false)
    private Long startChapter;
    @Column(nullable = false)
    private Long endChapter;

    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private Long createdEpochMilli;

    public static PaymentForTranslatedYoutubeBook buildPaymentForTranslatedYoutubeBookWithoutNumber(
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
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = new PaymentForTranslatedYoutubeBook();
        paymentForTranslatedYoutubeBook.setDedupeId(dedupeId);
        paymentForTranslatedYoutubeBook.setTranslatedYoutubeBookStatus(translatedYoutubeBookStatus);
        paymentForTranslatedYoutubeBook.setVideoCreationMilestone(videoCreationMilestone);
        paymentForTranslatedYoutubeBook.setTokenTransactionNumber(tokenTransactionNumber);
        paymentForTranslatedYoutubeBook.setBookNumber(bookNumber);
        paymentForTranslatedYoutubeBook.setStartChapter(startChapter);
        paymentForTranslatedYoutubeBook.setEndChapter(endChapter);
        paymentForTranslatedYoutubeBook.setCreatedBy(createdBy);
        paymentForTranslatedYoutubeBook.setCreatedEpochMilli(createdEpochMilli);
        return paymentForTranslatedYoutubeBook;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PaymentForTranslatedYoutubeBook)) {
            return false;
        }
        PaymentForTranslatedYoutubeBook paymentForTranslatedYoutubeBook = (PaymentForTranslatedYoutubeBook) o;
        return paymentForTranslatedYoutubeBook.getPaymentNumber().equals(this.getPaymentNumber());
    }

    public int hashCode() {
        return this.getPaymentNumber().hashCode();
    }

}
