package book.data.service.service.youtube;


import book.data.service.model.VideoData;
import book.data.service.model.YouTubeVideo;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class YoutubeUploadService {

    private YouTube youtubeService;

    @Autowired
    public YoutubeUploadService(YouTube youtubeService) {
        this.youtubeService = youtubeService;
    }

    public YouTubeVideo uploadVideo(String title, String description, List<String> tags,
                                    VideoData videoData)
            throws IOException {
        log.info("Uploading video with title: {}, description: {}, tags: {}", title, description, tags);
        Video video = new Video();
        VideoStatus videoStatus = new VideoStatus();
        videoStatus.setPrivacyStatus("public");
        video.setStatus(videoStatus);

        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(title);
        snippet.setDescription(description);
        snippet.setTags(tags);
        video.setSnippet(snippet);

        byte[] videoBytes = videoData.getVideoBytes();
        String fileType = "video/" + videoData.getVideoFileType();

        ByteArrayContent mediaContent = new ByteArrayContent(fileType, videoBytes);
        YouTube.Videos.Insert videoInsert = youtubeService.videos()
                .insert("snippet,statistics,status", video, mediaContent);
        Video uploadedVideo = videoInsert.execute();
        String videoUrl = YOUTUBE_VIDEO_URL_TEMPLATE.replace("{videoId}", uploadedVideo.getId());
        YouTubeVideo youTubeVideo = YouTubeVideo.builder()
                .videoId(uploadedVideo.getId())
                .title(uploadedVideo.getSnippet().getTitle())
                .description(uploadedVideo.getSnippet().getDescription())
                .privacyStatus(uploadedVideo.getStatus().getPrivacyStatus())
                .videoUrl(videoUrl)
                .tags(uploadedVideo.getSnippet().getTags())
                .build();
        log.info("youTubeVideo: " + youTubeVideo);
        return youTubeVideo;
    }

}
