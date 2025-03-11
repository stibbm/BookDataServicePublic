package book.data.service.manager.youtubevideo;


import book.data.service.dao.book.BookDAO;
import book.data.service.manager.book.BookManager;
import book.data.service.manager.book.PopulateBooksManager;
import book.data.service.manager.chapter.ChapterManager;
import book.data.service.manager.textblock.TextBlockManager;
import book.data.service.model.YoutubeVideoInfo;
import book.data.service.service.file.FileService;
import book.data.service.service.youtube.YouTubePlayListService;
import book.data.service.service.youtube.YouTubeVideoUrlService;
import book.data.service.service.youtube.YoutubeTitleParsingService;
import book.data.service.service.youtube.YoutubeVideoDetailsService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.textblock.TextBlock;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class YoutubeVideosPopulateDataForPlaylistManager {

    private YouTubeVideoUrlService youTubeVideoUrlService;
    private YouTubePlayListService youTubePlayListService;
    private YoutubeTitleParsingService youtubeTitleParsingService;
    private YoutubeVideoDetailsService youtubeVideoDetailsService;
    private YouTubeVideoManager youTubeVideoManager;
    private BookDAO bookDAO;
    private ChapterManager chapterManager;
    private TextBlockManager textBlockManager;
    private FileService fileService;
    private BookManager bookManager;
    private PopulateBooksManager populateBooksManager;

    @Autowired
    public YoutubeVideosPopulateDataForPlaylistManager(
            YouTubeVideoUrlService youTubeVideoUrlService,
            YouTubePlayListService youTubePlayListService,
            YoutubeTitleParsingService youtubeTitleParsingService,
            YoutubeVideoDetailsService youtubeVideoDetailsService,
            YouTubeVideoManager youTubeVideoManager,
            BookDAO bookDAO,
            ChapterManager chapterManager,
            TextBlockManager textBlockManager,
            FileService fileService,
            BookManager bookManager,
            PopulateBooksManager populateBooksManager
    ) {
        this.youTubeVideoUrlService = youTubeVideoUrlService;
        this.youTubePlayListService = youTubePlayListService;
        this.youtubeTitleParsingService = youtubeTitleParsingService;
        this.youtubeVideoDetailsService = youtubeVideoDetailsService;
        this.youTubeVideoManager = youTubeVideoManager;
        this.bookDAO = bookDAO;
        this.chapterManager = chapterManager;
        this.textBlockManager = textBlockManager;
        this.fileService = fileService;
        this.bookManager = bookManager;
        this.populateBooksManager = populateBooksManager;
    }

    public Chapter createTextChapter(String bookName, Long bookNumber, Long chapterNumber,
                                     String textFilePath, String createdBy) throws IOException {
        log.info("creating chapter");
        log.info("bookName: " + bookName);
        String chapterName = "chapter " + chapterNumber;
        String fileText = new String(fileService.readFile(textFilePath));
        Chapter chapter = chapterManager.createChapterNoViews(
                bookName, chapterNumber, chapterName, createdBy
        );
        TextBlock textBlock = textBlockManager.createLargeTextBlock(bookName, chapterNumber,
                fileText, createdBy);
        return chapter;
    }

    public List<List<YouTubeVideo>> createYoutubeVideoPlaylists(String createdBy)
            throws IOException, URISyntaxException {
        List<Book> existingBooks = bookManager.getAllBooksPaged(0, 10000, createdBy);
        log.info("existing books = " + existingBooks.toString());
        List<Book> createdBookList = populateBooksManager.populateBooks(createdBy);
        List<List<YouTubeVideo>> createdYoutubeVideoList = new ArrayList<>();
        Map<String, PlayListData> playListMap = getPlayListMapBySeriesString();
        for (Map.Entry<String, PlayListData> entry : playListMap.entrySet()) {
            String seriesString = entry.getKey();
            String playlistId = getPlaylistIdBySeriesString(seriesString);
            Map<String, PlayListData> playlistDataBySeriesString = getPlayListMapBySeriesString();
            PlayListData playListData = playlistDataBySeriesString.get(seriesString);
            String bookName = seriesString.replace("_", " ");
            Book book = bookDAO.getBookByBookNameOnly(bookName);
            List<YouTubeVideo> playlistVideos = createYoutubeVideosByPlaylistId(
                    book.getBookNumber(), playlistId, createdBy);
            createdYoutubeVideoList.add(playlistVideos);
            String directoryString = "text/" + seriesString;
            File directory = new File(directoryString);
            List<Chapter> chapterList = new ArrayList<>();
            for (File file : directory.listFiles()) {
                log.info("creating chapter for file = " + file.getAbsolutePath());
                String chapterNumberString = file.getName().substring(file.getName().lastIndexOf("_") + 1);
                if (chapterNumberString.contains("/")) {
                    chapterNumberString = chapterNumberString.substring(chapterNumberString.lastIndexOf("/")+1);
                }
                chapterNumberString = chapterNumberString.substring(0,
                        chapterNumberString.lastIndexOf("."));
                Long chapterNumber = Long.parseLong(chapterNumberString);
                Chapter chapter = createTextChapter(
                        book.getBookName(),
                        book.getBookNumber(),
                        chapterNumber,
                        file.getAbsolutePath(),
                        createdBy
                );
                chapterList.add(chapter);
            }
        }
        return createdYoutubeVideoList;
    }

    public List<YouTubeVideo> createYoutubeVideosByPlaylistId(Long bookNumber, String playlistId,
                                                              String createdBy)
            throws IOException {
        List<YouTubeVideo> createdYoutubeVideoList = new ArrayList<>();
        List<String> youtubeVideoIdList = youTubePlayListService.getVideoIdsInPlayList(playlistId);
        for (String youtubeVideoId : youtubeVideoIdList) {
            try {
                YoutubeVideoInfo videoInfo = youtubeVideoDetailsService.getYoutubeVideoDetailsByVideoId(
                        youtubeVideoId);
                String title = videoInfo.getTitle();
                String chaptersString = youtubeTitleParsingService.findLastMatch(title);
                String startChapterString = chaptersString.substring(0,
                        chaptersString.indexOf("-"));
                String endChapterString = chaptersString.substring(chaptersString.indexOf("-") + 1);
                Long startChapterLong = Long.parseLong(startChapterString);
                Long endChapterLong = Long.parseLong(endChapterString);
                if (!youTubeVideoManager.doesYouTubeVideoExist(youtubeVideoId)) {
                    YouTubeVideo youtubeVideo = youTubeVideoManager
                            .createYouTubeVideo(
                                    youtubeVideoId,
                                    bookNumber,
                                    startChapterLong,
                                    endChapterLong,
                                    createdBy
                            );
                    createdYoutubeVideoList.add(youtubeVideo);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return createdYoutubeVideoList;
    }
}
