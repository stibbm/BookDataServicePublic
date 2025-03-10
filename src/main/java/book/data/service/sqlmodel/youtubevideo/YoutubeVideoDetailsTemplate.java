package book.data.service.sqlmodel.youtubevideo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "YoutubeVideoDetailsTemplate")
@Table(name = "youtubeVideoDetailsTemplate")
public class YoutubeVideoDetailsTemplate implements Serializable {

    @Id
    private Long bookNumber;

    @Column(nullable = false)
    private String titleTemplate;

    @Column(nullable = false)
    private String descriptionTemplate;

    @ElementCollection
    @CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_tag_id"))
    private Set<String> videoTags;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass()!=o.getClass()) {
            return false;
        }
        YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate = (YoutubeVideoDetailsTemplate) o;
        return youtubeVideoDetailsTemplate.getBookNumber().equals(bookNumber)
            && youtubeVideoDetailsTemplate.getTitleTemplate().equals(titleTemplate)
            && youtubeVideoDetailsTemplate.getDescriptionTemplate().equals(descriptionTemplate)
            && Objects.equals(youtubeVideoDetailsTemplate.getVideoTags(), videoTags);
    }

    public int hashCode() {
        return bookNumber.hashCode();
    }
}
