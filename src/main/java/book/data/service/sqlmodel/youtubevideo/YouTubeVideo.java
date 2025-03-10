package book.data.service.sqlmodel.youtubevideo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity(name = "YouTubeVideo")
@Table(name = "youTubeVideo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class YouTubeVideo implements Serializable {
    @Id
    private String youtubeVideoId;
    @Column(nullable = false)
    private Long bookNumber;
    @Column(nullable = false)
    private Long startChapterNumber;
    @Column(nullable = false)
    private Long endChapterNumber;
    @Column(nullable = false)
    private String youtubeVideoUrl;

    @Column(nullable = false)
    private String requestedBy;

    @Column(nullable = false)
    private Long createdEpochMilli;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass()!=o.getClass()) {
            return false;
        }
        YouTubeVideo youTubeVideo = (YouTubeVideo) o;
        return Objects.equals(youTubeVideo.getYoutubeVideoId(), youtubeVideoId)
            && Objects.equals(youTubeVideo.getBookNumber(), bookNumber)
            && Objects.equals(youTubeVideo.getStartChapterNumber(), startChapterNumber)
            && Objects.equals(youTubeVideo.getEndChapterNumber(), endChapterNumber)
            && Objects.equals(youTubeVideo.getYoutubeVideoUrl(), youtubeVideoUrl)
            && Objects.equals(youTubeVideo.getRequestedBy(), requestedBy)
            && Objects.equals(youTubeVideo.getCreatedEpochMilli(), createdEpochMilli);
    }

    @Override
    public int hashCode() {
        return Objects.hash(youtubeVideoId);
    }

    public static YouTubeVideo buildYoutubeVideo(
        String youTubeVideoId,
        Long bookNumber,
        Long startChapterNumber,
        Long endChapterNumber,
        String youTubeVideoUrl,
        String requestedBy,
        Long createdEpochMilli
    ) {
        YouTubeVideo youTubeVideo = new YouTubeVideo();
        youTubeVideo.setYoutubeVideoId(youTubeVideoId);
        youTubeVideo.setBookNumber(bookNumber);
        youTubeVideo.setStartChapterNumber(startChapterNumber);
        youTubeVideo.setEndChapterNumber(endChapterNumber);
        youTubeVideo.setYoutubeVideoUrl(youTubeVideoUrl);
        youTubeVideo.setRequestedBy(requestedBy);
        youTubeVideo.setCreatedEpochMilli(createdEpochMilli);
        return youTubeVideo;
    }

}

