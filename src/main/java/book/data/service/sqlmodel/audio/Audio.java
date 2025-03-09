package book.data.service.sqlmodel.audio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity(name = "Audio")
@Table(name = "audio")
public class Audio implements Serializable {

    @EmbeddedId
    private AudioId audioId;

    private String s3Key;
    private String s3Bucket;

    private String relativeUrl;
    private String createdBy;
    private String fileType;
    private Long createdEpochMilli;

    public AudioId getAudioId() {
        return this.audioId;
    }

    public String getS3Key() {
        return this.s3Key;
    }

    public String getS3Bucket() {
        return this.s3Bucket;
    }

    public String getRelativeUrl() {
        return this.relativeUrl;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Long getCreatedEpochMilli() {
        return this.createdEpochMilli;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setAudioId(AudioId audioId) {
        this.audioId = audioId;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedEpochMilli(Long createdEpochMilli) {
        this.createdEpochMilli = createdEpochMilli;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Audio(
            AudioId audioId,
            String s3Key,
            String s3Bucket,
            String relativeUrl,
            String createdBy,
            String fileType,
            Long createdEpochMilli
    ) {
        this.audioId = audioId;
        this.s3Key = s3Key;
        this.s3Bucket = s3Bucket;
        this.relativeUrl = relativeUrl;
        this.createdBy = createdBy;
        this.fileType = fileType;
        this.createdEpochMilli = createdEpochMilli;
    }

    public Audio() {
    }

    @Override
    public int hashCode() {
        return this.audioId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Audio that = (Audio) o;
        return this.audioId.equals(that.getAudioId());
    }

    @Override
    public String toString() {
        return "Audio{" +
                "audioId=" + audioId +
                ", s3Key='" + s3Key + '\'' +
                ", s3Bucket='" + s3Bucket + '\'' +
                ", relativeUrl='" + relativeUrl + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdEpochMilli=" + createdEpochMilli +
                '}';
    }
}
