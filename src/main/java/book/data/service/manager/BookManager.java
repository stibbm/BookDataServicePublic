package book.data.service.manager;

import book.data.service.clientwrapper.S3ClientWrapper;
import book.data.service.constants.BookSortTypes;
import book.data.service.dao.textblock.TextBlockDAO;
import book.data.service.exception.BookAlreadyExistsException;
import book.data.service.service.filter.BookFilterService;
import book.data.service.service.id.IdService;
import book.data.service.service.time.TimeService;
import book.data.service.sqlmodel.tag.SearchTermFilterType;
import book.data.service.sqlmodel.textblock.TextBlock;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import book.data.service.dao.book.BookDAO;
import book.data.service.exception.BookDoesNotExistException;
import book.data.service.sqlmodel.book.Book;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookManager {

    private BookDAO bookDAO;
    private S3ClientWrapper s3ClientWrapper;
    private IdService idService;
    private TimeService timeService;
    private BookFilterService bookFilterService;
    private TextBlockDAO textBlockDAO;

    @Autowired
    public BookManager(
            BookDAO bookDAO,
            S3ClientWrapper s3ClientWrapper,
            IdService idService,
            TimeService timeService,
            BookFilterService bookFilterService,
            TextBlockDAO textBlockDAO) {
        this.bookDAO = bookDAO;
        this.s3ClientWrapper = s3ClientWrapper;
        this.idService = idService;
        this.timeService = timeService;
        this.bookFilterService = bookFilterService;
        this.textBlockDAO = textBlockDAO;
    }

    public Book getBookByBookName(String bookName, String createdBy)
            throws BookDoesNotExistException {
        return bookDAO.getBookByBookName(bookName, createdBy);
    }

    public Book getBookByBookNumber(Long bookNumber, String createdBy) {
        return bookDAO.getBookByBookNumber(bookNumber, createdBy);
    }

    public List<Book> getAllBooksPaged(int pageNumber, int pageSize, String createdBy) {
        return bookDAO.getAllBooksPaged(pageNumber, pageSize, createdBy);
    }

    public List<Book> getAllBooksSortedPaged(String sortType, int pageNumber, int pageSize,
                                             String createdBy) {
        log.info("getAllBooksSorted sortType: " + sortType);
        if (sortType.equalsIgnoreCase(BookSortTypes.BOOK_VIEWS)) {
            return bookDAO.getAllBooksSortedByBookViewsPaged(pageNumber, pageSize, createdBy);
        } else if (sortType.equalsIgnoreCase(BookSortTypes.CREATION_TIME)) {
            return bookDAO.getAllBooksSortedByCreationTimePaged(pageNumber, pageSize, createdBy);
        } else if (sortType.equalsIgnoreCase(BookSortTypes.BOOK_NAME)) {
            return bookDAO.getAllBooksSortedByBookNamePaged(pageNumber, pageSize, createdBy);
        } else {
            throw new InvalidBookSortTypeException();
        }
    }

    public List<Book> getBooksByBookTag(String bookTag, int pageNumber, int pageSize,
                                        String createdBy) {
        return bookDAO.getBooksByBookTagPaged(bookTag, pageNumber, pageSize, createdBy);
    }

    private List<Book> getBooksWithAllSpecifiedTags(List<String> tagList, String createdBy) {
        List<Book> bookList = bookDAO.getAllBooksPaged(PAGE_ZERO, MAX_PAGE_SIZE, createdBy);
        return bookFilterService.selectBooksWithAllSpecifiedTags(bookList, tagList);
    }

    private List<Book> getBooksWithAtLeastOneSpecifiedTag(List<String> tagList, String createdBy) {
        List<Book> bookList = bookDAO.getAllBooksPaged(PAGE_ZERO, MAX_PAGE_SIZE, createdBy);
        return bookFilterService.selectBooksWithAtLeastOneSpecifiedTag(bookList, tagList);
    }

    public List<Book> searchBooksByBookTags(List<String> tagList, TagFilterType tagFilterType,
                                            String createdBy) {
        if (tagFilterType == TagFilterType.MATCH_ALL_TAGS) {
            return getBooksWithAllSpecifiedTags(tagList, createdBy);
        } else if (tagFilterType == TagFilterType.MATCH_AT_LEAST_ONE_TAG) {
            return getBooksWithAtLeastOneSpecifiedTag(tagList, createdBy);
        } else {
            throw new IllegalArgumentException("Invalid tag filter type");
        }
    }

    public List<Book> searchBooks(String searchInput, SearchTermFilterType searchTermFilterType,
                                  String createdBy) {
        String[] searchTokensArray = searchInput.split("\\s+");
        List<String> searchTokensList = Arrays.asList(searchTokensArray);
        List<Book> bookList = searchBooks(searchTokensList, searchTermFilterType, createdBy);
        return bookList;
    }

    public List<Book> searchBooks(List<String> searchTokens,
                                  SearchTermFilterType searchTermFilterType, String createdBy) {
        List<TextBlock> textBlockList = textBlockDAO.searchTextBlocksBySearchTokens(
                searchTokens.toArray(new String[searchTokens.size()]), searchTermFilterType, createdBy);
        Map<Long, List<TextBlock>> textBlocksByBook = textBlockList.stream()
                .collect(Collectors.groupingBy(
                        textBlock -> textBlock.getTextBlockId().getChapter().getChapterId().getBook()
                                .getBookNumber()));
        List<Book> bookList = sortBooksByNumberOfTextBlocks(textBlocksByBook);
        return bookList;
    }

    public boolean doesBookExistByBookNameOnly(String bookName) {
        return bookDAO.doesBookExistByBookNameOnly(bookName);
    }

    public boolean doesBookExist(String bookName, String createdBy) {
        return bookDAO.doesBookExist(bookName, createdBy);
    }

    public Book createBookNoViews(
            String bookName,
            String createdBy,
            String bookDescription,
            String bookLanguage,
            Set<String> bookTags,
            byte[] fileBytes,
            String fileType
    ) throws MalformedURLException, URISyntaxException {
        return createBook(bookName,
                createdBy,
                bookDescription,
                bookLanguage,
                NO_VIEWS,
                bookTags,
                fileBytes,
                fileType);
    }

    public Book createBook(
            String bookName,
            String createdBy,
            String bookDescription,
            String bookLanguage,
            Long bookViews,
            Set<String> bookTags,
            byte[] fileBytes,
            String fileType) throws BookAlreadyExistsException, MalformedURLException, URISyntaxException {
        String thumbnailS3Key = idService.generateThumbnailS3Key(bookName, fileType, createdBy);
        String thumbnailRelativeImageUrl = idService.generateRelativeImageUrlFromS3Key(
                thumbnailS3Key);
        s3ClientWrapper.createS3File(fileBytes, S3_IMAGE_BUCKET, thumbnailS3Key);
        Long createAtEpochMilli = timeService.getCurrentLocalDateTimeLong();
        log.info("createAtEpochMilli: " + createAtEpochMilli);
        return bookDAO.createBook(
                bookName,
                createdBy,
                bookDescription,
                bookLanguage,
                bookViews,
                thumbnailRelativeImageUrl,
                thumbnailS3Key,
                S3_IMAGE_BUCKET,
                bookTags,
                createAtEpochMilli);
    }

    public Book updateBookViewsByBookNumber(Long bookNumber, Long updatedBookViews,
                                            String createdBy) {
        Book updatedBook = bookDAO.updateBookViewsByBookNumber(bookNumber, updatedBookViews,
                createdBy);
        return updatedBook;
    }

    public Book deleteBook(String bookName, String createdBy) {
        Book deletedBook = bookDAO.deleteBook(bookName, createdBy);
        return deletedBook;
    }

    private List<Long> sortKeysByListSize(Map<Long, List<TextBlock>> map) {
        return map.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(),
                        entry1.getValue().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Book> sortBooksByNumberOfTextBlocks(
            Map<Long, List<TextBlock>> map) {
        return map.entrySet()
                .stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().size(),
                        entry1.getValue().size()))
                .map(entry -> entry.getValue().get(0).getTextBlockId().getChapter().getChapterId()
                        .getBook())
                .toList();
    }

}
