package book.data.service.manager.chapter;


import book.data.service.dao.book.BookDAO;
import book.data.service.dao.chapter.ChapterDAO;
import book.data.service.dao.image.ImageDAO;
import book.data.service.dao.textblock.TextBlockDAO;
import book.data.service.factory.TextBlockFactory;
import book.data.service.model.ChapterHeader;
import book.data.service.sqlmodel.book.Book;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.image.Image;
import book.data.service.sqlmodel.textblock.TextBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChapterHeaderManager {

    private BookDAO bookDAO;
    private ChapterDAO chapterDAO;
    private ImageDAO imageDAO;
    private TextBlockDAO textBlockDAO;
    private TextBlockFactory textBlockFactory;

    @Autowired
    public ChapterHeaderManager(BookDAO bookDAO, ChapterDAO chapterDAO, ImageDAO imageDAO,
                                TextBlockDAO textBlockDAO, TextBlockFactory textBlockFactory) {
        this.bookDAO = bookDAO;
        this.chapterDAO = chapterDAO;
        this.imageDAO = imageDAO;
        this.textBlockDAO = textBlockDAO;
        this.textBlockFactory = textBlockFactory;
    }

    public List<ChapterHeader> getChapterHeadersByBookNumber(Long bookNumber, String createdBy) {
        List<ChapterHeader> chapterHeaderList = new ArrayList<>();
        Book book = bookDAO.getBookByBookNumber(bookNumber, createdBy);
        List<Chapter> chapterList = chapterDAO.getChaptersByBookNamePaged(book.getBookName(),
                PAGE_ZERO, MAX_PAGE_SIZE, createdBy);
        List<TextBlock> bookTextBlockList = textBlockDAO.getTextBlocksByBookNumber(bookNumber);
        List<Image> bookImageList = imageDAO.getImagesByBookNumber(bookNumber);
        Map<Long, List<Image>> imageMap = bookImageList.stream()
                .collect(Collectors.groupingBy(image -> image.getImageId().getChapter().getChapterId().getChapterNumber()));
        Map<Long, List<TextBlock>> textBlockMap = bookTextBlockList.stream()
                .collect(Collectors.groupingBy(textBlock -> textBlock.getTextBlockId().getChapter().getChapterId().getChapterNumber()));
        for (Chapter chapter : chapterList) {
            List<Image> imageList = imageMap.get(chapter.getChapterId().getChapterNumber());
            if (imageList == null) {
                imageList = new ArrayList<>();
            }
            String chapterType = "image";
            Integer charCount = 0;
            if (imageList.size() == 0) {
                List<TextBlock> textBlockList = textBlockMap.get(chapter.getChapterId().getChapterNumber());
                if (textBlockList == null) {
                    textBlockList = new ArrayList<>();
                }
                TextBlock textBlock = textBlockFactory.buildCombinedTextBlock(textBlockList);
                chapterType = "text";
                charCount = textBlock.getTextBlockText().length();
            }
            ChapterHeader chapterHeader = ChapterHeader.builder()
                    .bookName(book.getBookName())
                    .chapterNumber((chapter.getChapterId().getChapterNumber()))
                    .chapterName(chapter.getChapterName())
                    .chapterViews(chapter.getChapterViews())
                    .createdBy(chapter.getCreatedBy())
                    .imagesCount(imageList.size())
                    .charCount(charCount)
                    .chapterType(chapterType)
                    .build();
            chapterHeaderList.add(chapterHeader);
        }
        return chapterHeaderList;
    }
}
