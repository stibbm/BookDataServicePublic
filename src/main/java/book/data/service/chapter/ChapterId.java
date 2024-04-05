package book.data.service.chapter;

import book.data.service.model.Book;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;
import java.io.Serializable;

@Embeddable
public class ChapterId implements Serializable {
    private Long chapterNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    public Book getBook() {
        return this.book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public Long getChapterNumber() {
        return this.chapterNumber;
    }
    public void setChapterNumber(Long chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public ChapterId(Long chapterNumber, Book book) {
        this.chapterNumber = chapterNumber;
        this.book = book;
    }

    public ChapterId() {
    }

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

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chapterNumber", chapterNumber);
        jsonObject.put("book", book);
        return jsonObject;
    }
}
