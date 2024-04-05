package book.data.service.chapter;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.json.JSONObject;

@Entity(name = "Chapter")
@Table(name = "chapter")
public class Chapter implements Serializable {

    @EmbeddedId
    private ChapterId chapterId;
    private String chapterName;
    private Long chapterViews;
    private String createdBy;

    public ChapterId getChapterId() {
        return this.chapterId;
    }
    public void setChapterId(ChapterId chapterId) {
        this.chapterId = chapterId;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public Long getChapterViews() {
        return this.chapterViews;
    }
    public void setChapterViews(Long chapterViews) {
        this.chapterViews = chapterViews;
    }
    public String getCreatedBy() {return this.createdBy;}
    public void setCreatedBy(String createdBy) {this.createdBy = createdBy;}

    public Chapter(ChapterId chapterId, String chapterName, Long chapterViews, String createdBy) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.chapterViews = chapterViews;
        this.createdBy = createdBy;
    }

    public Chapter() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Chapter chapter = (Chapter)o;
        return Objects.equals(chapter.getChapterId(), chapterId)
            && Objects.equals(chapter.getChapterName(), chapterName)
            && Objects.equals(chapter.getChapterViews(), chapterViews)
            && Objects.equals(chapter.getCreatedBy(), createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            chapterId
        );
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chapterId", chapterId.getJson());
        jsonObject.put("chapterName", chapterName);
        jsonObject.put("chapterViews", chapterViews);
        jsonObject.put("createdBy", createdBy);
        return jsonObject;
    }

    @Override
    public String toString() {
        return getJson().toString();
    }
}
