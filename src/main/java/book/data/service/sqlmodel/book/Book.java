package book.data.service.sqlmodel.book;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.time.LocalDateTime;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity(name = "Book")
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(
                columnNames={"bookName", "createdBy"}
        ),
        @UniqueConstraint(
                columnNames={"bookName"}
        )
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {


    public static Book createBookWithoutBookId(
            String bookName,
            String createdBy,
            String bookDescription,
            String bookLanguage,
            Long bookViews,
            String bookThumbnail,
            String bookThumbnailS3Key,
            String bookThumbnailS3Bucket,
            Set<String> bookTags,
            Long createdEpochMilli
    ) {
        Book book = new Book();
        book.setBookName(bookName);
        book.setCreatedBy(createdBy);
        book.setBookDescription(bookDescription);
        book.setBookLanguage(bookLanguage);
        book.setBookViews(bookViews);
        book.setBookThumbnail(bookThumbnail);
        book.setBookThumbnailS3Key(bookThumbnailS3Key);
        book.setBookThumbnailS3Bucket(bookThumbnailS3Bucket);
        book.setBookTags(bookTags);
        book.setCreatedEpochMilli(createdEpochMilli);
        return book;
    }

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
                && Objects.equals(bookThumbnailS3Key, book.getBookThumbnailS3Key())
                && Objects.equals(bookThumbnailS3Bucket, book.getBookThumbnailS3Bucket())
                && Objects.equals(bookTags, book.getBookTags())
                && Objects.equals(createdEpochMilli, book.getCreatedEpochMilli());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookNumber);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookNumber;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String createdBy;

    @Lob
    @Column(columnDefinition = "LONGBLOB NOT NULL", nullable = false)
    private String bookDescription;

    @Column(nullable = false)
    private String bookLanguage;

    @Column(nullable = false)
    private Long bookViews;

    @Column
    private String bookThumbnail;

    @Column
    private String bookThumbnailS3Key;

    @Column
    private String bookThumbnailS3Bucket;

    @ElementCollection
    @CollectionTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_tag_id"))
    private Set<String> bookTags;

    @Column(nullable = false)
    private Long createdEpochMilli;
}
