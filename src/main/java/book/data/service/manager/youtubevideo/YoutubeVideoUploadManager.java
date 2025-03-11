package book.data.service.manager.youtubevideo;


import book.data.service.service.youtube.YouTubePlayListService;
import book.data.service.service.youtube.YoutubeVideoDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YoutubeVideoUploadManager {

    private YouTubeVideoManager youTubeVideoManager;
    private YoutubeVideoDetailsService youtubeVideoDetailsService;
    private YouTubePlayListService youTubePlayListService;

    @Autowired
    public YoutubeVideoUploadManager(
            YouTubeVideoManager youTubeVideoManager,
            YoutubeVideoDetailsService youtubeVideoDetailsService,
            YouTubePlayListService youTubePlayListService
    ) {
        this.youtubeVideoDetailsService = youtubeVideoDetailsService;
        this.youTubeVideoManager = youTubeVideoManager;
        this.youTubePlayListService = youTubePlayListService;
    }
}
