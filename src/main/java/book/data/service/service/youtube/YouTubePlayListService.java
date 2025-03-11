package book.data.service.service.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class YouTubePlayListService {
    private YouTube youtubeService;

    @Autowired
    public YouTubePlayListService(YouTube youtubeService) {
        this.youtubeService = youtubeService;
    }

    public List<String> getVideoIdsInPlayList(String playListId) throws IOException {
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list("snippet")
                .setPlaylistId(playListId)
                .setMaxResults(50L)
                .setKey(YOUTUBE_GOOGLE_API_KEY);
        List<String> videoIdList = new ArrayList<>();
        String nextToken = "";
        do {
            request.setPageToken(nextToken);
            PlaylistItemListResponse response = request.execute();
            for (PlaylistItem item : response.getItems()) {
                videoIdList.add(item.getSnippet().getResourceId().getVideoId());
            }
            nextToken = response.getNextPageToken();
        } while (nextToken != null && !nextToken.isEmpty());
        return videoIdList;
    }

    public PlaylistItem addVideoToPlayList(String playlistId, String videoId) throws IOException {
        YouTube.PlaylistItems.Insert request = youtubeService.playlistItems()
                .insert("snippet", new PlaylistItem()
                        .setSnippet(new PlaylistItemSnippet()
                                .setPlaylistId(playlistId)
                                .setResourceId(new ResourceId()
                                        .setKind("youtube#video")
                                        .setVideoId(videoId))));
        PlaylistItem response = request.execute();
        log.info("video added to playlist: " + response.getSnippet().getTitle());
        return response;
    }


}
