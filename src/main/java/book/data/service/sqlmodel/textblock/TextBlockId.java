package book.data.service.sqlmodel.textblock;

import book.data.service.sqlmodel.chapter.Chapter;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextBlockId implements Serializable {
    private Long textBlockNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "book_number"),
        @JoinColumn(name = "chapter_number")
    })
    private Chapter chapter;

    @Override
    public int hashCode() {return Objects.hash(this.chapter, this.textBlockNumber);}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextBlockId that = (TextBlockId) o;
        return this.chapter.equals(that.getChapter()) && this.textBlockNumber.equals(that.getTextBlockNumber());
    }

    @Override
    public String toString() {
        return "TextBlockId{" +
            "chapter=" + chapter +
            ", textBlockNumber=" + textBlockNumber +
            '}';
    }
}
