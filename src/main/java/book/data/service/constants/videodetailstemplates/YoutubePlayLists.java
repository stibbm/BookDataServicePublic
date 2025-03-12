package book.data.service.constants.videodetailstemplates;

import book.data.service.model.PlayListData;
import book.data.service.service.file.FileService;

import java.util.HashMap;
import java.util.Map;

public class YoutubePlayLists {

    public static Map<String, PlayListData> getPlayListMapBySeriesString() {
        Map<String, PlayListData> map = new HashMap<>();
        //map.put(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY.toString(), SURVIVING_MAGIC_ACADEMY);
        //map.put(Series.GENIUS_MAGE_TAKES_MEDICINE.toString(), GENIUS_MAGE_TAKES_MEDICINE);
        //map.put(Series.REGRESSION_OF_MAX_LEVEL_PLAYER.toString(),
        //    MAX_LEVEL_PLAYER_100TH_REGRESSION);
        map.put(Series.TEST_SERIES.toString(), TEST_PLAYLIST);
        return map;
    }

    public static final PlayListData TEST_PLAYLIST = PlayListData.builder()
            .playListId("PLyITqO7pxxA5NEM9NfI5TsBjm7-0A0QU1")
            .bookName("Test Series")
            .series(Series.TEST_SERIES)
            .thumbnailBytes(new FileService().readFileReturnsNullOnFailure("text/thumbnails/wizard.jpg"))
            .thumbnailFileType("jpg")
            .build();

    public static final PlayListData SURVIVING_MAGIC_ACADEMY = PlayListData.builder()
            .playListId("PLFWXMbDaDUZAM6ibOXLk27YwhEGT7KtIR")
            .bookName("Surviving as a Wizard in a Magic Academy")
            .series(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY)
            .thumbnailBytes(new FileService().readFileReturnsNullOnFailure("text/thumbnails/wizard.jpg"))
            .thumbnailFileType("jpg")
            .build();
    public static final PlayListData GENIUS_MAGE_TAKES_MEDICINE = PlayListData.builder()
            .playListId("PLFWXMbDaDUZD6yaGX_YBpQ0hzivQLVip5")
            .bookName("Genius Mage Takes Medicine")
            .series(Series.GENIUS_MAGE_TAKES_MEDICINE)
            .thumbnailBytes(new FileService().readFileReturnsNullOnFailure("text/thumbnails/geniusmeds.png"))
            .thumbnailFileType("png")
            .build();

    public static final PlayListData MAX_LEVEL_PLAYER_100TH_REGRESSION = PlayListData.builder()
            .playListId("PLFWXMbDaDUZB8YTJFKuEIzqKJM-Ofkkr3")
            .bookName("Max Level Players 100th Regression")
            .series(Series.REGRESSION_OF_MAX_LEVEL_PLAYER)
            .thumbnailBytes(new FileService().readFileReturnsNullOnFailure("text/thumbnails/maxplayer.png"))
            .thumbnailFileType("png")
            .build();

    public static final PlayListData REVENGE_OF_THE_IRON_BLOODED_SWORD_HOUND = PlayListData.builder()
            .playListId("PLFWXMbDaDUZCVM0Fyf5d1u7yQOqjhBB-n")
            .bookName("Revenge of the Iron-Blooded Sword Hound")
            .series(Series.REGRESSION_OF_MAX_LEVEL_PLAYER)
            .build();

}
