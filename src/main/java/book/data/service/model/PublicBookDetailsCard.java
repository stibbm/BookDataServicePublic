package book.data.service.model;

import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicBookDetailsCard implements Serializable {
    private Book book;
    private Long maxVideo;
    private Long maxTextChapter;
    private Boolean hasEnoughChaptersForNextVideo;
    private NextVideo nextVideo;
    private List<YouTubeVideo> youTubeVideoList;
    private List<Long> textChapterNumberList;
}
