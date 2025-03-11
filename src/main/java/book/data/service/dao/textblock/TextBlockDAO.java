package book.data.service.dao.textblock;

import book.data.service.exception.book.BookDoesNotExistException;
import book.data.service.exception.ChapterDoesNotExistException;
import book.data.service.factory.TextBlockFactory;
import book.data.service.repository.BookRepository;
import book.data.service.repository.ChapterRepository;
import book.data.service.repository.TextBlockRepository;
import book.data.service.service.filter.TextBlockFilterService;
import book.data.service.sqlmodel.chapter.Chapter;
import book.data.service.sqlmodel.chapter.LockStatus;
import book.data.service.sqlmodel.tag.SearchTermFilterType;
import book.data.service.sqlmodel.textblock.TextBlock;
import book.data.service.sqlmodel.textblock.TextBlockId;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextBlockDAO {

    private TextBlockRepository textBlockRepository;
    private BookRepository bookRepository;
    private ChapterRepository chapterRepository;
    private TextBlockFilterService textBlockFilterService;
    private TextBlockFactory textBlockFactory;

    @Autowired
    public TextBlockDAO(
        TextBlockRepository textBlockRepository,
        BookRepository bookRepository,
        ChapterRepository chapterRepository,
        TextBlockFilterService textBlockFilterService,
        TextBlockFactory textBlockFactory
    ) {
        this.textBlockRepository = textBlockRepository;
        this.bookRepository = bookRepository;
        this.chapterRepository = chapterRepository;
        this.textBlockFilterService = textBlockFilterService;
        this.textBlockFactory = textBlockFactory;
    }

    public List<TextBlock> getAllTextBlocks(String createdBy) {
        List<TextBlock> textBlockList = textBlockRepository.findAllTextBlocks(createdBy);
        return textBlockList;
    }

    public TextBlock getCombinedTextBlockByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        String createdBy,
        int pageNumber,
        int pageSize
    ) {
        List<TextBlock> textBlockList = getTextBlocksByBookNameAndChapterNumberPaged(
            bookName,
            chapterNumber,
            createdBy,
            pageNumber,
            pageSize
        );
        TextBlock combinedTextBlock = textBlockFactory.buildCombinedTextBlock(textBlockList);
        return combinedTextBlock;
    }

    public List<TextBlock> getTextBlocksByBookNumber(Long bookNumber) {
        List<TextBlock> textBlockList = textBlockRepository.findTextBlocksByBookNumber(bookNumber);
        return textBlockList;
    }

    public TextBlock getCombinedTextBlockByBookNumber(Long bookNumber) {
        List<TextBlock> textBlockList = textBlockRepository.findTextBlocksByBookNumber(bookNumber);
        return textBlockFactory.buildCombinedTextBlock(textBlockList);
    }

    public TextBlock getCombinedTextBlockForBookNameAndChapterNumberRange(String bookName,
        Long start, Long end, String email) {
        List<TextBlock> chapterTextBlocks = new ArrayList<>();
        for (Long i = start; i <= end; i++) {
            TextBlock textBlock = getCombinedTextBlockByBookNameAndChapterNumberPaged(bookName, i,
                email, PAGE_ZERO, MAX_PAGE_SIZE);
            chapterTextBlocks.add(textBlock);
        }
        TextBlock combinedTextBlock = textBlockFactory.buildCombinedTextBlock(chapterTextBlocks);
        return combinedTextBlock;
    }

    public TextBlock getCombinedTextBlockForBookNumberAndChapterNumberRange(Long bookNumber,
        Long start, Long end) {
        List<TextBlock> textBlockList = new ArrayList<>();
        for (Long i = start; i<=end; i++) {
            TextBlock textBlock = getCombinedTextBlockByBookNumberAndChapterNumber(
                bookNumber, i
            );
            textBlockList.add(textBlock);
        }
        TextBlock combinedTextBlock = textBlockFactory.buildCombinedTextBlock(textBlockList);
        return combinedTextBlock;
    }

    public TextBlock getCombinedTextBlockByBookNumberAndChapterNumber(
        Long bookNumber,
        Long chapterNumber
    ) {
        List<TextBlock> textBlockList = getTextBlocksByBookNumberAndChapterNumberPaged(bookNumber, chapterNumber,
            PAGE_ZERO, MAX_PAGE_SIZE);
        return textBlockFactory.buildCombinedTextBlock(textBlockList);
    }


    public List<TextBlock> getTextBlocksByBookNumberAndChapterNumberPaged(
        Long bookNumber,
        Long chapterNumber,
        int pageNumber,
        int pageSize
    ) {
        if (!bookRepository.doesBookExistWithOnlyBookNumber(bookNumber)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExistWithOnlyBookNumberAndChapterNumber(
            bookNumber, chapterNumber
        )) {
            throw new ChapterDoesNotExistException();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<TextBlock> textBlockList = textBlockRepository
            .findTextBlocksByBookNumberAndChapterNumberPaged(bookNumber, chapterNumber, pageRequest);
        return textBlockList;
    }

    public List<TextBlock> getTextBlocksByBookNameAndChapterNumberPaged(
        String bookName,
        Long chapterNumber,
        String createdBy,
        int pageNumber,
        int pageSize
    ) {
        if (!bookRepository.doesBookExist(bookName, createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(bookName, chapterNumber, createdBy)) {
            throw new ChapterDoesNotExistException();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<TextBlock> textBlockList = textBlockRepository
            .findTextBlocksByBookNameAndChapterNumberPaged(bookName, chapterNumber, createdBy,
                pageRequest);
        return textBlockList;
    }

    public List<Long> getTextBlockChapterNumbersByBookNumber(Long bookNumber) {
        List<TextBlock> textBlockList = getTextBlocksByBookNumber(bookNumber);
        Set<Long> textBlockChapterNumberSet = textBlockList.stream().map((textBlock) -> {
            Long chapterNumber = textBlock.getTextBlockId().getChapter().getChapterId().getChapterNumber();
            return chapterNumber;
        }).collect(Collectors.toSet());
        List<Long> textBlockChapterNumberList = new ArrayList<>(textBlockChapterNumberSet);
        return textBlockChapterNumberList;
    }

    public List<TextBlock> searchTextBlocksBySearchTokens(String[] searchTokens,
        SearchTermFilterType searchTermFilterType, String createdBy) {
        List<TextBlock> textBlockList = textBlockRepository.findAllTextBlocks(createdBy);
        List<String> searchTokenList = List.of(searchTokens);
        List<TextBlock> selectedTextBlockList = textBlockFilterService.selectTextBlockBySearchTerms(
            textBlockList, searchTokenList, searchTermFilterType);
        return selectedTextBlockList;
    }


    public TextBlock createTextBlock(
        Chapter chapter,
        Long textBlockNumber,
        String textBlockText,
        String createdBy,
        Long createdEpochMilli
    ) {
        if (!bookRepository.doesBookExist(chapter.getChapterId().getBook().getBookName(),
            createdBy)) {
            throw new BookDoesNotExistException();
        }
        if (!chapterRepository.doesChapterExist(chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(), createdBy)) {
            throw new ChapterDoesNotExistException();
        }

        Chapter currChapter = chapterRepository.findChapterByOnlyBookNumberAndChapterNumber(
            chapter.getChapterId().getBook().getBookNumber(),
            chapter.getChapterId().getChapterNumber());
        if (currChapter.getLockStatus() == LockStatus.LOCKED) {
            throw new RuntimeException("Cannot modify a locked chapter");
        }
        if (textBlockRepository.doesTextBlockExist(
            chapter.getChapterId().getBook().getBookName(),
            chapter.getChapterId().getChapterNumber(),
            textBlockNumber,
            createdBy
        )) {
            throw new TextBlockAlreadyExistsException();
        }
        TextBlockId textBlockId = new TextBlockId(textBlockNumber, chapter);
        TextBlock textBlock = new TextBlock(textBlockId, textBlockText, createdBy,
            createdEpochMilli);
        textBlockRepository.saveTextBlock(textBlock);
        return textBlock;
    }
}
