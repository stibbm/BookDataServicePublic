package book.data.service.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "Book")
@Table(name = "book", uniqueConstraints = {
    @UniqueConstraint(
        columnNames={"bookName", "createdBy"}
    )
})
public class Book implements Serializable {

    public Long getBookNumber() {return this.bookNumber;}
    public void setBookNumber(Long bookNumber) {this.bookNumber = bookNumber;}
    public String getBookName() {
        return this.bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getCreatedBy() {return this.createdBy;}
    public void setCreatedBy(String bookCreatedBy) {this.createdBy = bookCreatedBy;}
    public String getBookDescription() {
        return this.bookDescription;
    }
    public void setBookDescription(String bookDescription) {this.bookDescription = bookDescription;}
    public String getBookLanguage() {
        return this.bookLanguage;
    }
    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }
    public Long getBookViews() {
        return this.bookViews;
    }
    public void setBookViews(Long bookViews) {
        this.bookViews = bookViews;
    }
    public String getBookThumbnail() {
        return this.bookThumbnail;
    }
    public void setBookThumbnail(String bookThumbnail) {
        this.bookThumbnail = bookThumbnail;
    }
    public Set<String> getBookTags() {
        return this.bookTags;
    }
    public void setBookTags(Set<String> bookTags) {
        this.bookTags = bookTags;
    }

    public Book(
        Long bookNumber,
        String bookName,
        String createdBy,
        String bookDescription,
        String bookLanguage,
        Long bookViews,
        String bookThumbnail,
        Set<String> bookTags
    ) {
        this.bookNumber = bookNumber;
        this.bookName = bookName;
        this.createdBy = createdBy;
        this.bookDescription = bookDescription;
        this.bookLanguage = bookLanguage;
        this.bookViews = bookViews;
        this.bookThumbnail = bookThumbnail;
        this.bookTags = bookTags;
    }

    public static Book createBookWithoutBookId(
        String bookName,
        String createdBy,
        String bookDescription,
        String bookLanguage,
        Long bookViews,
        String bookThumbnail,
        Set<String> bookTags
    ) {
        Book book = new Book();
        book.setBookName(bookName);
        book.setCreatedBy(createdBy);
        book.setBookDescription(bookDescription);
        book.setBookLanguage(bookLanguage);
        book.setBookViews(bookViews);
        book.setBookThumbnail(bookThumbnail);
        book.setBookTags(bookTags);
        return book;
    }

    public Book() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book)o;
        return Objects.equals(bookNumber, book.getBookNumber())
            && Objects.equals(bookName, book.getBookName())
            && Objects.equals(createdBy, book.getCreatedBy())
            && Objects.equals(bookDescription, book.getBookDescription())
            && Objects.equals(bookViews, book.getBookViews())
            && Objects.equals(bookLanguage, book.getBookLanguage())
            && Objects.equals(bookThumbnail, book.getBookThumbnail())
            && Objects.equals(bookTags, book.getBookTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookNumber);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookNumber;

    @Column
    private String bookName;

    @Column
    private String createdBy;

    @Lob
    @Column(columnDefinition = "LONGBLOB NOT NULL")
    private String bookDescription;

    @Column
    private String bookLanguage;

    @Column
    private Long bookViews;

    @Column
    private String bookThumbnail;

    @ElementCollection
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_tag_id"))
    private Set<String> bookTags;
}
