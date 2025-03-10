package book.data.service.sqlmodel.image;

import java.io.Serializable;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Image")
@Table(name = "image")
public class Image implements Serializable {
    @EmbeddedId
    private ImageId imageId;

    private String s3Key;
    private String s3Bucket;

    private String relativeImageUrl;
    private String createdBy;

    public ImageId getImageId() {
        return this.imageId;
    }
    public String getS3Key() {
        return this.s3Key;
    }
    public String getS3Bucket() {
        return this.s3Bucket;
    }
    public String getRelativeImageUrl() {
        return this.relativeImageUrl;
    }

    public void setImageId(ImageId imageId) {
        this.imageId = imageId;
    }
    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }
    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }
    public void setRelativeImageUrl(String relativeImageUrl) {
        this.relativeImageUrl = relativeImageUrl;
    }
    public String getCreatedBy() {return this.createdBy;}
    public void setCreatedBy(String createdBy){this.createdBy = createdBy;}

    public Image(
        ImageId imageId,
        String s3Key,
        String s3Bucket,
        String relativeImageUrl,
        String createdBy
    ) {
        this.imageId = imageId;
        this.s3Key = s3Key;
        this.s3Bucket = s3Bucket;
        this.relativeImageUrl = relativeImageUrl;
        this.createdBy = createdBy;
    }

    public Image() {
    }

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
            && Objects.equals(image.getCreatedBy(), createdBy);
    }

}
