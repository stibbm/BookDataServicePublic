package book.data.service.constants.videodetailstemplates;


import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class VideoDetailsTemplates {

    public static final List<String> TAG_LIST = ImmutableList.of(
            "audiobook", "webnovel", "lightnovel"
    );

    public static Map<String, Series> GET_NAME_TO_SERIES_MAP() {
        Map<String, Series> map = new HashMap<>();
        map.put(Series.TEST_SERIES.toString().replace("_", " "), Series.TEST_SERIES);
        //map.put(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY.toString().replace("_", " "), Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY);
        //map.put(Series.GENIUS_MAGE_TAKES_MEDICINE.toString().replace("_", " "), Series.GENIUS_MAGE_TAKES_MEDICINE);
        //map.put(Series.REGRESSION_OF_MAX_LEVEL_PLAYER.toString().replace("_", " "), Series.REGRESSION_OF_MAX_LEVEL_PLAYER);
        return map;
    }

    public static Map<String, VideoDetailsTemplate> GET_VIDEO_DETAILS_TEMPLATE_MAP() {
        Map<String, VideoDetailsTemplate> videoDetailsTemplateMap = new HashMap<>();

        videoDetailsTemplateMap.put(
                Series.TEST_SERIES.toString(),
                VideoDetailsTemplate.builder()
                        .titleTemplate("Test Series {startChapter}-{endChapter} | Audiobook | Web Novel")
                        .descriptionTemplate("Test Description")
                        .tags(TAG_LIST)
                        .playListId(YoutubePlayLists.getPlayListMapBySeriesString().get(
                                Series.TEST_SERIES.toString()
                        ).getPlayListId())
                        .build()
        );

        /*videoDetailsTemplateMap.put(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY.toString(),
            VideoDetailsTemplate.builder()
                .titleTemplate(
                    "Surviving as a Mage in a Magic Academy {startChapter}-{endChapter} | Audiobook | Web Novel")
                .descriptionTemplate(
                    "#audiobook  #webnovel #lightnovel \nSurviving as a Mage in a Magic Academy {startChapter}-{endChapter}  | Audiobook | Web Novel")
                .tags(TAG_LIST)
                .playListId(YoutubePlayLists.getPlayListMapBySeriesString().get(
                    Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY.toString()).getPlayListId())
                .build());

        videoDetailsTemplateMap.put(Series.GENIUS_MAGE_TAKES_MEDICINE.toString(),
            VideoDetailsTemplate.builder()
                .titleTemplate(
                    "Genius Mage Takes Medicine {startChapter}-{endChapter} | Audiobook | Web Novel | Light Novel")
                .descriptionTemplate(
                    "#audiobook #webnovel #lightnovel \nGenius Mage Takes Medicine {startChapter}-{endChapter} | Audiobook | Web Novel | Light Novel")
                .tags(TAG_LIST)
                .playListId(
                    YoutubePlayLists.getPlayListMapBySeriesString()
                        .get(Series.GENIUS_MAGE_TAKES_MEDICINE.toString()).getPlayListId()
                )
                .build());

        videoDetailsTemplateMap.put(Series.REGRESSION_OF_MAX_LEVEL_PLAYER.toString(),
            VideoDetailsTemplate.builder()
                .titleTemplate(
                    "Max Level Players 100th Regression Chapter {startChapter}-{endChapter} | Audiobook | Web Novel")
                .descriptionTemplate("#audiobook  #webnovel #lightnovel \n"
                    + "Max Level Players 100th Regression Chapter {startChapter}-{endChapter} | Audiobook | Web Novel")
                .tags(TAG_LIST)
                .playListId(YoutubePlayLists.getPlayListMapBySeriesString()
                    .get(Series.REGRESSION_OF_MAX_LEVEL_PLAYER.toString()).getPlayListId())
                .build());*/

        return videoDetailsTemplateMap;
    }


    public YoutubeVideoDetailsTemplate getSeriesTemplateAsYoutubeVideoDetailsTemplate(Series series) {
        log.info("Get Series Template As Youtube Video Details Template");
        log.info(series.toString());
        VideoDetailsTemplate videoDetailsTemplate = GET_VIDEO_DETAILS_TEMPLATE_MAP().get(
                series.toString());
        Set<String> tagSet = new HashSet<>(videoDetailsTemplate.getTags());
        YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate = new YoutubeVideoDetailsTemplate();
        youtubeVideoDetailsTemplate.setTitleTemplate(videoDetailsTemplate.getTitleTemplate());
        youtubeVideoDetailsTemplate.setDescriptionTemplate(
                videoDetailsTemplate.getDescriptionTemplate());
        youtubeVideoDetailsTemplate.setVideoTags(tagSet);
        return youtubeVideoDetailsTemplate;
    }


    public static String getPlaylistIdBySeriesString(String seriesString) {
        return GET_VIDEO_DETAILS_TEMPLATE_MAP().get(seriesString).getPlayListId();
    }

}
