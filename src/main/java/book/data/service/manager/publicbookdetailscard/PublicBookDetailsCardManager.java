package book.data.service.manager.publicbookdetailscard;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.textblock.TextBlockDAO;
import book.data.service.dao.youtubevideo.YouTubeVideoDAO;
import book.data.service.model.NextVideo;
import book.data.service.model.PublicBookDetailsCard;
import book.data.service.service.costandprice.CostAndPriceService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.textblock.TextBlock;
import book.data.service.sqlmodel.youtubevideo.YouTubeVideo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static book.data.service.constants.Constants.MAX_PAGE_SIZE;
import static book.data.service.constants.Constants.PAGE_ZERO;
import static book.data.service.constants.Constants.YOUTUBE_VIDEO_CHAPTER_COUNT;

@Slf4j
@Service
public class PublicBookDetailsCardManager {

    private BookDAO bookDAO;
    private ChapterDAO chapterDAO;
    private YouTubeVideoDAO youTubeVideoDAO;
    private TextBlockDAO textBlockDAO;
    private CostAndPriceService costAndPriceService;

    public PublicBookDetailsCardManager(
            BookDAO bookDAO,
            ChapterDAO chapterDAO,
            YouTubeVideoDAO youTubeVideoDAO,
            TextBlockDAO textBlockDAO,
            CostAndPriceService costAndPriceService
    ) {
        this.bookDAO = bookDAO;
        this.chapterDAO = chapterDAO;
        this.youTubeVideoDAO = youTubeVideoDAO;
        this.textBlockDAO = textBlockDAO;
        this.costAndPriceService = costAndPriceService;
    }

    public PublicBookDetailsCard getPublicBookDetailsCardByBookNumber(
            Long bookNumber,
            String createdBy
    ) {
        Book book = bookDAO.getBookByBookNumber(bookNumber, createdBy);
        List<Chapter> chapterList = chapterDAO.getChaptersByBookNamePaged(
                book.getBookName(),
                PAGE_ZERO,
                MAX_PAGE_SIZE,
                createdBy
        );
        Map<Long, Chapter> chapterMap = chapterList.stream().collect(Collectors.toMap(
                chapter -> chapter.getChapterId().getChapterNumber(),
                chapter -> chapter
        ));
        List<YouTubeVideo> youTubeVideoList = youTubeVideoDAO.getYouTubeVideosByBookNumber(
                bookNumber);
        Optional<YouTubeVideo> maxYouTubeVideoOptional = youTubeVideoList.stream()
                .max((ytv1, ytv2) ->
                        Long.compare(ytv1.getEndChapterNumber(),
                                ytv2.getEndChapterNumber())
                );
        List<TextBlock> textBlockList = textBlockDAO.getTextBlocksByBookNumber(bookNumber);
        Optional<TextBlock> maxTextBlockOptional = textBlockList.stream().max((tb1, tb2) ->
                Long.compare(tb1.getTextBlockId().getChapter().getChapterId().getChapterNumber(),
                        tb2.getTextBlockId().getChapter().getChapterId().getChapterNumber()));
        PublicBookDetailsCard publicBookDetailsCard = new PublicBookDetailsCard();
        publicBookDetailsCard.setBook(book);
        publicBookDetailsCard.setYouTubeVideoList(youTubeVideoList);
        maxYouTubeVideoOptional.ifPresentOrElse(
                maxYouTubeVideo -> publicBookDetailsCard.setMaxVideo(
                        maxYouTubeVideo.getEndChapterNumber()),
                () -> publicBookDetailsCard.setMaxVideo(0L));
        maxTextBlockOptional.ifPresentOrElse(
                maxTextBlock -> publicBookDetailsCard.setMaxTextChapter(
                        maxTextBlock.getTextBlockId().getChapter().getChapterId().getChapterNumber()),
                () -> publicBookDetailsCard.setMaxTextChapter(0L));
        Long nextVideoStartChapter = publicBookDetailsCard.getMaxVideo() + 1;
        Long nextVideoEndChapter =
                publicBookDetailsCard.getMaxVideo() + YOUTUBE_VIDEO_CHAPTER_COUNT;
        if (publicBookDetailsCard.getMaxTextChapter() >= (publicBookDetailsCard.getMaxVideo()
                + YOUTUBE_VIDEO_CHAPTER_COUNT)) {
            for (Long chapterNumber = nextVideoStartChapter; chapterNumber <= nextVideoEndChapter;
                 chapterNumber++) {
                if (!chapterMap.containsKey(chapterNumber)) {
                    log.warn("chapter number: " + chapterNumber
                            + " is missing even though future chapters are present");
                }
            }
            publicBookDetailsCard.setHasEnoughChaptersForNextVideo(true);
        } else {
            publicBookDetailsCard.setHasEnoughChaptersForNextVideo(false);
        }
        if (publicBookDetailsCard.getHasEnoughChaptersForNextVideo()) {
            String nextVideoText = textBlockDAO.getCombinedTextBlockForBookNameAndChapterNumberRange(
                    book.getBookName(),
                    nextVideoStartChapter,
                    nextVideoEndChapter,
                    createdBy
            ).getTextBlockText();
            Long nextVideoTextSize = (long) nextVideoText.length();
            Double nextVideoPriceCentsDouble = costAndPriceService.calculatePriceInCentsForUntranslatedInputCharCount(
                    nextVideoTextSize);
            Long nextVideoPriceCentsLong = nextVideoPriceCentsDouble.longValue();
            Double nextVideoPriceTokensDouble = costAndPriceService.calculatePriceInTokensForUntranslatedInputCharCount(
                    nextVideoTextSize);
            Long nextVideoPriceTokensLong = nextVideoPriceTokensDouble.longValue();
            NextVideo nextVideo = NextVideo.builder()
                    .startChapter(nextVideoStartChapter)
                    .endChapter(nextVideoEndChapter)
                    .priceCents(nextVideoPriceCentsLong)
                    .priceTokens(nextVideoPriceTokensLong)
                    .build();
            publicBookDetailsCard.setNextVideo(nextVideo);
            List<Long> textBlockChapterNumberList = textBlockDAO.getTextBlockChapterNumbersByBookNumber(bookNumber);
            publicBookDetailsCard.setTextChapterNumberList(textBlockChapterNumberList);
        }
        return publicBookDetailsCard;
    }
}
