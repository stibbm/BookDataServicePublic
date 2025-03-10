package book.data.service.sqlmodel.bookstatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name="BookStatus")
@Table(name="bookStatus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStatus implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long bookStatusNumber;
    @Column(nullable = false)
    private Long bookNumber;
    @Column(nullable = false)
    private Long maxChapterGeneratedVideo;
    @Column(nullable = false)
    private Long maxChapterRawsAvailable;
    @Column(nullable = false)
    private Long maxChapterEnglishAvailable;
    @Column(nullable = false)
    private String createdBy;

    public static BookStatus buildBookStatusWithoutBookStatusNumber(
        Long bookNumber,
        Long maxChapterGeneratedVideo,
        Long maxChapterRawsAvailable,
        Long maxChapterEnglishAvailable,
        String createdBy
    ) {
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookNumber(bookNumber);
        bookStatus.setMaxChapterGeneratedVideo(maxChapterGeneratedVideo);
        bookStatus.setMaxChapterRawsAvailable(maxChapterRawsAvailable);
        bookStatus.setMaxChapterEnglishAvailable(maxChapterEnglishAvailable);
        bookStatus.setCreatedBy(createdBy);
        return bookStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookStatus bookStatus = (BookStatus) o;
        return Objects.equals(bookStatusNumber, bookStatus.getBookStatusNumber())
            && Objects.equals(bookNumber, bookStatus.getBookNumber())
            && Objects.equals(maxChapterGeneratedVideo, bookStatus.getMaxChapterGeneratedVideo())
            && Objects.equals(maxChapterRawsAvailable, bookStatus.getMaxChapterRawsAvailable())
            && Objects.equals(maxChapterEnglishAvailable, bookStatus.getMaxChapterEnglishAvailable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookStatusNumber);
    }

}
