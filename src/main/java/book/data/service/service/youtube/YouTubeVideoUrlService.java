package book.data.service.service.youtube;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static book.data.service.constants.Constants.YOUTUBE_VIDEO_URL_TEMPLATE;

@Slf4j
@Service
public class YouTubeVideoUrlService {
    public String getYouTubeVideoUrl(String youTubeVideoId) {
        log.info("youTubeVideoId: " + youTubeVideoId);
        String url = YOUTUBE_VIDEO_URL_TEMPLATE.replace("{youTubeVideoId}", youTubeVideoId);
        return url;
    }
}
