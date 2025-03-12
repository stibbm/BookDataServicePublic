package book.data.service.manager.book;

import book.data.service.constants.videodetailstemplates.SeriesHelper;
import book.data.service.constants.videodetailstemplates.VideoDetailsTemplate;
import book.data.service.constants.videodetailstemplates.VideoDetailsTemplates;
import book.data.service.constants.videodetailstemplates.YoutubePlayLists;
import book.data.service.model.PlayListData;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.youtubevideo.YoutubeVideoDetailsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import book.data.service.constants.videodetailstemplates.Series;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class PopulateBooksManager {

    private BookManager bookManager;
    private VideoDetailsTemplates videoDetailsTemplates;

    @Autowired
    public PopulateBooksManager(BookManager bookManager,
                                VideoDetailsTemplates videoDetailsTemplates) {
        this.bookManager = bookManager;
        this.videoDetailsTemplates = videoDetailsTemplates;
    }

    public List<Book> populateBooks(String createdBy)
            throws MalformedURLException, URISyntaxException {
        List<Series> seriesList = SeriesHelper.getSeriesList();
        List<Book> bookList = new ArrayList<>();
        for (Series series : seriesList) {
            String seriesString = series.toString();
            Map<String, PlayListData> playListMapBySeriesString = YoutubePlayLists.getPlayListMapBySeriesString();
            YoutubeVideoDetailsTemplate youtubeVideoDetailsTemplate = videoDetailsTemplates.getSeriesTemplateAsYoutubeVideoDetailsTemplate(
                    series);
            Map<String, VideoDetailsTemplate> videoDetailsTemplateMap = new HashMap<>();

            String bookName = seriesString.replace("_", " ");
            Set<String> tagSet = youtubeVideoDetailsTemplate.getVideoTags();
            String language = "Korean";
            VideoDetailsTemplate videoDetailsTemplate = videoDetailsTemplateMap.get(seriesString);
            boolean doesBookExist = bookManager.doesBookExistByBookNameOnly(bookName);
            if (!doesBookExist) {
                Book book = bookManager.createBookNoViews(
                        bookName,
                        createdBy,
                        bookName,
                        language,
                        tagSet,
                        YoutubePlayLists.getPlayListMapBySeriesString().get(seriesString)
                                .getThumbnailBytes(),
                        YoutubePlayLists.getPlayListMapBySeriesString().get(seriesString)
                                .getThumbnailFileType()
                );
                bookList.add(book);
            }
        }
        return bookList;
    }
}
