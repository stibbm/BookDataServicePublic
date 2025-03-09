package book.data.service.sqlmodel.chapter;

import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import book.data.service.sqlmodel.book.Book;
import java.io.Serializable;

@ToString
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChapterId implements Serializable {
    private Long chapterNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_number")
    private Book book;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChapterId chapterId = (ChapterId) o;
        return Objects.equals(chapterId.getChapterNumber(), chapterNumber)
                && Objects.equals(chapterId.getBook(), book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterNumber, book);
    }
}
