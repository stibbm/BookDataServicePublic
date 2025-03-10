package book.data.service.sqlmodel.image;

import java.io.Serializable;
import java.util.Objects;

import book.data.service.sqlmodel.chapter.Chapter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ImageId implements Serializable {
    private Long imageNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "book_id"),
            @JoinColumn(name = "chapter_number")
    })
    private Chapter chapter;

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
        jsonObject.put("chapter", chapter.toString());
        return jsonObject;
    }

    @Override
    public String toString() {
        return getJson().toString();
    }

}
