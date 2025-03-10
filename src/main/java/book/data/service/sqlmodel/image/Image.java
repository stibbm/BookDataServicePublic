package book.data.service.sqlmodel.image;

import java.io.Serializable;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Image")
@Table(name = "image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image implements Serializable {
    @EmbeddedId
    private ImageId imageId;

    private String s3Key;
    private String s3Bucket;

    private String relativeImageUrl;
    private String createdBy;
    private String fileType;
    private Long createdEpochMilli;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return Objects.equals(image.getImageId(), imageId)
                && Objects.equals(image.getS3Key(), s3Key)
                && Objects.equals(image.getS3Bucket(), s3Bucket)
                && Objects.equals(image.getRelativeImageUrl(), relativeImageUrl)
                && Objects.equals(image.getCreatedBy(), createdBy)
                && Objects.equals(image.getFileType(), fileType)
                && Objects.equals(image.getCreatedEpochMilli(), createdEpochMilli);
    }

}
