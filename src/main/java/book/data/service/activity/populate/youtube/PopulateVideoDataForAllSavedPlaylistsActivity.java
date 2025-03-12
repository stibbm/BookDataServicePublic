package book.data.service.activity.populate.youtube;

import static book.data.service.constants.Routes.ALL_ORIGINS;
import static book.data.service.constants.Routes.POPULATE_VIDEO_DATA_FOR_ALL_SAVED_PLAYLISTS;

import book.data.service.firebase.FirebaseService;
import book.data.service.manager.youtubevideo.YoutubeVideosPopulateDataForPlaylistManager;
import book.data.service.message.populate.PopulateVideoDataForAllSavedPlaylistsResponse;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PopulateVideoDataForAllSavedPlaylistsActivity {
    private YoutubeVideosPopulateDataForPlaylistManager youtubeVideosPopulateDataForPlaylistManager;
    private FirebaseService firebaseService;

    @Autowired
    public PopulateVideoDataForAllSavedPlaylistsActivity(
        YoutubeVideosPopulateDataForPlaylistManager youtubeVideosPopulateDataForPlaylistManager,
        FirebaseService firebaseService
    ) {
        this.youtubeVideosPopulateDataForPlaylistManager = youtubeVideosPopulateDataForPlaylistManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(POPULATE_VIDEO_DATA_FOR_ALL_SAVED_PLAYLISTS)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody PopulateVideoDataForAllSavedPlaylistsResponse act(
        @RequestHeader("Authorization") String authToken
    ) throws IOException, URISyntaxException {
        log.info("populateVideoDataForAllSavedPlaylists");
        String email = firebaseService.getEmail(authToken);
        List<List<YouTubeVideo>> youtubeVideoListList = youtubeVideosPopulateDataForPlaylistManager.createYoutubeVideoPlaylists(email);
        PopulateVideoDataForAllSavedPlaylistsResponse populateVideoDataForAllSavedPlaylistsResponse =
            PopulateVideoDataForAllSavedPlaylistsResponse.builder()
                .youtubePlaylistList(youtubeVideoListList)
                .done(true)
                .build();
        return populateVideoDataForAllSavedPlaylistsResponse;
    }
}
