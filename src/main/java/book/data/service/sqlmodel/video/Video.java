package book.data.service.sqlmodel.video;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Video")
@Table(name = "video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {

    @EmbeddedId
    private VideoId videoId;

    private String s3Key;
    private String s3Bucket;
    private String relativeUrl;
    private String createdBy;
    private String fileType;
    private Long createdEpochMilli;

    @Override
    public int hashCode() {
        return this.videoId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Video that = (Video) o;
        return this.videoId.equals(that.getVideoId());
    }

    @Override
    public String toString() {
        return "Video{" +
            "videoId=" + videoId +
            ", s3Key='" + s3Key + '\'' +
            ", s3Bucket='" + s3Bucket + '\'' +
            ", relativeUrl='" + relativeUrl + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdEpochMilli=" + createdEpochMilli +
            '}';
    }

}
