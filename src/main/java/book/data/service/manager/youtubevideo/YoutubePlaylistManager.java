package book.data.service.manager.youtubevideo;


import book.data.service.dao.youtubevideo.YouTubeVideoDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YoutubePlaylistManager {
    private YouTubeVideoDAO youTubeVideoDAO;
    private YouTubePlayListService youTubePlayListService;

    @Autowired
    public YoutubePlaylistManager(
            YouTubeVideoDAO youTubeVideoDAO,
            YouTubePlayListService youTubePlayListService
    ) {
        this.youTubeVideoDAO = youTubeVideoDAO;
        this.youTubePlayListService = youTubePlayListService;
    }
}
