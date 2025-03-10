package book.data.service.sqlmodel.textblock;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "TextBlock")
@Table(name = "textBlock")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextBlock implements Serializable {
    @EmbeddedId
    private TextBlockId textBlockId;
    @Lob
    @Column(columnDefinition = "LONGBLOB NOT NULL")
    private String textBlockText;
    private String createdBy;
    private Long createdEpochMilli;

    @Override
    public int hashCode() {return this.textBlockId.hashCode();}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TextBlock that = (TextBlock) o;
        return this.textBlockId.equals(that.getTextBlockId());
    }

    @Override
    public String toString() {
        return "TextBlock{" +
            "textBlockId=" + textBlockId +
            ", textBlockText='" + textBlockText + '\'' +
            '}';
    }

}
