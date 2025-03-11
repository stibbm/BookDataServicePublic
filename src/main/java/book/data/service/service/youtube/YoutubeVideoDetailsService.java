package book.data.service.service.youtube;

import book.data.service.model.YoutubeVideoInfo;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class YoutubeVideoDetailsService {
    private YouTube youtubeService;

    @Autowired
    public YoutubeVideoDetailsService(YouTube youtubeService) {
        this.youtubeService = youtubeService;
    }

    public YoutubeVideoInfo getYoutubeVideoDetailsByVideoId(String videoId) throws IOException {
        YouTube.Videos.List request = youtubeService.videos()
                .list("snippet")
                .setId(videoId)
                .setKey(YOUTUBE_GOOGLE_API_KEY);
        VideoListResponse response = request.execute();
        List<Video> videoList = response.getItems();
        if (videoList == null || videoList.size() == 0) {
            throw new RuntimeException("Failed to lookup youtube video by videoId using youtube api");
        }
        Video video = videoList.get(0);
        VideoSnippet snippet = video.getSnippet();
        String title = snippet.getTitle();
        List<String> tagList = snippet.getTags();
        String description = snippet.getDescription();
        String categoryId = snippet.getCategoryId();
        YoutubeVideoInfo youtubeVideoInfo = YoutubeVideoInfo.builder()
                .videoId(videoId)
                .title(title)
                .description(description)
                .tagList(tagList)
                .categoryId(categoryId)
                .build();
        return youtubeVideoInfo;
    }
}