package book.data.service.activity.populate.youtube;

import static matt.book.data.service.constants.Routes.ALL_ORIGINS;
import static matt.book.data.service.constants.Routes.POPULATE_YOUTUBE_VIDEO_DATA_BY_PLAYLIST_ID_AND_BOOK_NUMBER;

import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import matt.book.data.service.firebase.FirebaseService;
import matt.book.data.service.manager.youtubevideo.YoutubeVideosPopulateDataForPlaylistManager;
import matt.book.data.service.messages.youtube.PopulateYoutubeVideoDataByPlaylistIdAndBookNumberRequest;
import matt.book.data.service.messages.youtube.PopulateYoutubeVideoDataByPlaylistIdAndBookNumberResponse;
import matt.book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PopulateYoutubeVideoDataByVideoIdActivity {
    private YoutubeVideosPopulateDataForPlaylistManager youtubeVideosPopulateDataForPlaylistManager;
    private FirebaseService firebaseService;

    @Autowired
    public PopulateYoutubeVideoDataByVideoIdActivity(
        YoutubeVideosPopulateDataForPlaylistManager youtubeVideosPopulateDataForPlaylistManager,
        FirebaseService firebaseService
    ) {
        this.youtubeVideosPopulateDataForPlaylistManager = youtubeVideosPopulateDataForPlaylistManager;
        this.firebaseService = firebaseService;
    }

    @PostMapping(POPULATE_YOUTUBE_VIDEO_DATA_BY_PLAYLIST_ID_AND_BOOK_NUMBER)
    @CrossOrigin(ALL_ORIGINS)
    public @ResponseBody PopulateYoutubeVideoDataByPlaylistIdAndBookNumberResponse populateYoutubeVideoDataByPlaylistIdAndBookNumber(
        @RequestBody PopulateYoutubeVideoDataByPlaylistIdAndBookNumberRequest populateYoutubeVideoDataByPlaylistIdAndBookNumberRequest,
        @RequestHeader("Authorization") String authToken
    ) throws IOException {
        log.info("PATH: " + POPULATE_YOUTUBE_VIDEO_DATA_BY_PLAYLIST_ID_AND_BOOK_NUMBER);
        String email = firebaseService.getEmail(authToken);
        List<YouTubeVideo> youtubeVideoList = youtubeVideosPopulateDataForPlaylistManager.createYoutubeVideosByPlaylistId(
            Long.parseLong(populateYoutubeVideoDataByPlaylistIdAndBookNumberRequest.getBookNumber()),
            populateYoutubeVideoDataByPlaylistIdAndBookNumberRequest.getPlayListId(), email
        );
        PopulateYoutubeVideoDataByPlaylistIdAndBookNumberResponse response =
            PopulateYoutubeVideoDataByPlaylistIdAndBookNumberResponse.builder()
                .youtubeVideoList(youtubeVideoList)
                .playlistId(populateYoutubeVideoDataByPlaylistIdAndBookNumberRequest.getPlayListId())
                .build();
        return response;
    }
}
