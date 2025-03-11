package book.data.service.manager.textblock;

import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.textblock.TextBlockDAO;
import book.data.service.factory.TextBlockFactory;
import book.data.service.service.textservice.TextHelperService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.LockStatus;
import book.data.service.sqlmodel.textblock.TextBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class TextBlockManager {

    private TextBlockDAO textBlockDAO;
    private ChapterDAO chapterDAO;
    private TimeService timeService;
    private TextHelperService textHelperService;
    private TextBlockFactory textBlockFactory;
    private BookDAO bookDAO;

    @Autowired
    public TextBlockManager(TextBlockDAO textBlockDAO, ChapterDAO chapterDAO,
                            TimeService timeService, TextHelperService textHelperService, BookDAO bookDAO,
                            TextBlockFactory textBlockFactory) {
        this.textBlockDAO = textBlockDAO;
        this.chapterDAO = chapterDAO;
        this.timeService = timeService;
        this.textHelperService = textHelperService;
        this.textBlockFactory = textBlockFactory;
        this.bookDAO = bookDAO;
    }

    public TextBlock getCombinedTextBlockByBookNameAndChapterNumber(String bookName, Long chapterNumber,
                                                                    String createdBy) {
        List<TextBlock> textBlockList = textBlockDAO.getTextBlocksByBookNameAndChapterNumberPaged(
                bookName,
                chapterNumber,
                createdBy,
                PAGE_ZERO,
                MAX_PAGE_SIZE
        );
        TextBlock textBlock = textBlockFactory.buildCombinedTextBlock(textBlockList);
        return textBlock;
    }

    public TextBlock createLargeTextBlock(
            String bookName,
            Long chapterNumber,
            String textBlockText,
            String createdBy
    ) {
        List<String> textChunks = textHelperService.splitTextIntoChunks(
                textBlockText, TEXT_BLOCK_CHUNK_SIZE
        );
        List<TextBlock> textBlockList = IntStream.range(0, textChunks.size())
                .mapToObj(index -> createTextBlock(
                        bookName,
                        chapterNumber,
                        (long) index,
                        textChunks.get(index),
                        createdBy
                ))
                .toList();
        TextBlock combinedTextBlock = textBlockFactory.buildCombinedTextBlock(textBlockList);
        Book book = bookDAO.getBookByBookName(bookName, createdBy);
        Chapter lockedChapter = chapterDAO.updateChapterLockStatusByBookNumberAndChapterNumber(
                book.getBookNumber(), chapterNumber, LockStatus.LOCKED
        );
        log.info("lockedChapter: " + lockedChapter);
        return combinedTextBlock;
    }

    public TextBlock createTextBlock(
            String bookName,
            Long chapterNumber,
            Long textBlockNumber,
            String textBlockText,
            String createdBy
    ) {
        log.info("chapterNumber: " + chapterNumber);
        log.info("create text block with block number: " + textBlockNumber);
        Chapter chapter = chapterDAO.getChapterByBookNameAndChapterNumber(bookName, chapterNumber,
                createdBy);
        Long createdEpochMilli = timeService.getCurrentLocalDateTimeLong();
        TextBlock textBlock = textBlockDAO.createTextBlock(chapter, textBlockNumber, textBlockText,
                createdBy, createdEpochMilli);
        return textBlock;
    }
}
