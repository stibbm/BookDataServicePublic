package book.data.service.sqlmodel.chapter;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

@ToString
@Entity(name = "Chapter")
@Table(name = "chapter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements Serializable {

    @EmbeddedId
    private ChapterId chapterId;
    @Column(nullable = false)
    private String chapterName;
    @Column(nullable = false)
    private Long chapterViews;
    @Column(nullable = false)
    private String createdBy;
    @Column(nullable = false)
    private Long createdEpochMilli;
    @Enumerated(EnumType.STRING)
    private LockStatus lockStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Chapter chapter = (Chapter) o;
        return Objects.equals(chapter.getChapterId(), chapterId)
                && Objects.equals(chapter.getChapterName(), chapterName)
                && Objects.equals(chapter.getChapterViews(), chapterViews)
                && Objects.equals(chapter.getCreatedBy(), createdBy)
                && Objects.equals(chapter.getCreatedEpochMilli(), createdEpochMilli)
                && Objects.equals(chapter.getLockStatus(), lockStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                chapterId
        );
    }
}
