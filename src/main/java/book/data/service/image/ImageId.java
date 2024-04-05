package book.data.service.image;

import book.data.service.chapter.Chapter;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Embeddable
public class ImageId implements Serializable {
    private Long imageNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
        @JoinColumn(name = "book_id"),
        @JoinColumn(name = "chapter_number")
    })
    private Chapter chapter;

    public Chapter getChapter() {
        return this.chapter;
    }

    public Long getImageNumber() {
        return this.imageNumber;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setImageNumber(Long imageNumber) {
        this.imageNumber = imageNumber;
    }

    public ImageId(Long imageNumber, Chapter chapter) {
        this.imageNumber = imageNumber;
        this.chapter = chapter;
    }

    public ImageId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageId imageId = (ImageId) o;
        return Objects.equals(imageId.getImageNumber(), imageNumber)
            && Objects.equals(imageId.getChapter(), chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imageNumber", imageNumber);
        jsonObject.put("chapter", chapter.getJson());
        return jsonObject;
    }

    @Override
    public String toString() {
        return getJson().toString();
    }

}
